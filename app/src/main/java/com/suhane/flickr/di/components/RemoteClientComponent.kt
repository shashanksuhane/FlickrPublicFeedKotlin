package com.suhane.flickr.di.components

import com.suhane.flickr.di.modules.RemoteClientModule
import com.suhane.flickr.repository.data.remote.PhotoGetInfoServerAPI
import com.suhane.flickr.repository.data.remote.PhotoSearchServerAPI

import javax.inject.Singleton

import dagger.Component

/**
 * Created by shashanksuhane on 04/02/18.
 */

@Singleton
@Component(modules = arrayOf(RemoteClientModule::class))
interface RemoteClientComponent {
    fun inject(api: PhotoSearchServerAPI)
    fun inject(api: PhotoGetInfoServerAPI)
}
