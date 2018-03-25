package com.suhane.flickr.repository.data

import com.suhane.flickr.repository.model.photos.getinfo.GetInfoResponse
import io.reactivex.Observer

/**
 * Created by shashanksuhane on 04/02/18.
 */

interface PhotoGetInfoAPI {
    /*
     * To search the response for the procedure api
     * and return the response as result observer
     */
    operator fun get(photoId: String, observer: Observer<GetInfoResponse>): Boolean
}
