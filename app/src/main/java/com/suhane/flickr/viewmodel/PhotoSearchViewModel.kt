package com.suhane.flickr.viewmodel

import android.util.Log

import com.suhane.flickr.repository.data.remote.PhotoSearchServerAPI
import com.suhane.flickr.repository.model.photos.search.Photo
import com.suhane.flickr.repository.model.photos.search.Photos
import com.suhane.flickr.repository.model.photos.search.SearchResponse

import java.util.HashMap

import javax.inject.Inject

import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * Created by shashanksuhane on 04/02/18.
 */

class PhotoSearchViewModel @Inject
internal constructor() : IPhotoSearchViewModel {
    internal var view: IPhotoSearchViewModel.View? = null

    internal var searchQueryStatus: HashMap<String, Boolean>
    internal var searchQueryPageCounter: HashMap<String, Int>
    internal var searchResult: HashMap<String, List<Photo>>

    init {
        searchQueryStatus = HashMap()
        searchQueryPageCounter = HashMap()
        searchResult = HashMap()
    }

    override fun init(view: IPhotoSearchViewModel.View) {
        this.view = view
    }

    override fun search(searchText: String) {
        // if result can't be shown, better not retrieve it
        if (view == null || isSearchQueryLoading(searchText)) {
            return
        }

        val page = getPageCounter(searchText)

        val observer = object : Observer<SearchResponse> {
            override fun onSubscribe(d: Disposable) {
                Log.i("FPF", "onSubscribe")
            }

            override fun onNext(response: SearchResponse) {
                if (response.stat.equals("ok", ignoreCase = true) && response.photos?.photo?.size!! > 0) {
                    view!!.load(searchText, getConsolidatedPhotos(searchText, response.photos))
                } else {
                    view!!.error(searchText, "Fail to search photos")
                }
                searchQueryStatus.put(searchText, false)
            }

            override fun onError(e: Throwable) {
                view!!.error(searchText, e.toString())
                searchQueryStatus.put(searchText, false)
            }

            override fun onComplete() {
                Log.i("FPF", "onComplete")
            }
        }

        PhotoSearchServerAPI().search(searchText, page, observer)
        searchQueryStatus.put(searchText, true)
    }

    private fun getConsolidatedPhotos(searchText: String, photos: Photos?): Photos? {
        var photoList: MutableList<Photo>

        if (searchResult.containsKey(searchText)) {
            photoList = (searchResult.get(searchText) as MutableList<Photo>?)!!
            photoList.addAll(photos?.photo!!)

        } else {
            photoList = (photos?.photo as MutableList<Photo>?)!!
        }

        searchResult.put(searchText, photoList)
        photos?.photo = photoList
        return photos
    }

    private fun getPageCounter(searchText: String): String {
        var ctr = 1
        if (searchQueryPageCounter.containsKey(searchText)) {
            ctr = searchQueryPageCounter.get(searchText)?.plus(1) ?: 1
        }
        searchQueryPageCounter.put(searchText, ctr)
        return ctr.toString()
    }

    private fun isSearchQueryLoading(searchText: String): Boolean {
        return if (searchQueryStatus.containsKey(searchText)) {
            searchQueryStatus.get(searchText)!!
        } else {
            false
        }
    }
}
