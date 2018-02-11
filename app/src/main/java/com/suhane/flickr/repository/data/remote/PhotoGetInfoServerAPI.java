package com.suhane.flickr.repository.data.remote;

import com.suhane.flickr.FlickrPFApplication;
import com.suhane.flickr.common.Constants;
import com.suhane.flickr.repository.data.PhotoGetInfoAPI;
import com.suhane.flickr.repository.model.photos.getinfo.GetInfoResponse;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by shashanksuhane on 04/02/18.
 */

public class PhotoGetInfoServerAPI implements PhotoGetInfoAPI {

    private static final String GET_INFO_QUERY = "/services/rest/?format=json&nojsoncallback=1&api_key";
    private static final String FORMAT_PARAM_NAME = "format";
    private static final String NOJSONCB_PARAM_NAME = "nojsoncallback";
    private static final String API_KEY_PARAM_NAME = "api_key";
    private static final String METHOD_PARAM_NAME = "method";
    private static final String PHOTO_ID_PARAM_NAME = "photo_id";

    @Inject
    Retrofit retrofit;

    public PhotoGetInfoServerAPI() {
        FlickrPFApplication.getApp().remoteClientComponent().inject(this);
    }

    @Override
    public boolean get(String photoId, Observer observer) {

        PhotoInfoService api = retrofit.create(PhotoInfoService.class);

        api.search("json", "1", Constants.API_KEY, "flickr.photos.getInfo",
                "mode")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

        return true;
    }

    /*
     * Interface for search api
    */
    private interface PhotoInfoService {
        @GET(GET_INFO_QUERY)
        Observable<GetInfoResponse> search(@Query(FORMAT_PARAM_NAME) String format,
                                           @Query(NOJSONCB_PARAM_NAME) String noJsonCallback,
                                           @Query(API_KEY_PARAM_NAME) String apiKey,
                                           @Query(METHOD_PARAM_NAME) String method,
                                           @Query(PHOTO_ID_PARAM_NAME) String photoId);
    }
}
