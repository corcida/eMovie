package com.rappi.emovie.adapter

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.rappi.emovie.databinding.NodeMovieBinding
import com.rappi.emovie.domain.model.Movie
import com.rappi.emovie.utils.Constants

class MoviesViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = NodeMovieBinding.bind(view)

    fun render(movie: Movie, onClickListener : (Movie) -> Unit) {
        if  (movie.id != "loading"){
            val url = Constants.imageUrl + movie.poster_path
            Glide.with(binding.photo.context).load(url).into(binding.photo)
            binding.shimmerViewContainer.stopShimmer()
            binding.photo.visibility = View.VISIBLE
            binding.shimmerViewContainer.visibility = View.GONE
        }else {
            binding.shimmerViewContainer.startShimmer()
        }
        itemView.setOnClickListener { onClickListener(movie) }
    }

}