package com.omniroid.tapan.movieslist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class FavMovieDetailActivity extends AppCompatActivity {

    private TextView mTextTitle, mTextOverView, mTextRleaseDate, mTextRatings;

    private ImageView mImageViewDes, mImageThumb;

    private String movie_Title, imageUrl, movie_overView, movie_releaseD, movie_backdrop_image_url, movie_ratings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_movie_detail);

        Bundle bundle = getIntent().getExtras();
        movie_Title = bundle.getString(getString(R.string.extra_movie_title));
        imageUrl = bundle.getString(getString(R.string.extra_image_url));
        movie_overView = bundle.getString(getString(R.string.extra_movie_overview));
        movie_releaseD = bundle.getString(getString(R.string.extra_movie_releaseDate));
        movie_backdrop_image_url = bundle.getString(getString(R.string.extra_backdrop_url));
        movie_ratings = bundle.getString(getString(R.string.extra_movie_ratings));


        mTextTitle = (TextView) findViewById(R.id.tv_title_descript);
        mImageViewDes = (ImageView) findViewById(R.id.img_view_poster_description);
        mTextOverView = (TextView) findViewById(R.id.tv_description_descript);
        mTextRleaseDate = (TextView) findViewById(R.id.tv_releaseDate_descript);
        mTextRatings = (TextView) findViewById(R.id.tv_movie_ratings);
        mImageThumb = (ImageView) findViewById(R.id.img_view_thumb);

        mTextRatings.setText(" " + movie_ratings);
        mTextTitle.setText(movie_Title);
        Picasso.with(this)
                .load(movie_backdrop_image_url)
                .fit()
                .centerCrop()
                .into(mImageViewDes);
        mTextOverView.setText(movie_overView);
        mTextRleaseDate.setText(" " + movie_releaseD);


        Picasso.with(this)
                .load(imageUrl)
                .fit()
                .centerCrop()
                .into(mImageThumb);



    }
}
