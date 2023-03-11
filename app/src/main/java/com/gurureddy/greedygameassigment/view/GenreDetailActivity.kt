package com.gurureddy.greedygameassigment.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.tabs.TabLayout
import com.gurureddy.greedygameassigment.R
import com.gurureddy.greedygameassigment.adapters.*
import com.gurureddy.greedygameassigment.databinding.ActivityGenreDetailBinding
import com.gurureddy.greedygameassigment.extension.showToast
import com.gurureddy.greedygameassigment.model.Album
import com.gurureddy.greedygameassigment.model.Artist
import com.gurureddy.greedygameassigment.model.Track
import com.gurureddy.greedygameassigment.utils.Constants.EXTRA_ALBUM
import com.gurureddy.greedygameassigment.utils.Constants.EXTRA_ARTIST
import com.gurureddy.greedygameassigment.utils.Constants.TAG
import com.gurureddy.greedygameassigment.viewmodel.GenreDetailViewModel

class GenreDetailActivity : AppCompatActivity(), AlbumItemClickListener, ArtistItemClickListener {

    private lateinit var binding: ActivityGenreDetailBinding
    private lateinit var viewModel: GenreDetailViewModel
    private val albumAdapter: AlbumAdapter by lazy { AlbumAdapter(albums, this) }
    private val artistAdapter: ArtistAdapter by lazy { ArtistAdapter(artists, this) }
    private val trackAdapter: TrackAdapter by lazy { TrackAdapter(tracks) }
    var albums: MutableList<Album> = mutableListOf()
    var artists: MutableList<Artist> = mutableListOf()
    var tracks: MutableList<Track> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_genre_detail)
        viewModel = ViewModelProvider(this)[GenreDetailViewModel::class.java]
        val tagName = intent.getStringExtra(TAG)
        if (tagName != null) {
            viewModel.tagName = tagName
        }

        initViews()
        fetchData()
        initObserveData()
    }

    private fun initViews() {
        binding.apply {
            recyclerView.layoutManager = GridLayoutManager(this@GenreDetailActivity, 3)
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    progressBar.visibility = View.VISIBLE // show the ProgressBar
                    when (tab?.text) {
                        ALBUMS -> recyclerView.adapter = albumAdapter
                        ARTISTS -> recyclerView.adapter = artistAdapter
                        TRACKS -> recyclerView.adapter = trackAdapter
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}

                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
        }
    }

    private fun fetchData() {
        binding.progressBar.visibility = View.VISIBLE
        showToast("Please wait while we fetch data from the API")
        viewModel.fetchData()
    }

    private fun initObserveData() {
        viewModel.apply {
            binding.apply {
                progressBar.apply {
                    albums.observe(this@GenreDetailActivity, Observer { albums ->
                        albumAdapter.list.addAll(albums)
                        visibility = View.GONE
                    })

                    artists.observe(this@GenreDetailActivity, Observer { artists ->
                        artistAdapter.list.addAll(artists)
                        visibility = View.GONE
                    })

                    tracks.observe(this@GenreDetailActivity, Observer { tracks ->
                        trackAdapter.list.addAll(tracks)
                        visibility = View.GONE
                    })

                    tagInfoResponse.observe(this@GenreDetailActivity, Observer { tagInfoResponse ->
                        tagDescription.text = tagInfoResponse.tag.wiki.summary
                        tagTitle.text = tagInfoResponse.tag.name
                        visibility = View.GONE
                    })

                    errorMessage.observe(this@GenreDetailActivity) { errorMessage ->
                        showToast(errorMessage)
                    }
                }
            }
        }
    }

    companion object {
        private const val ALBUMS = "ALBUMS"
        private const val ARTISTS = "ARTISTS"
        private const val TRACKS = "TRACKS"
    }

    override fun albumItemClick(album: Album) {
        val intent = Intent(this, AlbumDetailsActivity::class.java)
        intent.putExtra(EXTRA_ALBUM, album.name)
        startActivity(intent)
    }

    override fun artistItemClick(artist: Artist) {
        val intent = Intent(this, ArtistInfoActivity::class.java)
        intent.putExtra(EXTRA_ARTIST, artist.name)
        startActivity(intent)
    }
}
