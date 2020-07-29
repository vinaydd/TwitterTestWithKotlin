package com.dipti.sweettweettest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dipti.sweettweettest.network.ApiHelper
import com.dipti.sweettweettest.repo.TweetRepository

class ViewModelFactory (private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SweetTweetViewModel::class.java)) {
            return SweetTweetViewModel(TweetRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}
