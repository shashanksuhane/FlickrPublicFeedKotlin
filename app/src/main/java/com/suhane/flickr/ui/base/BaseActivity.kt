package com.suhane.flickr.ui.base

import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ProgressBar

import com.suhane.flickr.R

import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder

/**
 * Created by shashanksuhane on 04/02/18.
 */

abstract class BaseActivity : AppCompatActivity() {

    private var unbinder: Unbinder? = null

    @BindView(R.id.progressbar) @Nullable
    @JvmField
    var progressBar: ProgressBar? = null

    protected abstract val layout: Int
    protected abstract val activity: BaseActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout)
        unbinder = ButterKnife.bind(activity)
    }

    override fun onDestroy() {
        unbinder!!.unbind()
        super.onDestroy()
    }

    protected fun showProgress() {
        progressBar!!.visibility = View.VISIBLE
    }

    protected fun hideProgress() {
        progressBar!!.visibility = View.INVISIBLE
    }

    protected abstract fun init()
    protected abstract fun reset()
}
