package com.gurureddy.greedygameassigment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.assignment.Api.RetrofitInstance
import com.gurureddy.greedygameassigment.model.Tag
import com.gurureddy.greedygameassigment.model.TopTagResponse
import com.gurureddy.greedygameassigment.utils.Constants.FAILED
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel : ViewModel() {

    private val _tagList = MutableLiveData<List<Tag>>()
    val tagList: LiveData<List<Tag>> = _tagList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    var isListExpanded = false

    fun fetchData() {
        fetchTopTags()
    }

    private fun fetchTopTags() {
        _isLoading.value = true
        val response = RetrofitInstance.api.getTopTags()
        response.enqueue(object : Callback<TopTagResponse> {
            override fun onResponse(
                call: Call<TopTagResponse>,
                response: Response<TopTagResponse>
            ) {
                if (response.isSuccessful) {
                    val topTags = response.body()?.toptags?.tag
                    _tagList.value = topTags?.subList(1, topTags.size)
                    _isLoading.value = false
                } else {
                    _errorMessage.value = FAILED
                    _isLoading.value = false
                }
            }

            override fun onFailure(call: Call<TopTagResponse>, t: Throwable) {
                _errorMessage.value = FAILED
                _isLoading.value = false
            }
        })
    }

    fun toggleList() {
        _tagList.value?.let { tagList ->
            isListExpanded = !isListExpanded
            _tagList.value = if (isListExpanded) {
                tagList
            } else {
                tagList.subList(0, 9)
            }
        }
    }
}
