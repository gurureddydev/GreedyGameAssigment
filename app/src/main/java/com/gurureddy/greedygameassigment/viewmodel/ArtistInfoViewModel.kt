package com.gurureddy.greedygameassigment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.assignment.Api.RetrofitInstance
import com.gurureddy.greedygameassigment.model.*
import com.gurureddy.greedygameassigment.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArtistInfoViewModel : ViewModel() {

    private val _artists = MutableLiveData<List<Artist>>()
    val artists: LiveData<List<Artist>>
        get() = _artists

    private val _tracks = MutableLiveData<List<Track>>()
    val tracks: LiveData<List<Track>>
        get() = _tracks

    private val _tagList = MutableLiveData<List<Tag>>()
    val tagList: LiveData<List<Tag>> = _tagList

    private val _extraArtist = MutableLiveData<String>()

    var extraArtist: String
        get() = _extraArtist.value ?: ""
        set(value) {
            _extraArtist.value = value
        }

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _tagInfoResponse = MutableLiveData<TagInfoResponse>()
    val tagInfoResponse: LiveData<TagInfoResponse>
        get() = _tagInfoResponse


    fun fetchData() {
        getTopArtists()
        getTagInfo()
        getTopTracks()
        fetchTopTags()
    }

    private fun getTopArtists() {
        RetrofitInstance.api.getTagArtists(extraArtist)
            .enqueue(object : Callback<TopArtistResponse> {
                override fun onResponse(
                    call: Call<TopArtistResponse>,
                    response: Response<TopArtistResponse>
                ) {
                    response.body()?.let {
                        _artists.value = it.topartists.artist
                    }
                }

                override fun onFailure(call: Call<TopArtistResponse>, t: Throwable) {
                    _errorMessage.value = "${Constants.FAILED} $t"

                }
            })
    }

    private fun getTopTracks() {
        RetrofitInstance.api.getTagTracks(extraArtist).enqueue(object : Callback<TopTrackResponse> {
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

    private fun getTagInfo() {
        val response = RetrofitInstance.api.getTagInfo(extraArtist)
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
                _errorMessage.value = "${Constants.FAILED} $t"
            }
        })
    }


    private fun fetchTopTags() {
        val response = RetrofitInstance.api.getTopTags()
        response.enqueue(object : Callback<TopTagResponse> {
            override fun onResponse(
                call: Call<TopTagResponse>,
                response: Response<TopTagResponse>
            ) {
                if (response.isSuccessful) {
                    val topTags = response.body()?.toptags?.tag
                    _tagList.value = topTags?.subList(1, topTags.size)
                } else {
                    _errorMessage.value = Constants.FAILED
                }
            }

            override fun onFailure(call: Call<TopTagResponse>, t: Throwable) {
                _errorMessage.value = Constants.FAILED
            }
        })
    }
}
