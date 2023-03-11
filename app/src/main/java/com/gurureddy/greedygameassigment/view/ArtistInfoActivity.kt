package com.gurureddy.greedygameassigment.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.gurureddy.greedygameassigment.R
import com.gurureddy.greedygameassigment.adapters.TrackAdapter
import com.gurureddy.greedygameassigment.databinding.ActivityArtistInfoBinding
import com.gurureddy.greedygameassigment.model.Track
import com.gurureddy.greedygameassigment.utils.Constants
import com.gurureddy.greedygameassigment.viewmodel.ArtistInfoViewModel


class ArtistInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArtistInfoBinding
    private lateinit var viewModel: ArtistInfoViewModel
    private val trackAdapter: TrackAdapter by lazy { TrackAdapter(tracks) }
    var tracks: MutableList<Track> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_artist_info)
        viewModel = ViewModelProvider(this)[ArtistInfoViewModel::class.java]

        val extraArtist = intent.getStringExtra(Constants.EXTRA_ARTIST)
        if (extraArtist != null) {
            viewModel.extraArtist = extraArtist
        }

        initViewModel()
        viewModel.fetchData()
        tagList()
    }

    private fun initViewModel() {
        binding.recyclerView.adapter = trackAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        viewModel.apply {
            binding.apply {
                artists.observe(this@ArtistInfoActivity, Observer { albumList ->
                    val artist = albumList.firstOrNull()
                    if (artist != null) {
                        // Load the album image using Glide
                        val imageUrl =
                            artist.image.firstOrNull { it.size == "extralarge" }?.text ?: ""
                        Glide.with(coverImage)
                            .load(imageUrl)
                            .into(coverImage)
                        artistTitleTv.text = artist.name
                    }
                })

                tracks.observe(this@ArtistInfoActivity, Observer { tracks ->
                    if (tracks != null) {
                        trackAdapter.list.addAll(tracks)
                        trackAdapter.notifyDataSetChanged()
                    }
                })

            }
        }

        viewModel.tagInfoResponse.observe(this@ArtistInfoActivity, Observer { tagInfoResponse ->
            binding.artistDescriptionTv.text = tagInfoResponse.tag.wiki.content

        })
    }

    private fun tagList() {
        viewModel.tagList.observe(this@ArtistInfoActivity) { tagList ->
            for (i in tagList.indices) {
                if (i == 10) {
                    break
                }
                val tag = tagList[i]
                val chip = Chip(this)
                chip.text = tag.name

                chip.setChipBackgroundColorResource(R.color.teal_200)
                chip.setOnClickListener {
                    val intent = Intent(this, GenreDetailActivity::class.java)
                    intent.putExtra(Constants.TAG, tag.name)
                    startActivity(intent)
                }
                binding.chipGroups.addView(chip)
            }
        }
    }
}
