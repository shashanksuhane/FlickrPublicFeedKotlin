package com.suhane.flickr

import android.app.Application
import com.suhane.flickr.di.components.ApplicationComponent
import com.suhane.flickr.di.components.DaggerApplicationComponent
import com.suhane.flickr.di.components.DaggerRemoteClientComponent
import com.suhane.flickr.di.components.RemoteClientComponent
import com.suhane.flickr.di.modules.ApplicationModule
import com.suhane.flickr.di.modules.RemoteClientModule

/**
 * Created by shashanksuhane on 04/02/18.
 */

open class FlickrPFApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        app = this

        initialize()
    }

    private fun initialize() {
        applicationComponent = DaggerApplicationComponent.builder()
                //.applicationModule(ApplicationModule(this))
                .build()

        remoteClientComponent = DaggerRemoteClientComponent.builder()
                .remoteClientModule(RemoteClientModule(this))
                .build()
    }

    fun component(): ApplicationComponent? {
        return applicationComponent
    }

    fun remoteClientComponent(): RemoteClientComponent? {
        return remoteClientComponent
    }

    companion object {

        var app: FlickrPFApplication? = null
            private set

        @JvmStatic lateinit var applicationComponent: ApplicationComponent
        @JvmStatic lateinit var remoteClientComponent: RemoteClientComponent
    }

}
