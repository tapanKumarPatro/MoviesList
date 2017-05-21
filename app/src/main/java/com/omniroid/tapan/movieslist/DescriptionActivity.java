package com.omniroid.tapan.movieslist;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.omniroid.tapan.movieslist.adapter.CreditsAdapter;
import com.omniroid.tapan.movieslist.adapter.MovieTrailerAdapter;
import com.omniroid.tapan.movieslist.adapter.ReviewMoviesAdapter;
import com.omniroid.tapan.movieslist.data.MovieContract;
import com.omniroid.tapan.movieslist.model.Credits;
import com.omniroid.tapan.movieslist.model.MovieTrailer;
import com.omniroid.tapan.movieslist.model.ReviewMovies;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class DescriptionActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private TextView mTextTitle, mTextOverView, mTextRleaseDate, mTextRatings, mTextRunTime;

    private ImageView mImageViewDes, mImageThumb;
    private String descript_url;

    private SharedPreferences preferences;
    private ToggleButton toggleButton;

    public static String movie_Title, imageUrl, movie_overView, movie_releaseD, movie_backdrop_image_url, movie_ratings, movie_id;

    private String trailer_urls;
    private RecyclerView mRecyclerView;

    private  MovieTrailerAdapter movieTrailerAdapter;
    private List<MovieTrailer> movieTrailerList;

    private String REVIEW_URL, CASTING_URL;

    private RecyclerView reviewRecyclerView;
    private ReviewMoviesAdapter reviewMoviesAdapter;
    private List<ReviewMovies> reviewMoviesList;

    private RecyclerView recyclerViewCredits;
    private CreditsAdapter creditsAdapter;
    private List<Credits> creditsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.tb_descr);
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
        });*/
        /*scrollView = (ScrollView) findViewById(R.id.scroll_detailActivity);
        scrollView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                findViewById(R.id.scroll_reviews).getParent().requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });*/

        toggleButton = (ToggleButton) findViewById(R.id.myToggleButton);
        toggleButton.setChecked(false);
        toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_star_black_24dp));
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //SaveButtonState("isChecked");
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_star_com_yellow));
                    addMoviesToFavroite();
                    toggleButton.setClickable(false);
                } else {
                    //SaveButtonState("isNotChecked");
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_star_black_24dp));
                }
            }
        });

        //doImageSet();

        //String buttonState = LoadButtonState();

      /*  if (buttonState.equals("isChecked")) {
            toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_star_com_yellow));
        } else if (buttonState.equals("isNotChecked")) {
            toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_star_black_24dp));
        }
*/

        mTextTitle = (TextView) findViewById(R.id.tv_title_descript);
        mImageViewDes = (ImageView) findViewById(R.id.img_view_poster_description);
        mTextOverView = (TextView) findViewById(R.id.tv_description_descript);
        mTextRleaseDate = (TextView) findViewById(R.id.tv_releaseDate_descript);
        mTextRatings = (TextView) findViewById(R.id.tv_movie_ratings);
        mTextRunTime = (TextView) findViewById(R.id.tv_movie_runtime);
        mImageThumb = (ImageView) findViewById(R.id.img_view_thumb);

        Bundle bundle = getIntent().getExtras();
        movie_Title = bundle.getString(getString(R.string.extra_movie_title));
        imageUrl = bundle.getString(getString(R.string.extra_image_url));
        movie_overView = bundle.getString(getString(R.string.extra_movie_overview));
        movie_releaseD = bundle.getString(getString(R.string.extra_movie_releaseDate));
        movie_backdrop_image_url = bundle.getString(getString(R.string.extra_backdrop_url));
        movie_ratings = bundle.getString(getString(R.string.extra_movie_ratings));
        movie_id = bundle.getString(getString(R.string.extra_movie_id));

        //trailer_urls = "https://api.themoviedb.org/3/movie/" + movie_id + "/videos?api_key=bc84ffcdd59a9135a9e4418c7805a9f9&language=en-US";
        trailer_urls = String.format(AppConfig.MOVIE_TRAILER_URL,movie_id);

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

        mTextTitle.setText(movie_Title);
        Picasso.with(this)
                .load(movie_backdrop_image_url)
                .fit()
                .centerCrop()
                .into(mImageViewDes);
        mTextOverView.setText(movie_overView);
        mTextRleaseDate.setText(" " + movie_releaseD);

        REVIEW_URL = String.format(AppConfig.REVIEW_MOVIE_URL, movie_id);
        CASTING_URL = String.format(AppConfig.CREDITS_MOVIE_URL, movie_id);


        Picasso.with(this)
                .load(imageUrl)
                .fit()
                .centerCrop()
                .into(mImageThumb);


        getMovieDetailDescription();
        getMovieTrailersDes();
        getMovieReviews();
        getMovieCasting();
    }

    private void getMovieCasting() {

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(CASTING_URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                creditsList = new ArrayList<Credits>();
                try {
                    JSONObject object = new JSONObject(new String(responseBody));
                    JSONArray array = object.getJSONArray("cast");
                    for (int k = 0; k < array.length(); k++) {
                        Credits credits = new Credits();
                        credits.setName(array.getJSONObject(k).getString("name"));
                        credits.setCharcterName(array.getJSONObject(k).getString("character"));
                        credits.setProfile_path(array.getJSONObject(k).getString("profile_path"));

                        Log.v("casting", array.getJSONObject(k).getString("name"));
                        creditsList.add(credits);
                    }
                    initMovieCasting();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }

    private void initMovieCasting() {


        recyclerViewCredits = (RecyclerView) findViewById(R.id.recycler_view_credits);

        //singleTranAdapter = new ReciptSingleTranAdapter(transItemList,ReciptHistoryActivity.this);
        creditsAdapter = new CreditsAdapter(this, creditsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCredits.setLayoutManager(mLayoutManager);
        recyclerViewCredits.setItemAnimator(new DefaultItemAnimator());
        recyclerViewCredits.setAdapter(creditsAdapter);

    }

    private void SaveButtonState(String isChecked) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString("focus_value", isChecked);
        edit.commit();

    }

    public String LoadButtonState() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String buttonState = preferences.getString("focus_value", "DEFAULT");
        return buttonState;
    }

    private void getMovieReviews() {

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(REVIEW_URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                reviewMoviesList = new ArrayList<ReviewMovies>();

                try {
                    JSONObject object = new JSONObject(new String(responseBody));

                    JSONArray array = object.getJSONArray("results");
                    for (int m = 0; m < array.length(); m++) {
                        ReviewMovies reviewMovies = new ReviewMovies();

                        Log.v("author", array.getJSONObject(m).getString("author"));

                        reviewMovies.setAuthor(array.getJSONObject(m).getString("author"));
                        reviewMovies.setContent(array.getJSONObject(m).getString("content"));
                        reviewMovies.setUrl(array.getJSONObject(m).getString("url"));

                        reviewMoviesList.add(reviewMovies);

                    }
                    initWithReviews();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }

    private void initWithReviews() {

        reviewRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_review);

        //singleTranAdapter = new ReciptSingleTranAdapter(transItemList,ReciptHistoryActivity.this);
        reviewMoviesAdapter = new ReviewMoviesAdapter(this, reviewMoviesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        reviewRecyclerView.setLayoutManager(mLayoutManager);
        reviewRecyclerView.setItemAnimator(new DefaultItemAnimator());
        reviewRecyclerView.setAdapter(reviewMoviesAdapter);


    }


    public void addMoviesToFavroite() {

        if (movie_Title.length() == 0 && imageUrl.length() == 0 && movie_overView.length() == 0 && movie_releaseD.length() == 0 &&
                movie_backdrop_image_url.length() == 0 && movie_ratings.length() == 0) {
            return;
        }

        //Check if it's already added or not

        ContentValues contentValues = new ContentValues();

        contentValues.put(MovieContract.MoviesEntry.COLUMN_MOVIE_ID, movie_id);
        contentValues.put(MovieContract.MoviesEntry.COLUMN_MOVIE_TITLE, movie_Title);
        contentValues.put(MovieContract.MoviesEntry.COLUMN_MOVIE_DESCRIPTION, movie_overView);
        contentValues.put(MovieContract.MoviesEntry.COLUMN_MOVIE_THUMBNAIL, imageUrl);
        contentValues.put(MovieContract.MoviesEntry.COLUMN_MOVIE_BACKDROP, movie_backdrop_image_url);
        contentValues.put(MovieContract.MoviesEntry.COLUMN_MOVIE_RELEASE_DATE, movie_releaseD);
        contentValues.put(MovieContract.MoviesEntry.COLUMN_MOVIE_RATINGS, movie_ratings);

        Uri uri = getContentResolver().insert(MovieContract.MoviesEntry.CONTENT_URI, contentValues);

        if (uri != null) {
            Toast.makeText(getApplicationContext(), uri.toString(), Toast.LENGTH_SHORT).show();
        }


    }


    private void getMovieTrailersDes() {

        AsyncHttpClient clientTrailer = new AsyncHttpClient();
        clientTrailer.get(trailer_urls, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                movieTrailerList = new ArrayList<MovieTrailer>();
                try {
                    JSONObject object = new JSONObject(new String(responseBody));

                    JSONArray array = object.getJSONArray("results");
                    for (int k = 0; k < array.length(); k++) {

                        MovieTrailer trailer = new MovieTrailer();

                        trailer.setKey(array.getJSONObject(k).getString("key"));
                        trailer.setName(array.getJSONObject(k).getString("name"));
                        trailer.setType(array.getJSONObject(k).getString("type"));

                        movieTrailerList.add(trailer);
                        Log.v("sizeOFArray", String.valueOf(array.getJSONObject(k).getString("key")));
                    }
                    initTrailer();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }

    private void initTrailer() {

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_trailer);

        movieTrailerAdapter = new MovieTrailerAdapter(movieTrailerList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(movieTrailerAdapter);

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

                    String mMovieId = null;
                    Cursor cursor = getContentResolver().query(MovieContract.MoviesEntry.CONTENT_URI,
                            null,
                            MovieContract.MoviesEntry.COLUMN_MOVIE_ID + "=" + movie_id, //
                            null, null);

                    if (cursor.getCount() == 0) {
                        toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_star_black_24dp));
                    } else {
                        toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_star_com_yellow));
                        toggleButton.setClickable(false);
                    }

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

    /*@Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }*/


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }
}
