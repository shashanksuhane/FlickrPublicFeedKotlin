package com.suhane.flickr.repository.data.remote

import com.suhane.flickr.FlickrPFApplication
import com.suhane.flickr.common.Constants
import com.suhane.flickr.repository.data.PhotoSearchAPI
import com.suhane.flickr.repository.model.photos.search.SearchResponse
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject

/**
 * Created by shashanksuhane on 04/02/18.
 */

class PhotoSearchServerAPI : PhotoSearchAPI {

    var retrofit: Retrofit? = null
        @Inject set

    init {
        FlickrPFApplication.app?.remoteClientComponent()?.inject(this)
    }

    override fun search(searchText: String, page: String, observer: Observer<SearchResponse>): Boolean {

        val api = retrofit!!.create(PhotoSearchService::class.java)

        api.search(Constants.API_RESPONSE_FORMAT,
                Constants.API_SEARCH_NOJSONCALLBACK,
                Constants.API_KEY,
                Constants.API_SEARCH_METHOD,
                searchText,
                Constants.API_SEARCH_TAGS,
                Constants.API_SEARCH_PER_PAGE_COUNT,
                page,
                Constants.API_SEARCH_EXTRAS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)

        return true
    }

    /*
     * Interface for search api
    */
    private interface PhotoSearchService {
        @GET(SEARCH_QUERY)
        fun search(@Query(FORMAT_PARAM_NAME) format: String,
                   @Query(NOJSONCB_PARAM_NAME) noJsonCallback: String,
                   @Query(API_KEY_PARAM_NAME) apiKey: String,
                   @Query(METHOD_PARAM_NAME) method: String,
                   @Query(TEXT_PARAM_NAME) text: String,
                   @Query(TAGS_PARAM_NAME) tags: String,
                   @Query(PER_PAGE_PARAM_NAME) perPage: String,
                   @Query(PAGE_PARAM_NAME) page: String,
                   @Query(EXTRAS_PARAM_NAME) extras: String): Observable<SearchResponse>
    }

    companion object {

        private const val SEARCH_QUERY = "/services/rest/"
        private const val FORMAT_PARAM_NAME = "format"
        private const val NOJSONCB_PARAM_NAME = "nojsoncallback"
        private const val API_KEY_PARAM_NAME = "api_key"
        private const val METHOD_PARAM_NAME = "method"
        private const val TAGS_PARAM_NAME = "tags"
        private const val PER_PAGE_PARAM_NAME = "per_page"
        private const val PAGE_PARAM_NAME = "page"
        private const val EXTRAS_PARAM_NAME = "extras"
        private const val TEXT_PARAM_NAME = "text"
    }
}
