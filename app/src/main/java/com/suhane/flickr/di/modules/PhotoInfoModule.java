package com.suhane.flickr.di.modules;

import com.suhane.flickr.di.scopes.ActivityScope;
import com.suhane.flickr.viewmodel.IPhotoInfoViewModel;
import com.suhane.flickr.viewmodel.PhotoInfoViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by shashanksuhane on 04/02/18.
 */

@Module
public class PhotoInfoModule {
    @ActivityScope
    @Provides
    public IPhotoInfoViewModel providePhotoInfoViewModel(PhotoInfoViewModel viewModel) {
        return viewModel;
    }

}
