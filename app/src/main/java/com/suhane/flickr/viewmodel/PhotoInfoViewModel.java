package com.suhane.flickr.viewmodel;

import android.util.Log;

import com.suhane.flickr.repository.data.remote.PhotoGetInfoServerAPI;
import com.suhane.flickr.repository.model.photos.getinfo.GetInfoResponse;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by shashanksuhane on 04/02/18.
 */

public class PhotoInfoViewModel implements IPhotoInfoViewModel {

    private View view;
    Observer<GetInfoResponse> observer;

    @Inject
    PhotoInfoViewModel(){
        observer = new Observer<GetInfoResponse>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i("FPF", "onSubscribe");
            }

            @Override
            public void onNext(GetInfoResponse response) {
                if (response.getStat().equalsIgnoreCase("ok")
                        && response.getPhoto() != null) {
                    view.load(response.getPhoto());
                } else {
                    view.error("Fail to search info");
                }
            }

            @Override
            public void onError(Throwable e) {
                view.error(e.toString());
            }

            @Override
            public void onComplete() {
                Log.i("FPF", "onComplete");
            }
        };

    };


    @Override
    public void init(View view) {
        this.view = view;
    }

    @Override
    public void getInfo(String photoId) {
        // if result can't be shown, better not retrieve it
        if (view == null) {
            return;
        }

        new PhotoGetInfoServerAPI().get(photoId, observer);
    }
}
