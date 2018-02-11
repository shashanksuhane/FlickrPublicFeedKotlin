package com.suhane.flickr.ui.photos;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.suhane.flickr.R;
import com.suhane.flickr.repository.model.photos.search.Photo;
import com.suhane.flickr.repository.model.photos.search.Photos;

/**
 * Created by shashanksuhane on 11/02/18.
 */

public class PhotoViewAdapter extends RecyclerView.Adapter<PhotoViewAdapter.ViewHolder>{

    private Photos photos = null;

    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private PhotoViewListener photoViewListener;

    public PhotoViewAdapter(Photos photos, RecyclerView recyclerView) {
        this.photos = photos;

        // detect and load more items
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    if (totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        if (photoViewListener != null) {
                            photoViewListener.onLoadMore();
                        }
                    }
                }
            });
        }
    }

    public void setPhotoViewListener(PhotoViewListener photoViewListener) {
        this.photoViewListener = photoViewListener;
    }

    @Override
    public PhotoViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PhotoViewAdapter.ViewHolder holder, int position) {
        if (photos != null) {
            final Photo photo = photos.getPhoto().get(position);

            if (photo != null) {

                if (photo.getTitle() != null)
                    holder.getTitle().setText(photo.getTitle());

                if (photo != null && photo.getUrlS() != null) {
                    Picasso.with(holder.getContext())
                            .load(photo.getUrlS())
                            .fit()
                            .centerCrop()
                            .into(holder.getImage());
                }

                holder.getImage().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        photoViewListener.onItemClick(photo);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        if (photos != null && photos.getPhoto() != null) {
            return photos.getPhoto().size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final Context mContext;
        private final ImageView mImage;
        private final TextView mTitle;

        public ViewHolder(View view) {
            super(view);
            mContext = view.getContext();
            mImage = view.findViewById(R.id.image);
            mTitle = view.findViewById(R.id.title);
        }

        public Context getContext() {
            return mContext;
        }
        public ImageView getImage() {
            return mImage;
        }
        public TextView getTitle() {
            return mTitle;
        }
    }
}
