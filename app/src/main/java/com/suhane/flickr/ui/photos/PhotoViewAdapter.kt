package com.suhane.flickr.ui.photos

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.squareup.picasso.Picasso
import com.suhane.flickr.R
import com.suhane.flickr.repository.model.photos.search.Photo
import com.suhane.flickr.repository.model.photos.search.Photos

/**
 * Created by shashanksuhane on 11/02/18.
 */

class PhotoViewAdapter(photos: Photos, recyclerView: RecyclerView) : RecyclerView.Adapter<PhotoViewAdapter.ViewHolder>() {

    private var photos: Photos? = null

    private val visibleThreshold = 5
    private var lastVisibleItem: Int = 0
    private var totalItemCount: Int = 0
    private var photoViewListener: PhotoViewListener? = null

    init {
        this.photos = photos

        // detect and load more items
        if (recyclerView.layoutManager is LinearLayoutManager) {
            val linearLayoutManager = recyclerView
                    .layoutManager as LinearLayoutManager

            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    totalItemCount = linearLayoutManager.itemCount
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                    if (totalItemCount <= lastVisibleItem + visibleThreshold) {
                        if (photoViewListener != null) {
                            photoViewListener!!.onLoadMore()
                        }
                    }
                }
            })
        }
    }

    fun setPhotoViewListener(photoViewListener: PhotoViewListener) {
        this.photoViewListener = photoViewListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: PhotoViewAdapter.ViewHolder, position: Int) {
        if (photos != null) {
            val photo = photos?.photo?.get(position)

            if (photo != null) {

                if (photo.title != null)
                    holder.title.text = photo.title

                if (photo != null && photo.urlS != null) {
                    Picasso.with(holder.context)
                            .load(photo.urlS)
                            .fit()
                            .centerCrop()
                            .into(holder.image)
                }

                holder.image.setOnClickListener { photoViewListener!!.onItemClick(photo) }
            }
        }
    }

    override fun getItemCount() = photos?.photo?.size?: 0

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val context: Context
        val image: ImageView
        val title: TextView

        init {
            context = view.context
            image = view.findViewById(R.id.image)
            title = view.findViewById(R.id.title)
        }
    }
}
