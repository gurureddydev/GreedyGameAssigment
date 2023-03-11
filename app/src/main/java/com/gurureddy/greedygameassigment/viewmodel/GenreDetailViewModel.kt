package com.gurureddy.greedygameassigment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.assignment.Api.RetrofitInstance
import com.gurureddy.greedygameassigment.model.*
import com.gurureddy.greedygameassigment.utils.Constants.FAILED
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GenreDetailViewModel : ViewModel() {

    private val _tracks = MutableLiveData<List<Track>>()
    val tracks: LiveData<List<Track>>
        get() = _tracks

    private val _artists = MutableLiveData<List<Artist>>()
    val artists: LiveData<List<Artist>>
        get() = _artists

    private val _albums = MutableLiveData<List<Album>>()
    val albums: LiveData<List<Album>>
        get() = _albums

    private val _tagName = MutableLiveData<String>()

    var tagName: String
        get() = _tagName.value ?: ""
        set(value) {
            _tagName.value = value
        }

    private val _tagInfoResponse = MutableLiveData<TagInfoResponse>()
    val tagInfoResponse: LiveData<TagInfoResponse>
        get() = _tagInfoResponse

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun fetchData() {
        getTagInfo()
        getTopAlbums()
        getTopArtists()
        getTopTracks()
    }

    private fun getTopTracks() {
        RetrofitInstance.api.getTagTracks(tagName).enqueue(object : Callback<TopTrackResponse> {
            override fun onResponse(
                call: Call<TopTrackResponse>,
                response: Response<TopTrackResponse>
            ) {
                response.body()?.let {
                    _tracks.value = it.tracks.track
                }
            }

            override fun onFailure(call: Call<TopTrackResponse>, t: Throwable) {}
        })
    }

    private fun getTopArtists() {
        RetrofitInstance.api.getTagArtists(tagName).enqueue(object : Callback<TopArtistResponse> {
            override fun onResponse(
                call: Call<TopArtistResponse>,
                response: Response<TopArtistResponse>
            ) {
                response.body()?.let {
                    _artists.value = it.topartists.artist
                }
            }

            override fun onFailure(call: Call<TopArtistResponse>, t: Throwable) {
                _errorMessage.value = "$FAILED $t"
            }
        })
    }


    private fun getTopAlbums() {

        RetrofitInstance.api.getTopAlbums(tagName).enqueue(object : Callback<TopAlbumResponse> {
            override fun onResponse(
                call: Call<TopAlbumResponse>,
                response: Response<TopAlbumResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val topAlbumResponse = response.body()!!
                    _albums.value = topAlbumResponse.albums.album
                }
            }

            override fun onFailure(call: Call<TopAlbumResponse>, t: Throwable) {
                _errorMessage.value = "$FAILED $t"
            }
        })
    }


    private fun getTagInfo() {
        val response = RetrofitInstance.api.getTagInfo(tagName)
        response.enqueue(object : Callback<TagInfoResponse> {
            override fun onResponse(
                call: Call<TagInfoResponse>,
                response: Response<TagInfoResponse>
            ) {
                if (response.body() != null) {
                    val tagInfoResponse = response.body()!!
                    _tagInfoResponse.value = tagInfoResponse
                }
            }

            override fun onFailure(call: Call<TagInfoResponse>, t: Throwable) {
                _errorMessage.value = "$FAILED $t"
            }
        })
    }
}
