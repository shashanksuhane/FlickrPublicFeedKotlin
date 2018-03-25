package com.suhane.flickr.di.components

import android.app.Application
import android.content.Context

import com.suhane.flickr.FlickrPFApplication
import com.suhane.flickr.di.modules.ApplicationModule

import javax.inject.Singleton

import dagger.Component

/**
 * Created by shashanksuhane on 04/02/18.
 */

@Singleton
@Component(modules = [(ApplicationModule::class)])
interface ApplicationComponent {

    /**
     * Injections for the dependencies
     */
    fun inject(app: FlickrPFApplication)

    fun inject(context: Context)

}

