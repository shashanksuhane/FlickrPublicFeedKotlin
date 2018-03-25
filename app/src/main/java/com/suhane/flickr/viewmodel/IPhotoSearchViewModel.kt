package com.suhane.flickr.viewmodel

import com.suhane.flickr.repository.model.photos.search.Photos
import com.suhane.flickr.ui.base.BaseViewModel

/**
 * Created by shashanksuhane on 04/02/18.
 */

interface IPhotoSearchViewModel : BaseViewModel {

    fun init(view: View)
    fun search(searchText: String)

    interface View {
        fun load(searchText: String, photos: Photos?)
        fun error(searchText: String, error: String)
    }
}
