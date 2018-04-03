package com.suhane.flickr.ui.photos

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import butterknife.BindView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.suhane.flickr.FlickrPFApplication
import com.suhane.flickr.R
import com.suhane.flickr.common.Constants
import com.suhane.flickr.di.components.DaggerPhotoInfoComponent
import com.suhane.flickr.di.components.PhotoInfoComponent
import com.suhane.flickr.di.modules.ActivityModule
import com.suhane.flickr.repository.model.photos.getinfo.Photo
import com.suhane.flickr.ui.base.BaseActivity
import com.suhane.flickr.viewmodel.IPhotoInfoViewModel
import com.suhane.flickr.viewmodel.PhotoInfoViewModel
import javax.inject.Inject

/**
 * Created by shashanksuhane on 04/02/18.
 */

class PhotoInfoActivity : BaseActivity(), IPhotoInfoViewModel.View {

    private var component: PhotoInfoComponent? = null

    var viewModel: PhotoInfoViewModel? = null
        @Inject set

    @BindView(R.id.toolbar) @Nullable
    @JvmField
    var toolbar: Toolbar? = null

    @BindView(R.id.photo) @Nullable
    @JvmField
    var photoView: ImageView? = null

    @BindView(R.id.launch_go) @Nullable
    @JvmField
    var launchGo: Button? = null

    private var photo: com.suhane.flickr.repository.model.photos.search.Photo? = null

    override val layout: Int
        get() = R.layout.activity_photo_info

    override val activity: BaseActivity
        get() = this

    fun component(): PhotoInfoComponent? {
        if (component == null) {
            component = DaggerPhotoInfoComponent.builder()
                    .applicationComponent((application as FlickrPFApplication).component())
                    .activityModule(ActivityModule(this))
                    .build()
        }
        return component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component()?.inject(this)
        init()

        //for now just url is enough, so no need to get more info on photo
        //viewModel.getInfo(photo.getId());

        showProgress()

        Picasso.with(this)
                .load(photo!!.urlC)
                .fit()
                .centerInside()
                .into(photoView!!, object : Callback {
                    override fun onSuccess() {
                        hideProgress()
                    }

                    override fun onError() {
                        hideProgress()
                        Toast.makeText(baseContext, getText(R.string.load_data_error), Toast.LENGTH_SHORT).show()
                    }
                })

        launchGo!!.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
            if (photo!!.urlC != null) {
                i.data = Uri.parse(photo!!.urlC)
                startActivity(i)
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.action_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share -> {

                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND
                shareIntent.putExtra(Intent.EXTRA_TEXT, photo!!.title)
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(photo!!.urlC))
                shareIntent.type = "image/*"
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                startActivity(Intent.createChooser(shareIntent, resources.getText(R.string.photo_share_option)))
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        reset()
        super.onDestroy()
    }

    override fun init() {
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(false)
            actionBar.setDisplayHomeAsUpEnabled(false)
        }

        photo = intent.extras!!.getParcelable(Constants.PARCELABLE_KEY_PHOTO)
        title = photo!!.title

        viewModel!!.init(this)
    }

    override fun reset() {

    }

    override fun load(photo: Photo) {}

    override fun error(error: String) {

    }
}
