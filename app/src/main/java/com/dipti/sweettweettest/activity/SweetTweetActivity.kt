package com.dipti.sweettweettest.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.dipti.sweettweettest.R
import com.dipti.sweettweettest.adapter.TweetAdapter
import com.dipti.sweettweettest.model.Tweet
import com.dipti.sweettweettest.network.ApiHelper
import com.dipti.sweettweettest.network.RetrofitBuilder
import com.dipti.sweettweettest.network.Status
import com.dipti.sweettweettest.viewmodel.SweetTweetViewModel
import com.dipti.sweettweettest.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_sweet_tweet.*
import java.util.*


class SweetTweetActivity : AppCompatActivity() {

    lateinit var viewModel: SweetTweetViewModel
    lateinit var adapter: TweetAdapter
    lateinit var tweetsList: List<Tweet>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sweet_tweet)
        tweetsList= mutableListOf()
        setupListeners()
        setupViewModel()
        setupUI()
    }
    private fun setupListeners(){
        etTweetSearch.addTextChangedListener(
            object : TextWatcher {
                override fun onTextChanged(
                    s: CharSequence,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                }

                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }
                override fun afterTextChanged(s: Editable) {
                    setupObservers(etTweetSearch.text.toString())
                }
            }
        )
        rgSort.setOnCheckedChangeListener { group, checkedId -> retrieveList() }
    }
    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(SweetTweetViewModel::class.java)
    }

    private fun setupUI() {
        rvTweet.layoutManager = LinearLayoutManager(this)
        adapter = TweetAdapter(arrayListOf())
//        rvTweet.addItemDecoration(
//            DividerItemDecoration(
//                rvTweet.context,
//                (rvTweet.layoutManager as LinearLayoutManager).orientation
//            )
//        )
        rvTweet.adapter = adapter
    }

    private fun setupObservers(search : String) {
        viewModel.getTweets(search).observe(this, Observer {
            it?.let { resource ->

                when (resource.status) {
                    Status.SUCCESS -> {

                        rvTweet.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        resource.data?.let { tweets -> {
                            tweetsList = tweets.statuses
                            Log.e("MyList",tweetsList.toString())
                            retrieveList()
                        } }
                    }
                    Status.ERROR -> {
                        rvTweet.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                        rvTweet.visibility = View.INVISIBLE
                    }
                }
            }
        })
    }

    private fun retrieveList() {
        if (rdRetweetCount.isChecked){
            Collections.sort(tweetsList, Comparator { obj1, obj2 ->
                // ## Ascending order
                return@Comparator Integer.valueOf(obj1.retweet_count).compareTo(Integer.valueOf(obj1.retweet_count)); // To compare integer values

                     })
        }else if (rdFavoriteCout.isChecked){
            Collections.sort(tweetsList, Comparator { obj1, obj2 ->
                // ## Ascending order
                return@Comparator Integer.valueOf(obj2.favorite_count).compareTo(Integer.valueOf(obj1.favorite_count)); // To compare integer values

            })
        }
        adapter.apply {
            addTweets(tweetsList)
            notifyDataSetChanged()
        }
    }
}
