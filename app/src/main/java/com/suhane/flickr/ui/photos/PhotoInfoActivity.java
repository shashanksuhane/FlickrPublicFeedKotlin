package com.suhane.flickr.ui.photos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.suhane.flickr.FlickrPFApplication;
import com.suhane.flickr.R;
import com.suhane.flickr.common.Constants;
import com.suhane.flickr.di.components.DaggerPhotoInfoComponent;
import com.suhane.flickr.di.components.PhotoInfoComponent;
import com.suhane.flickr.di.modules.ActivityModule;
import com.suhane.flickr.repository.model.photos.getinfo.Photo;
import com.suhane.flickr.ui.base.BaseActivity;
import com.suhane.flickr.viewmodel.PhotoInfoViewModel;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by shashanksuhane on 04/02/18.
 */

public class PhotoInfoActivity extends BaseActivity implements PhotoInfoViewModel.View{

    private PhotoInfoComponent component;

    @Inject
    PhotoInfoViewModel viewModel;

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.photo)
    protected ImageView photoView;

    @BindView(R.id.launch_go)
    protected Button launchGo;

    private com.suhane.flickr.repository.model.photos.search.Photo photo;

    public PhotoInfoComponent component() {
        if (component == null) {
            component = DaggerPhotoInfoComponent.builder()
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

        //for now just url is enough, so no need to get more info on photo
        //viewModel.getInfo(photo.getId());

        showProgress();

        Picasso.with(this)
                .load(photo.getUrlC())
                .fit()
                .centerInside()
                .into(photoView, new Callback() {
                    @Override
                    public void onSuccess() {
                        hideProgress();
                    }

                    @Override
                    public void onError() {
                        hideProgress();
                        Toast.makeText(getBaseContext(), getText(R.string.load_data_error), Toast.LENGTH_SHORT).show();
                    }
                });

        launchGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(photo.getUrlC()));
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:

                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, photo.getTitle());
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(photo.getUrlC()));
                shareIntent.setType("image/*");
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.photo_share_option)));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        reset();
        super.onDestroy();
    }

    @Override
    protected void init() {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);
        }

        photo = getIntent().getExtras().getParcelable(Constants.PARCELABLE_KEY_PHOTO);
        setTitle(photo.getTitle());

        viewModel.init(this);
    }

    @Override
    protected void reset() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_photo_info;
    }

    @Override
    protected BaseActivity getActivity() {
        return this;
    }

    @Override
    public void load(Photo photo) {
    }

    @Override
    public void error(String error) {

    }
}
