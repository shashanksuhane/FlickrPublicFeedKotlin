package com.suhane.flickr.viewmodel;

import com.suhane.flickr.repository.model.photos.getinfo.Photo;
import com.suhane.flickr.ui.base.BaseViewModel;

/**
 * Created by shashanksuhane on 04/02/18.
 */

public interface IPhotoInfoViewModel extends BaseViewModel {

    void init(View view);
    void getInfo(String photoId);

    interface View {
        void load(Photo photo);
        void error(String error);
    }
}
