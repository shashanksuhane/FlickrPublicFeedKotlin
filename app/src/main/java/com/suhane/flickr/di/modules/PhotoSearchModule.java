package com.suhane.flickr.di.modules;

import com.suhane.flickr.di.scopes.ActivityScope;
import com.suhane.flickr.viewmodel.IPhotoSearchViewModel;
import com.suhane.flickr.viewmodel.PhotoSearchViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by shashanksuhane on 04/02/18.
 */

@Module
public class PhotoSearchModule {
    @Provides
    @ActivityScope
    IPhotoSearchViewModel providePhotoSearchViewModel(PhotoSearchViewModel viewModel) {
        return viewModel;
    }

}
