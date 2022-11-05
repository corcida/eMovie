package com.rappi.emovie.ui.views

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.rappi.emovie.R
import com.rappi.emovie.adapter.MoviesAdapter
import com.rappi.emovie.databinding.ActivityMainBinding
import com.rappi.emovie.domain.model.Movie
import com.rappi.emovie.ui.vm.MainViewModel
import com.rappi.emovie.ui.vm.MainViewModel.UiModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var topRatedAdapter : MoviesAdapter
    private lateinit var upcomingAdapter : MoviesAdapter
    private lateinit var recommendedAdapter : MoviesAdapter
    private lateinit var noDataDialogFragment : NoDataDialogFragment
    private val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    private fun setAdapters(){
        topRatedAdapter = MoviesAdapter{ movie: Movie, view: View ->
            viewModel.onMovieItemSelected(movie, view) }
        upcomingAdapter = MoviesAdapter{ movie: Movie, view: View ->
            viewModel.onMovieItemSelected(movie, view) }
        recommendedAdapter = MoviesAdapter{ movie: Movie, view: View ->
            viewModel.onMovieItemSelected(movie, view) }
        binding.topRated.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.upcoming.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recommended.layoutManager = GridLayoutManager(this, 2)
        binding.topRated.adapter = topRatedAdapter
        binding.upcoming.adapter = upcomingAdapter
        binding.recommended.adapter = recommendedAdapter
    }

    private fun changeButtonAppearance(active: Boolean, button: Button){
        val drawable = if (active) R.drawable.button_active_state else R.drawable.button_inactive_state
        val color = if (active) R.color.button_active_text_state else R.color.button_inactive_text_state
        button.background = ContextCompat.getDrawable(this, drawable)
        button.setTextColor(ContextCompat.getColorStateList(this, color))
    }

    private fun tryAgain(){
        noDataDialogFragment.dismiss()
        viewModel.onCreate()
    }

    private fun viewModelObserver(model : UiModel){
        when (model) {
            is UiModel.Error -> {
                noDataDialogFragment = NoDataDialogFragment(model.value){ tryAgain() }
                noDataDialogFragment.isCancelable = false
                noDataDialogFragment.show(supportFragmentManager, "noDataDialog")
            }
            is UiModel.RecommendedData -> recommendedAdapter.movies = model.data
            is UiModel.TopRatedData -> topRatedAdapter.movies = model.data
            is UiModel.UpcomingData -> upcomingAdapter.movies = model.data
            is UiModel.MovieSelected -> {
                val intent = Intent(this, DetailActivity::class.java)
                intent.putExtra("data", model.data)
                val options = ActivityOptions
                    .makeSceneTransitionAnimation(this, model.view, "photo")
                startActivity(intent, options.toBundle())
            }
        }
    }

}