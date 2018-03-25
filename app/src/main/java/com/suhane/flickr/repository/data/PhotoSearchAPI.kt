package com.suhane.flickr.repository.data

import com.suhane.flickr.repository.model.photos.search.SearchResponse
import io.reactivex.Observer

/**
 * Created by shashanksuhane on 04/02/18.
 */

interface PhotoSearchAPI {
    /*
     * To search the response for the procedure api
     * and return the response as result observer
     */
    fun search(searchText: String, page: String, observer: Observer<SearchResponse>): Boolean
}
