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
import com.rappi.emovie.databinding.NodeGenreBinding
import com.rappi.emovie.databinding.NodeMovieBinding
import com.rappi.emovie.domain.model.Genre
import com.rappi.emovie.domain.model.Movie
import com.rappi.emovie.utils.Constants

class GenresViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = NodeGenreBinding.bind(view)

    fun render(genre: Genre) {
        if  (genre.id != Constants.loading){
            binding.title.text = genre.name
            binding.shimmerViewContainer.stopShimmer()
            binding.shimmerViewContainer.visibility = View.GONE
        }else {
            binding.shimmerViewContainer.startShimmer()
        }
    }

}