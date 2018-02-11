package com.suhane.flickr.repository.data.remote;

import android.content.ComponentName;

import com.suhane.flickr.FlickrPFApplication;
import com.suhane.flickr.common.Constants;
import com.suhane.flickr.repository.data.PhotoSearchAPI;
import com.suhane.flickr.repository.model.photos.search.SearchResponse;

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

public class PhotoSearchServerAPI implements PhotoSearchAPI {

    private static final String SEARCH_QUERY = "/services/rest/";
    private static final String FORMAT_PARAM_NAME = "format";
    private static final String NOJSONCB_PARAM_NAME = "nojsoncallback";
    private static final String API_KEY_PARAM_NAME = "api_key";
    private static final String METHOD_PARAM_NAME = "method";
    private static final String TAGS_PARAM_NAME = "tags";
    private static final String PER_PAGE_PARAM_NAME = "per_page";
    private static final String PAGE_PARAM_NAME = "page";
    private static final String EXTRAS_PARAM_NAME = "extras";
    private static final String TEXT_PARAM_NAME = "text";


    @Inject
    Retrofit retrofit;

    public PhotoSearchServerAPI() {
        FlickrPFApplication.getApp().remoteClientComponent().inject(this);
    }

    @Override
    public boolean search(String searchText, String page, Observer observer) {

        if (searchText == null || observer == null) return false;

        PhotoSearchService api = retrofit.create(PhotoSearchService.class);

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
                .subscribe(observer);

        return true;
    }

    /*
     * Interface for search api
    */
    private interface PhotoSearchService {
        @GET(SEARCH_QUERY)
        Observable<SearchResponse> search(@Query(FORMAT_PARAM_NAME) String format,
                                          @Query(NOJSONCB_PARAM_NAME) String noJsonCallback,
                                          @Query(API_KEY_PARAM_NAME) String apiKey,
                                          @Query(METHOD_PARAM_NAME) String method,
                                          @Query(TEXT_PARAM_NAME) String text,
                                          @Query(TAGS_PARAM_NAME) String tags,
                                          @Query(PER_PAGE_PARAM_NAME) String perPage,
                                          @Query(PAGE_PARAM_NAME) String page,
                                          @Query(EXTRAS_PARAM_NAME) String extras);
    }
}
