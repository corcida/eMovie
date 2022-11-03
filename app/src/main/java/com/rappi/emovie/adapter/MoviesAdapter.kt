package com.rappi.emovie.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rappi.emovie.R
import com.rappi.emovie.domain.model.Movie
import kotlin.properties.Delegates

@SuppressLint("NotifyDataSetChanged")
class MoviesAdapter(private val onClickListener : (Movie) -> Unit) : RecyclerView.Adapter<MoviesViewHolder>() {

    var movies: List<Movie> by Delegates.observable(emptyList()) { _, _, _ -> notifyDataSetChanged() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MoviesViewHolder(layoutInflater.inflate(R.layout.node_movie, parent, false))
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val item = movies[position]
        holder.render(item, onClickListener)
    }

    override fun getItemCount(): Int = movies.size

}