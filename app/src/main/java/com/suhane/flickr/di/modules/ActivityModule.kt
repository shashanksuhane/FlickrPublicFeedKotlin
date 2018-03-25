package com.suhane.flickr.di.modules

import android.app.Activity

import com.suhane.flickr.di.scopes.ActivityScope

import dagger.Module
import dagger.Provides

/**
 * Created by shashanksuhane on 04/02/18.
 */

@Module
class ActivityModule(private val activityContext: Activity) {
    @Provides
    @ActivityScope
    fun getActivityContext(): Activity {
        return activityContext
    }
}
