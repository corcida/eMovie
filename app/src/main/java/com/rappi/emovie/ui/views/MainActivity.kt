package com.rappi.emovie.ui.views

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.rappi.emovie.R
import com.rappi.emovie.adapter.MoviesAdapter
import com.rappi.emovie.databinding.ActivityMainBinding
import com.rappi.emovie.ui.vm.MainViewModel
import com.rappi.emovie.ui.vm.MainViewModel.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var topRatedAdapter : MoviesAdapter
    private lateinit var upcomingAdapter : MoviesAdapter
    private lateinit var recommendedAdapter : MoviesAdapter
    private val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setViews()
        viewModel.model.observe(this, Observer(this::viewModelObserver))
        viewModel.onCreate()
    }

    private fun setViews(){
        setAdapters()
        setClickListeners()
    }

    private fun setClickListeners(){
        binding.language.setOnClickListener {
            changeButtonAppearance(false, binding.date)
            changeButtonAppearance(true, binding.language)
            viewModel.getEnglishMovies()
        }
        binding.date.setOnClickListener {
            changeButtonAppearance(true, binding.date)
            changeButtonAppearance(false, binding.language)
            viewModel.getMoviesByDate()
        }
    }

    private fun changeButtonAppearance(active: Boolean, button: Button){
        val drawable = if (active) R.drawable.button_active_state else R.drawable.button_inactive_state
        val color = if (active) R.color.button_active_text_state else R.color.button_inactive_text_state
        button.background = ContextCompat.getDrawable(this, drawable)
        button.setTextColor(ContextCompat.getColorStateList(this, color))
    }

    private fun setAdapters(){
        topRatedAdapter = MoviesAdapter { viewModel.onMovieItemSelected(it) }
        upcomingAdapter = MoviesAdapter { viewModel.onMovieItemSelected(it) }
        recommendedAdapter = MoviesAdapter { viewModel.onMovieItemSelected(it) }
        binding.topRated.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.upcoming.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recommended.layoutManager = GridLayoutManager(this, 2)
        binding.topRated.adapter = topRatedAdapter
        binding.upcoming.adapter = upcomingAdapter
        binding.recommended.adapter = recommendedAdapter
    }

    private fun viewModelObserver(model : UiModel){
        when (model) {
            is UiModel.Error -> {
                Toast.makeText(this, model.value, Toast.LENGTH_SHORT).show()
            }
            is UiModel.RecommendedData -> recommendedAdapter.movies = model.data
            is UiModel.TopRatedData -> topRatedAdapter.movies = model.data

            is UiModel.UpcomingData -> upcomingAdapter.movies = model.data
        }
    }

}