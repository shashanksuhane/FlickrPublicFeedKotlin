package com.suhane.flickr.di.modules

import android.app.Application
import android.content.Context
import com.suhane.flickr.FlickrPFApplication

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by shashanksuhane on 04/02/18.
 */

@Module
class ApplicationModule(private val application: FlickrPFApplication) {

    @Provides
    @Singleton
    fun provideApplication() = application

    @Provides
    @Singleton
    fun provideApplicationContext(): Context {
        return application.applicationContext
    }
}
