package com.omniroid.tapan.movieslist.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.omniroid.tapan.movieslist.DescriptionActivity;
import com.omniroid.tapan.movieslist.Movies;
import com.omniroid.tapan.movieslist.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by hp on 4/20/2017.
 */

public class MovieGridAdapter extends ArrayAdapter<Movies> {
    private TextView mTextTitle;
    private ImageView mImageView;
    private LinearLayout mLinearLayout;
    private CardView card_view;

    public MovieGridAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Movies> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_grid, null);
        }

        final Movies movies = getItem(position);

        Typeface custom_font = Typeface.createFromAsset(getContext().getAssets(), "fonts/caviardreams.ttf");

        mTextTitle = (TextView) view.findViewById(R.id.tv_movie_title);
        mImageView = (ImageView) view.findViewById(R.id.img_view_poster);
        mLinearLayout = (LinearLayout) view.findViewById(R.id.ll_click_item_des);
        card_view = (CardView) view.findViewById(R.id.card_view);

        mTextTitle.setTypeface(custom_font);
        mTextTitle.setText(movies.getTitle());

        String imageUri = "https://image.tmdb.org/t/p/w342";

        assert movies != null;
        final String url = imageUri + String.valueOf(movies.getImageUri());

        Picasso.with(getContext())
                .load(url)
                .fit()
                .into(mImageView);

        String image_backdrop_uri = "https://image.tmdb.org/t/p/w500";

        final String backdrop_Url = image_backdrop_uri + String.valueOf(movies.getBackdrop_path());

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(),movies.getTitle(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), DescriptionActivity.class);
                intent.putExtra(getContext().getString(R.string.extra_movie_title), movies.getTitle());
                intent.putExtra(getContext().getString(R.string.extra_movie_overview), movies.getOverview());
                intent.putExtra(getContext().getString(R.string.extra_movie_releaseDate), movies.getRelease_date());
                intent.putExtra(getContext().getString(R.string.extra_image_url), url);
                intent.putExtra(getContext().getString(R.string.extra_movie_ratings), movies.getRatings());
                intent.putExtra(getContext().getString(R.string.extra_backdrop_url), backdrop_Url);
                intent.putExtra(getContext().getString(R.string.extra_movie_id), movies.getiD());
                getContext().startActivity(intent);

            }
        });

        return view;
    }
}