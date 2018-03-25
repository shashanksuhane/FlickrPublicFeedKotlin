package com.suhane.flickr.viewmodel

import com.suhane.flickr.repository.model.photos.getinfo.Photo
import com.suhane.flickr.ui.base.BaseViewModel

/**
 * Created by shashanksuhane on 04/02/18.
 */

interface IPhotoInfoViewModel : BaseViewModel {

    fun init(view: View)
    fun getInfo(photoId: String)

    interface View {
        fun load(photo: Photo)
        fun error(error: String)
    }
}
