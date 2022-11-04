package com.rappi.emovie.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.rappi.emovie.databinding.NodeGenreBinding
import com.rappi.emovie.domain.model.Genre
import com.rappi.emovie.utils.Constants

class GenresViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = NodeGenreBinding.bind(view)

    fun render(genre: Genre, lastItem: Boolean) {
        if  (genre.id != Constants.loading){
            binding.title.text = genre.name
            binding.shimmerViewContainer.stopShimmer()
            binding.shimmerViewContainer.visibility = View.GONE
            binding.circle.visibility = if (lastItem) View.GONE else View.VISIBLE
        }else {
            binding.shimmerViewContainer.startShimmer()
        }
    }

}