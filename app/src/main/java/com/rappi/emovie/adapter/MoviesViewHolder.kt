package com.rappi.emovie.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rappi.emovie.databinding.NodeMovieBinding
import com.rappi.emovie.domain.model.Movie
import com.rappi.emovie.utils.Constants

class MoviesViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = NodeMovieBinding.bind(view)

    fun render(movie: Movie) {
        if  (movie.id != "loading"){
            binding.shimmerViewContainer.stopShimmer()
            Glide.with(binding.photo.context)
                .load(Constants.url + movie.poster_path).into(binding.photo)
        }else {
            binding.shimmerViewContainer.startShimmer()
        }
    }

}