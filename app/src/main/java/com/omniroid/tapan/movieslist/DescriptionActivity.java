package com.omniroid.tapan.movieslist;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DescriptionActivity extends AppCompatActivity {

    private TextView mTextTitle, mTextOverView, mTextRleaseDate, mTextRatings, mTextRunTime;

    private ImageView mImageViewDes, mImageThumb;
    private String descript_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_descr);
        setSupportActionBar(toolbar);


        getSupportActionBar().setTitle("MovieDetail");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mTextTitle = (TextView) findViewById(R.id.tv_title_descript);
        mImageViewDes = (ImageView) findViewById(R.id.img_view_poster_description);
        mTextOverView = (TextView) findViewById(R.id.tv_description_descript);
        mTextRleaseDate = (TextView) findViewById(R.id.tv_releaseDate_descript);
        mTextRatings = (TextView) findViewById(R.id.tv_movie_ratings);
        mTextRunTime = (TextView) findViewById(R.id.tv_movie_runtime);
        mImageThumb = (ImageView) findViewById(R.id.img_view_thumb);

        Bundle bundle = getIntent().getExtras();
        String texTitle = bundle.getString(getString(R.string.extra_movie_title));
        String imageUrl = bundle.getString(getString(R.string.extra_image_url));
        String movie_overView = bundle.getString(getString(R.string.extra_movie_overview));
        String movie_releaseD = bundle.getString(getString(R.string.extra_movie_releaseDate));
        String movie_backdrop_image_url = bundle.getString(getString(R.string.extra_backdrop_url));
        String movie_ratings = bundle.getString(getString(R.string.extra_movie_ratings));
        String movie_id = bundle.getString(getString(R.string.extra_movie_id));


        descript_url = String.format(AppConfig.MOVIE_DETAIL_URL, movie_id);
        Typeface custom_font_bold = Typeface.createFromAsset(getAssets(), "fonts/caviardreams_bold.ttf");
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/caviardreams.ttf");
        mTextOverView.setTypeface(custom_font);
        mTextRleaseDate.setTypeface(custom_font_bold);
        mTextTitle.setTypeface(custom_font_bold);
        mTextTitle.setSelected(true);
        mTextTitle.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        mTextTitle.setSingleLine(true);
        mTextRatings.setText(" " + movie_ratings);
        mTextOverView.setMovementMethod(new ScrollingMovementMethod());

        mTextTitle.setText(texTitle);
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


        getMovieDetailDescription();
    }

    private void getMovieDetailDescription() {

        AsyncHttpClient httpClient = new AsyncHttpClient();
        httpClient.get(descript_url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                try {
                    Log.v("UrlDesc", descript_url);
                    JSONObject jsonObject = new JSONObject(new String(responseBody));
                    mTextRunTime.setText(" " + jsonObject.getString("runtime") + " min's");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });


    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
