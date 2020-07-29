package com.dipti.sweettweettest.viewmodel

import androidx.lifecycle.ViewModel
import com.dipti.sweettweettest.repo.TweetRepository
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.liveData
import com.dipti.sweettweettest.network.Resource

class SweetTweetViewModel(private val tweetRepository: TweetRepository) : ViewModel() {

    fun getTweets(search : String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = tweetRepository.getTweets(search)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}