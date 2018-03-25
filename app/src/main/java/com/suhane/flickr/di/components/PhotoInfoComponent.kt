package com.suhane.flickr.di.components

import android.app.Activity

import com.suhane.flickr.di.modules.ActivityModule
import com.suhane.flickr.di.modules.PhotoInfoModule
import com.suhane.flickr.di.scopes.ActivityScope
import com.suhane.flickr.ui.photos.PhotoInfoActivity

import javax.inject.Singleton

import dagger.Component

/**
 * Created by shashanksuhane on 04/02/18.
 */

@ActivityScope
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(ActivityModule::class, PhotoInfoModule::class))
interface PhotoInfoComponent {
    fun inject(activity: PhotoInfoActivity)
}
