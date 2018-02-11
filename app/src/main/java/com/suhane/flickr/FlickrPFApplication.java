package com.suhane.flickr;

import android.app.Application;

import com.suhane.flickr.common.Constants;
import com.suhane.flickr.di.components.ApplicationComponent;
import com.suhane.flickr.di.components.DaggerApplicationComponent;
import com.suhane.flickr.di.components.DaggerRemoteClientComponent;
import com.suhane.flickr.di.components.RemoteClientComponent;
import com.suhane.flickr.di.modules.ApplicationModule;
import com.suhane.flickr.di.modules.RemoteClientModule;

/**
 * Created by shashanksuhane on 04/02/18.
 */

public class FlickrPFApplication extends Application {

    private static FlickrPFApplication app;
    private ApplicationComponent applicationComponent;
    private RemoteClientComponent remoteClientComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        app = this;

        initialize();
    }

    private void initialize() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        remoteClientComponent = DaggerRemoteClientComponent.builder()
                .remoteClientModule(new RemoteClientModule(this))
                .build();
    }

    public static FlickrPFApplication getApp() {
        return app;
    }

    public ApplicationComponent component() {
        return applicationComponent;
    }

    public RemoteClientComponent remoteClientComponent() {
        return remoteClientComponent;
    }

}
