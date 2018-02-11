package com.suhane.flickr.di.modules;

import android.app.Activity;

import com.suhane.flickr.di.scopes.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by shashanksuhane on 04/02/18.
 */

@Module
public class ActivityModule {
    private final Activity activityContext;

    public ActivityModule(Activity activityContext) {
        this.activityContext = activityContext;
    }

    @Provides
    @ActivityScope
    Activity getActivityContext() {
        return activityContext;
    }
}
