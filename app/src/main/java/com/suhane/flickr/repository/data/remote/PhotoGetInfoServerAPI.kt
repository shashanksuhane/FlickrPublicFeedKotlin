package com.suhane.flickr.repository.data.remote

import com.suhane.flickr.FlickrPFApplication
import com.suhane.flickr.common.Constants
import com.suhane.flickr.repository.data.PhotoGetInfoAPI
import com.suhane.flickr.repository.model.photos.getinfo.GetInfoResponse

import javax.inject.Inject

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by shashanksuhane on 04/02/18.
 */

class PhotoGetInfoServerAPI : PhotoGetInfoAPI {

    var retrofit: Retrofit? = null
        @Inject set

    init {
        FlickrPFApplication.app?.remoteClientComponent()?.inject(this)
    }

    override fun get(photoId: String, observer: Observer<GetInfoResponse>): Boolean {

        val api = retrofit!!.create(PhotoInfoService::class.java)

        api.search("json", "1", Constants.API_KEY, "flickr.photos.getInfo",
                "mode")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)

        return true
    }

    /*
     * Interface for search api
    */
    private interface PhotoInfoService {
        @GET(GET_INFO_QUERY)
        fun search(@Query(FORMAT_PARAM_NAME) format: String,
                   @Query(NOJSONCB_PARAM_NAME) noJsonCallback: String,
                   @Query(API_KEY_PARAM_NAME) apiKey: String,
                   @Query(METHOD_PARAM_NAME) method: String,
                   @Query(PHOTO_ID_PARAM_NAME) photoId: String): Observable<GetInfoResponse>
    }

    companion object {

        private const val GET_INFO_QUERY = "/services/rest/?format=json&nojsoncallback=1&api_key"
        private const val FORMAT_PARAM_NAME = "format"
        private const val NOJSONCB_PARAM_NAME = "nojsoncallback"
        private const val API_KEY_PARAM_NAME = "api_key"
        private const val METHOD_PARAM_NAME = "method"
        private const val PHOTO_ID_PARAM_NAME = "photo_id"
    }
}
