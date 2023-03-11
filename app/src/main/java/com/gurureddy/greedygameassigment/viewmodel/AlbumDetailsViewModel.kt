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

class AlbumDetailsViewModel : ViewModel() {

    private val _tagList = MutableLiveData<List<Tag>>()
    val tagList: LiveData<List<Tag>> = _tagList


    private val _albums = MutableLiveData<List<Album>>()
    val albums: LiveData<List<Album>>
        get() = _albums

    private val _extraAlbum = MutableLiveData<String>()

    var extraAlbum: String
        get() = _extraAlbum.value ?: ""
        set(value) {
            _extraAlbum.value = value
        }

    private val _tagInfoResponse = MutableLiveData<TagInfoResponse>()
    val tagInfoResponse: LiveData<TagInfoResponse>
        get() = _tagInfoResponse

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage


    fun fetchData() {
        fetchTopTags()
        getTopAlbums()
    }

    private fun getTopAlbums() {
        RetrofitInstance.api.getTopAlbums(extraAlbum).enqueue(object : Callback<TopAlbumResponse> {
            override fun onResponse(
                call: Call<TopAlbumResponse>,
                response: Response<TopAlbumResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val topAlbumResponse = response.body()!!
                    _albums.value = topAlbumResponse.albums.album
                } else {
                    _errorMessage.value = Constants.FAILED
                }
            }

            override fun onFailure(call: Call<TopAlbumResponse>, t: Throwable) {
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