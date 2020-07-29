package com.dipti.sweettweettest.model

 data class Tweet (val id : String,
                   val text : String,
                   val retweet_count : String,
                   val favorite_count : String,
                   val user: User
)