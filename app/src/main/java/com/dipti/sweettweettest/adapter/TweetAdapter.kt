package com.dipti.sweettweettest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dipti.sweettweettest.R
import com.dipti.sweettweettest.model.Tweet
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_tweet.view.*

class TweetAdapter (private val tweets: ArrayList<Tweet>) : RecyclerView.Adapter<TweetAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(tweet: Tweet) {
            itemView.apply {
                tvName.text = tweet.user.name
                tvTweet.text = tweet.text
                tvRetweetCount.text = tweet.retweet_count
                tvFavoriteCount.text = tweet.favorite_count
                Picasso.get()
                    .load(tweet.user.profile_image_url_https)
                    .into(civImage);
            }
        }
    }

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_tweet, parent, false))

    override fun getItemCount(): Int = tweets.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(tweets[position])
    }

    fun addTweets(users: List<Tweet>) {
        this.tweets.apply {
            clear()
            addAll(users)
        }

    }
}