package com.suhane.flickr.ui.photos

import com.suhane.flickr.repository.model.photos.search.Photo

/**
 * Created by shashanksuhane on 11/02/18.
 */

interface PhotoViewListener {
    fun onLoadMore()
    fun onItemClick(photo: Photo)
}
