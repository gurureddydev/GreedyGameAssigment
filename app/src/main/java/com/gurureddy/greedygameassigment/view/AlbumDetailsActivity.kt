package com.gurureddy.greedygameassigment.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.gurureddy.greedygameassigment.R
import com.gurureddy.greedygameassigment.databinding.ActivityAlbumDetailsBinding
import com.gurureddy.greedygameassigment.utils.Constants
import com.gurureddy.greedygameassigment.viewmodel.AlbumDetailsViewModel

class AlbumDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlbumDetailsBinding
    private lateinit var viewModel: AlbumDetailsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_album_details)
        viewModel = ViewModelProvider(this)[AlbumDetailsViewModel::class.java]

        val extraAlbum = intent.getStringExtra(Constants.EXTRA_ALBUM)
        if (extraAlbum != null) {
            viewModel.extraAlbum = extraAlbum
        }

        initObserverData()
        viewModel.fetchData()
        tagList()
    }

    private fun initObserverData() {

        viewModel.apply {
            binding.apply {
                albums.observe(this@AlbumDetailsActivity, Observer { albumList ->
                    val album = albumList.firstOrNull()
                    if (album != null) {
                        // Load the album image using Glide
                        val imageUrl =
                            album.image.firstOrNull { it.size == "extralarge" }?.text ?: ""
                        Glide.with(coverImage)
                            .load(imageUrl)
                            .into(coverImage)

                        // Set the text of the TextViews
                        albumTv.text = album.name
                        artistTitleTv.text = album.artist.name

                    }

                    tagInfoResponse.observe(this@AlbumDetailsActivity, Observer { tagInfoResponse ->
                        artistDescriptionTv.text = tagInfoResponse.tag.wiki.summary
                    })
                })
            }
        }
    }

    private fun tagList() {
        viewModel.tagList.observe(this@AlbumDetailsActivity) { tagList ->
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