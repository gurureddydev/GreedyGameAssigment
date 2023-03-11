package com.gurureddy.greedygameassigment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gurureddy.greedygameassigment.model.Album
import com.gurureddy.greedygameassigment.databinding.AlbumLayoutBinding
import com.gurureddy.greedygameassigment.view.ArtistInfoActivity
import com.gurureddy.greedygameassigment.view.GenreDetailActivity

class AlbumAdapter(
    val list: MutableList<Album>,
    val listener: GenreDetailActivity
) : RecyclerView.Adapter<AlbumAdapter.ViewHolder>() {

    class ViewHolder(val binding: AlbumLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Album,listener: AlbumItemClickListener) {
            binding.title.text = album.name
            binding.artist.text = album.artist.name
            val imageSize = album.image.size
            Glide.with(binding.image)
                .load(album.image[imageSize - 1].text)
                .into(binding.image)

            binding.rootLayout.setOnClickListener {
                listener.albumItemClick(album)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            binding = AlbumLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val album = list[position]
        holder.bind(album,listener)
    }

    override fun getItemCount() = list.size
}

interface AlbumItemClickListener {
    fun albumItemClick(album: Album)
}



