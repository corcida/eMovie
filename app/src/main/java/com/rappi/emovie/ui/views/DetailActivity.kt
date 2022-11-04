package com.rappi.emovie.ui.views

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.rappi.emovie.adapter.GenresAdapter
import com.rappi.emovie.databinding.ActivityDetailBinding
import com.rappi.emovie.ui.vm.DetailViewModel
import com.rappi.emovie.ui.vm.DetailViewModel.*
import com.rappi.emovie.utils.Constants
import android.util.Pair as UtilPair
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding
    private val genreAdapter = GenresAdapter()
    private val viewModel : DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setViews()
        viewModel.model.observe(this, Observer(this::viewModelObserver))
        viewModel.onCreate(intent.getStringExtra("data").toString())
    }

    private fun setViews(){
        binding.back.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        binding.genres.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.genres.adapter = genreAdapter
    }

    private fun viewModelObserver(model: UiModel){
        when (model){
            is UiModel.Genres -> genreAdapter.genres = model.data
            is UiModel.MovieData -> {
                val url = Constants.imageUrl + model.data.poster_path
                Glide.with(binding.photo.context).load(url).into(binding.photo)
                binding.title.text = model.data.title
                binding.date.text = model.data.release_date.take(4)
                binding.language.text = model.data.original_language
                binding.description.text = model.data.overview
                binding.rate.text = String.format("%.1f", model.data.vote_average)
                binding.trailer.setOnClickListener { viewModel.watchTrailer(model.data) }
            }
            is UiModel.WatchTrailer -> {
                val intent = Intent(this, WatchTrailerActivity::class.java)
                intent.putExtra("data", model.data)
                val options = ActivityOptions
                    .makeSceneTransitionAnimation(this,
                        UtilPair.create(binding.photo, "photo"),
                        UtilPair.create(binding.title, "title"),
                        UtilPair.create(binding.description, "description")
                    )
                startActivity(intent, options.toBundle())
            }
        }
    }
}