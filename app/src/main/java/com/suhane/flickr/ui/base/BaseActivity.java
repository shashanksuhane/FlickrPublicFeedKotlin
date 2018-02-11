package com.suhane.flickr.ui.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.suhane.flickr.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Optional;
import butterknife.Unbinder;

/**
 * Created by shashanksuhane on 04/02/18.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder unbinder;

    @BindView(R.id.progressbar)
    protected ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        unbinder = ButterKnife.bind(getActivity());
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    protected void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    protected void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    protected abstract void init();
    protected abstract void reset();
    protected abstract int getLayout();
    protected abstract BaseActivity getActivity();
}
