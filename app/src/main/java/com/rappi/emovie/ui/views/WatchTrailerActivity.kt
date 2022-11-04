package com.rappi.emovie.ui.views

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.rappi.emovie.databinding.ActivityWatchTrailerBinding
import com.rappi.emovie.ui.vm.WatchTrailerViewModel
import com.rappi.emovie.ui.vm.WatchTrailerViewModel.*
import com.rappi.emovie.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WatchTrailerActivity : AppCompatActivity() {

    private lateinit var binding : ActivityWatchTrailerBinding
    private val viewModel : WatchTrailerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWatchTrailerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setViews()
        viewModel.model.observe(this, Observer(this::viewModelObserver))
        viewModel.onCreate(intent.getStringExtra("data").toString())
    }

    private fun setViews(){
        binding.back.setOnClickListener {  onBackPressedDispatcher.onBackPressed() }
        lifecycle.addObserver(binding.youtubePlayerView)
    }

    private fun viewModelObserver(model : UiModel){
        when (model) {
            is UiModel.Loading -> {
                if (model.value){
                    binding.shimmerViewContainer.startShimmer()
                } else {
                    binding.shimmerViewContainer.stopShimmer()
                    binding.shimmerViewContainer.visibility = View.GONE
                }
            }
            is UiModel.MovieData -> {
                val url = Constants.imageUrl + model.data.poster_path
                Glide.with(binding.photo.context).load(url).into(binding.photo)
                binding.title.text = model.data.title
                binding.description.text = model.data.overview
            }
            is UiModel.VideoData -> {
                model.data?.let {
                    lifecycle.addObserver(binding.youtubePlayerView)
                    binding.youtubePlayerView.visibility =  View.VISIBLE
                    binding.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener(){
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            super.onReady(youTubePlayer)
                            youTubePlayer.loadVideo(it.key, 0f)
                        }
                    })
                }?:run{
                    binding.noVideo.visibility = View.VISIBLE
                    binding.youtubePlayerView.visibility =  View.GONE
                }
            }
        }
    }
}