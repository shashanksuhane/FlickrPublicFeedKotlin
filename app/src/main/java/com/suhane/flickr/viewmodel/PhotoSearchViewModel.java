package com.suhane.flickr.viewmodel;

import android.util.Log;

import com.suhane.flickr.repository.data.remote.PhotoSearchServerAPI;
import com.suhane.flickr.repository.model.photos.search.Photo;
import com.suhane.flickr.repository.model.photos.search.Photos;
import com.suhane.flickr.repository.model.photos.search.SearchResponse;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by shashanksuhane on 04/02/18.
 */

public class PhotoSearchViewModel implements IPhotoSearchViewModel {
    View view;

    HashMap<String, Boolean> searchQueryStatus;
    HashMap<String, Integer> searchQueryPageCounter;
    HashMap<String, List<Photo>> searchResult;

    @Inject
    PhotoSearchViewModel(){
        searchQueryStatus = new HashMap<>();
        searchQueryPageCounter = new HashMap<>();
        searchResult = new HashMap<>();
    };

    @Override
    public void init(View view) {
        this.view = view;
    }

    @Override
    public void search(final String searchText) {
        // if result can't be shown, better not retrieve it
        if (view == null || isSearchQueryLoading(searchText)) {
            return;
        }

        String page = getPageCounter(searchText);

        Observer<SearchResponse> observer = new Observer<SearchResponse>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i("FPF", "onSubscribe");
            }

            @Override
            public void onNext(SearchResponse response) {
                if (response.getStat().equalsIgnoreCase("ok")
                        && response.getPhotos().getPhoto().size() > 0) {
                    view.load(searchText, getConsolidatedPhotos(searchText, response.getPhotos()));
                } else {
                    view.error(searchText, "Fail to search photos");
                }
                searchQueryStatus.put(searchText, false);
            }

            @Override
            public void onError(Throwable e) {
                view.error(searchText, e.toString());
                searchQueryStatus.put(searchText, false);
            }

            @Override
            public void onComplete() {
                Log.i("FPF", "onComplete");
            }
        };

        new PhotoSearchServerAPI().search(searchText, page, observer);
        searchQueryStatus.put(searchText, true);
    }

    private Photos getConsolidatedPhotos(String searchText, Photos photos) {
        List<Photo> photoList;

        if (searchResult.containsKey(searchText)) {
            photoList = searchResult.get(searchText);
            photoList.addAll(photos.getPhoto());

        } else {
            photoList = photos.getPhoto();
        }

        searchResult.put(searchText, photoList);
        photos.setPhoto(photoList);
        return photos;
    }

    private String getPageCounter(String searchText) {
        int ctr = 1;
        if (searchQueryPageCounter.containsKey(searchText)) {
            ctr = searchQueryPageCounter.get(searchText) + 1;
        }
        searchQueryPageCounter.put(searchText, ctr);
        return String.valueOf(ctr);
    }

    private boolean isSearchQueryLoading(String searchText) {
        if (searchQueryStatus.containsKey(searchText)) {
            return searchQueryStatus.get(searchText);
        } else {
            return false;
        }
    }
}
