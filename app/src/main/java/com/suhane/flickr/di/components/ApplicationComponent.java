package com.suhane.flickr.di.components;

import android.app.Application;
import android.content.Context;

import com.suhane.flickr.FlickrPFApplication;
import com.suhane.flickr.di.modules.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by shashanksuhane on 04/02/18.
 */

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    /**
     * Injections for the dependencies
     */
    void inject(FlickrPFApplication app);

    void inject(Context context);

    /**
     * Used in child components
     */
    Application application();

}

