package com.suhane.flickr.viewmodel;

import com.suhane.flickr.repository.model.photos.search.Photos;
import com.suhane.flickr.ui.base.BaseViewModel;

/**
 * Created by shashanksuhane on 04/02/18.
 */

public interface IPhotoSearchViewModel extends BaseViewModel {

    void init(View view);
    void search(String searchText);

    interface View {
        void load(String searchText, Photos photos);
        void error(String searchText, String error);
    }
}
