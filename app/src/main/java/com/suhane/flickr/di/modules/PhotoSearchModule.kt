package com.suhane.flickr.di.modules

import com.suhane.flickr.di.scopes.ActivityScope
import com.suhane.flickr.viewmodel.IPhotoSearchViewModel
import com.suhane.flickr.viewmodel.PhotoSearchViewModel

import dagger.Module
import dagger.Provides

/**
 * Created by shashanksuhane on 04/02/18.
 */

@Module
class PhotoSearchModule {
    @Provides
    @ActivityScope
    fun providePhotoSearchViewModel(viewModel: PhotoSearchViewModel): IPhotoSearchViewModel {
        return viewModel
    }

}
