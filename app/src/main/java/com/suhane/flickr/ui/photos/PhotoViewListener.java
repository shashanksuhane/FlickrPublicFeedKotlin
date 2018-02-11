package com.suhane.flickr.ui.photos;

import com.suhane.flickr.repository.model.photos.search.Photo;

/**
 * Created by shashanksuhane on 11/02/18.
 */

public interface PhotoViewListener {
    void onLoadMore();
    void onItemClick(Photo photo);
}
