package com.suhane.flickr.viewmodel

import android.util.Log

import com.suhane.flickr.repository.data.remote.PhotoGetInfoServerAPI
import com.suhane.flickr.repository.model.photos.getinfo.GetInfoResponse

import javax.inject.Inject

import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * Created by shashanksuhane on 04/02/18.
 */

class PhotoInfoViewModel @Inject
internal constructor() : IPhotoInfoViewModel {

    private var view: IPhotoInfoViewModel.View? = null
    internal var observer: Observer<GetInfoResponse>

    init {
        observer = object : Observer<GetInfoResponse> {
            override fun onSubscribe(d: Disposable) {
                Log.i("FPF", "onSubscribe")
            }

            override fun onNext(response: GetInfoResponse) {
                if (response.stat.equals("ok", ignoreCase = true) && response.photo != null) {
                    view!!.load(response.photo!!)
                } else {
                    view!!.error("Fail to search info")
                }
            }

            override fun onError(e: Throwable) {
                view!!.error(e.toString())
            }

            override fun onComplete() {
                Log.i("FPF", "onComplete")
            }
        }

    }


    override fun init(view: IPhotoInfoViewModel.View) {
        this.view = view
    }

    override fun getInfo(photoId: String) {
        // if result can't be shown, better not retrieve it
        if (view == null) {
            return
        }

        PhotoGetInfoServerAPI().get(photoId, observer)
    }
}
