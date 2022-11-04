package com.rappi.emovie.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rappi.emovie.R
import com.rappi.emovie.domain.model.Genre
import kotlin.properties.Delegates

@SuppressLint("NotifyDataSetChanged")
class GenresAdapter() : RecyclerView.Adapter<GenresViewHolder>() {

    var genres: List<Genre> by Delegates.observable(emptyList()) { _, _, _ -> notifyDataSetChanged() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return GenresViewHolder(layoutInflater.inflate(R.layout.node_genre, parent, false))
    }

    override fun onBindViewHolder(holder: GenresViewHolder, position: Int) {
        val item = genres[position]
        holder.render(item, position == genres.lastIndex)
    }

    override fun getItemCount(): Int = genres.size

}