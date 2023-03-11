package com.gurureddy.greedygameassigment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gurureddy.greedygameassigment.model.Track
import com.gurureddy.greedygameassigment.databinding.TrackLayoutBinding

class TrackAdapter(
    val list: MutableList<Track>
) : RecyclerView.Adapter<TrackAdapter.ViewHolder>() {

    class  ViewHolder(val binding: TrackLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return  ViewHolder(binding = TrackLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val track=list[position]
        val imageSize = track.image.size
        holder.binding.title.text=track.name
        holder.binding.artist.text=track.artist.name
        Glide.with(holder.binding.image).load(track.image[imageSize-1].text).into(holder.binding.image)
    }

    override fun getItemCount()=list.size
}