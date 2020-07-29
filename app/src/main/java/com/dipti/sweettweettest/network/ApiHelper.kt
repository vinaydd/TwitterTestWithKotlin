package com.dipti.sweettweettest.network

import com.dipti.sweettweettest.network.ApiService

class ApiHelper (private val apiService: ApiService) {

    suspend fun getTweets(search : String) = apiService.getTweets(search,"recent")
}