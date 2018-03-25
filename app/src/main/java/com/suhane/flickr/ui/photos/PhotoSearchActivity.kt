package com.suhane.flickr.ui.photos

import android.content.Intent
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.widget.Toast

import com.suhane.flickr.FlickrPFApplication
import com.suhane.flickr.R
import com.suhane.flickr.common.Constants
import com.suhane.flickr.di.components.DaggerPhotoSearchComponent
import com.suhane.flickr.di.components.PhotoSearchComponent
import com.suhane.flickr.di.modules.ActivityModule
import com.suhane.flickr.repository.model.photos.search.Photo
import com.suhane.flickr.repository.model.photos.search.Photos
import com.suhane.flickr.ui.base.BaseActivity
import com.suhane.flickr.viewmodel.PhotoSearchViewModel

import javax.inject.Inject

import butterknife.BindView
import com.suhane.flickr.viewmodel.IPhotoSearchViewModel

/**
 * Created by shashanksuhane on 04/02/18.
 */

class PhotoSearchActivity : BaseActivity(), IPhotoSearchViewModel.View {

    var component: PhotoSearchComponent? = null

    var viewModel: PhotoSearchViewModel? = null
        @Inject set

    @BindView(R.id.toolbar) @Nullable
    @JvmField
    var toolbar: Toolbar? = null

    @BindView(R.id.recyclerview_kittens) @Nullable
    @JvmField
    var photoViewKittens: RecyclerView? = null

    @BindView(R.id.recyclerview_dogs) @Nullable
    @JvmField
    var photoViewDogs: RecyclerView? = null

    @BindView(R.id.recyclerview_public_feed) @Nullable
    @JvmField
    var photoViewPublicFeed: RecyclerView? = null

    private var kittensAdapter: PhotoViewAdapter? = null
    private var dogsAdapter: PhotoViewAdapter? = null
    private var publicFeedAdapter: PhotoViewAdapter? = null

    private var kittenPhotos: Photos? = null
    private var dogPhotos: Photos? = null
    private var publicFeedPhotos: Photos? = null

    override val layout: Int
        get() = R.layout.activity_photo_search

    override val activity: BaseActivity
        get() = this

    fun component(): PhotoSearchComponent? {
        component = DaggerPhotoSearchComponent.builder()
                    .applicationComponent((application as FlickrPFApplication).component())
                    .activityModule(ActivityModule(this))
                    .build()
        return component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component()?.inject(this)
        init()
        viewModel!!.search(Constants.SEARCH_TEXT_KITTENS)
        viewModel!!.search(Constants.SEARCH_TEXT_DOGS)
        viewModel!!.search(Constants.SEARCH_TEXT_PUBLICFEEDS)
        showProgress()
    }

    override fun onDestroy() {
        reset()
        super.onDestroy()
    }

    override fun init() {
//        setSupportActionBar(toolbar);
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(false)
            actionBar.setDisplayHomeAsUpEnabled(false)
        }
        toolbar!!.title = getString(R.string.app_name)
        viewModel!!.init(this)

        initPhotoViewKittens()
        initPhotoViewDogs()
        initPhotoViewPublicFeed()
    }

    private fun initPhotoViewKittens() {
        kittenPhotos = Photos()
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        photoViewKittens!!.layoutManager = layoutManager
        kittensAdapter = PhotoViewAdapter(kittenPhotos!!, photoViewKittens!!)
        photoViewKittens!!.adapter = kittensAdapter

        kittensAdapter!!.setPhotoViewListener(object : PhotoViewListener {
            override fun onLoadMore() {
                viewModel!!.search(Constants.SEARCH_TEXT_KITTENS)
            }

            override fun onItemClick(photo: Photo) {
                val intent = Intent(this@PhotoSearchActivity, PhotoInfoActivity::class.java)
                intent.putExtra(Constants.PARCELABLE_KEY_PHOTO, photo)
                startActivity(intent)
            }
        })

    }

    private fun initPhotoViewDogs() {
        dogPhotos = Photos()
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        photoViewDogs!!.layoutManager = layoutManager
        dogsAdapter = PhotoViewAdapter(dogPhotos!!, photoViewDogs!!)
        photoViewDogs!!.adapter = dogsAdapter

        dogsAdapter!!.setPhotoViewListener(object : PhotoViewListener {
            override fun onLoadMore() {
                viewModel!!.search(Constants.SEARCH_TEXT_DOGS)
            }

            override fun onItemClick(photo: Photo) {
                val intent = Intent(this@PhotoSearchActivity, PhotoInfoActivity::class.java)
                intent.putExtra(Constants.PARCELABLE_KEY_PHOTO, photo)
                startActivity(intent)
            }
        })
    }

    private fun initPhotoViewPublicFeed() {
        publicFeedPhotos = Photos()
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        photoViewPublicFeed!!.layoutManager = layoutManager
        publicFeedAdapter = PhotoViewAdapter(publicFeedPhotos!!, photoViewPublicFeed!!)
        photoViewPublicFeed!!.adapter = publicFeedAdapter

        publicFeedAdapter!!.setPhotoViewListener(object : PhotoViewListener {
            override fun onLoadMore() {
                viewModel!!.search("")
            }

            override fun onItemClick(photo: Photo) {
                val intent = Intent(this@PhotoSearchActivity, PhotoInfoActivity::class.java)
                intent.putExtra(Constants.PARCELABLE_KEY_PHOTO, photo)
                startActivity(intent)
            }
        })
    }

    override fun reset() {}

    override fun load(searchText: String, photos: Photos?) {
        hideProgress()

        if (searchText.equals(Constants.SEARCH_TEXT_KITTENS, ignoreCase = true)) {
            kittenPhotos!!.photo = photos?.photo
            kittensAdapter!!.notifyDataSetChanged()
        } else if (searchText.equals(Constants.SEARCH_TEXT_DOGS, ignoreCase = true)) {
            dogPhotos!!.photo = photos?.photo
            dogsAdapter!!.notifyDataSetChanged()
        } else {
            publicFeedPhotos!!.photo = photos?.photo
            publicFeedAdapter!!.notifyDataSetChanged()
        }

    }

    override fun error(searchText: String, error: String) {
        hideProgress()
        val msg = String.format(getString(R.string.search_photo_error), searchText, error)
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
