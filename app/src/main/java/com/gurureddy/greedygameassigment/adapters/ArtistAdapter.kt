package com.gurureddy.greedygameassigment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gurureddy.greedygameassigment.model.Artist
import com.gurureddy.greedygameassigment.databinding.ArtistLayoutBinding

class ArtistAdapter(
    val list: MutableList<Artist>,
    val listener: ArtistItemClickListener
) : RecyclerView.Adapter<ArtistAdapter.ViewHolder>() {

    class ViewHolder(val binding: ArtistLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(artist: Artist, listener: ArtistItemClickListener) {
            binding.name.text = artist.name
            val imageSize = artist.image.size
            Glide.with(binding.image).load(artist.image[imageSize - 1].text)
                .into(binding.image)
            binding.artistRootLayout.setOnClickListener {
                listener.artistItemClick(artist)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            binding = ArtistLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val artist = list[position]
        holder.bind(artist, listener)
    }

    override fun getItemCount() = list.size
}

interface ArtistItemClickListener {
    fun artistItemClick(artist: Artist)
}