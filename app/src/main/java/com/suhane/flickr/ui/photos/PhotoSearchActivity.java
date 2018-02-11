package com.suhane.flickr.ui.photos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.suhane.flickr.FlickrPFApplication;
import com.suhane.flickr.R;
import com.suhane.flickr.common.Constants;
import com.suhane.flickr.di.components.DaggerPhotoSearchComponent;
import com.suhane.flickr.di.components.PhotoSearchComponent;
import com.suhane.flickr.di.modules.ActivityModule;
import com.suhane.flickr.repository.model.photos.search.Photo;
import com.suhane.flickr.repository.model.photos.search.Photos;
import com.suhane.flickr.ui.base.BaseActivity;
import com.suhane.flickr.viewmodel.PhotoSearchViewModel;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by shashanksuhane on 04/02/18.
 */

public class PhotoSearchActivity extends BaseActivity implements PhotoSearchViewModel.View{

    private PhotoSearchComponent component;

    @Inject
    PhotoSearchViewModel viewModel;

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.recyclerview_kittens)
    protected RecyclerView photoViewKittens;

    @BindView(R.id.recyclerview_dogs)
    protected RecyclerView photoViewDogs;

    @BindView(R.id.recyclerview_public_feed)
    protected RecyclerView photoViewPublicFeed;

    private PhotoViewAdapter kittensAdapter;
    private PhotoViewAdapter dogsAdapter;
    private PhotoViewAdapter publicFeedAdapter;

    private Photos kittenPhotos;
    private Photos dogPhotos;
    private Photos publicFeedPhotos;

    public PhotoSearchComponent component() {
        if (component == null) {
            component = DaggerPhotoSearchComponent.builder()
                    .applicationComponent(((FlickrPFApplication) getApplication()).component())
                    .activityModule(new ActivityModule(this))
                    .build();
        }
        return component;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        component().inject(this);
        init();
        viewModel.search(Constants.SEARCH_TEXT_KITTENS);
        viewModel.search(Constants.SEARCH_TEXT_DOGS);
        viewModel.search(Constants.SEARCH_TEXT_PUBLICFEEDS);
        showProgress();
    }

    @Override
    protected void onDestroy() {
        reset();
        super.onDestroy();
    }

    @Override
    protected void init() {
        //setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        toolbar.setTitle(getString(R.string.app_name));
        viewModel.init(this);

        initPhotoViewKittens();
        initPhotoViewDogs();
        initPhotoViewPublicFeed();
    }

    private void initPhotoViewKittens() {
        kittenPhotos = new Photos();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        photoViewKittens.setLayoutManager(layoutManager);
        kittensAdapter = new PhotoViewAdapter(kittenPhotos, photoViewKittens);
        photoViewKittens.setAdapter(kittensAdapter);

        kittensAdapter.setPhotoViewListener(new PhotoViewListener() {
            @Override
            public void onLoadMore() {
                viewModel.search(Constants.SEARCH_TEXT_KITTENS);
            }

            @Override
            public void onItemClick(Photo photo) {
                Intent intent = new Intent(PhotoSearchActivity.this, PhotoInfoActivity.class);
                intent.putExtra(Constants.PARCELABLE_KEY_PHOTO, photo);
                startActivity(intent);
            }
        });

    }
    private void initPhotoViewDogs() {
        dogPhotos = new Photos();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        photoViewDogs.setLayoutManager(layoutManager);
        dogsAdapter = new PhotoViewAdapter(dogPhotos, photoViewDogs);
        photoViewDogs.setAdapter(dogsAdapter);

        dogsAdapter.setPhotoViewListener(new PhotoViewListener() {
            @Override
            public void onLoadMore() {
                viewModel.search(Constants.SEARCH_TEXT_DOGS);
            }

            @Override
            public void onItemClick(Photo photo) {
                Intent intent = new Intent(PhotoSearchActivity.this, PhotoInfoActivity.class);
                intent.putExtra(Constants.PARCELABLE_KEY_PHOTO, photo);
                startActivity(intent);
            }
        });
    }

    private void initPhotoViewPublicFeed() {
        publicFeedPhotos = new Photos();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        photoViewPublicFeed.setLayoutManager(layoutManager);
        publicFeedAdapter = new PhotoViewAdapter(publicFeedPhotos, photoViewPublicFeed);
        photoViewPublicFeed.setAdapter(publicFeedAdapter);

        publicFeedAdapter.setPhotoViewListener(new PhotoViewListener() {
            @Override
            public void onLoadMore() {
                viewModel.search("");
            }

            @Override
            public void onItemClick(Photo photo) {
                Intent intent = new Intent(PhotoSearchActivity.this, PhotoInfoActivity.class);
                intent.putExtra(Constants.PARCELABLE_KEY_PHOTO, photo);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void reset() {
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_photo_search;
    }

    @Override
    protected BaseActivity getActivity() {
        return this;
    }

    @Override
    public void load(final String searchText, Photos photos) {
        hideProgress();

        if (searchText.equalsIgnoreCase(Constants.SEARCH_TEXT_KITTENS)) {
            kittenPhotos.setPhoto(photos.getPhoto());
            kittensAdapter.notifyDataSetChanged();
        } else if (searchText.equalsIgnoreCase(Constants.SEARCH_TEXT_DOGS)) {
            dogPhotos.setPhoto(photos.getPhoto());
            dogsAdapter.notifyDataSetChanged();
        } else {
            publicFeedPhotos.setPhoto(photos.getPhoto());
            publicFeedAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void error(String searchText, String error) {
        hideProgress();
        String msg = String.format(getString(R.string.search_photo_error), searchText, error);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
