package com.omniroid.tapan.movieslist;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.omniroid.tapan.movieslist.adapter.MovieGridAdapter;
import com.omniroid.tapan.movieslist.adapter.MovieGridRecyclerAdapter;
import com.omniroid.tapan.movieslist.model.Movies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private List<Movies> moviesList;
    private String Build_url;
    private String movieType = "";

    private int someVarA;
    private String someVarB;

    private static final String SELECTED_KEY = "selected_position";
    private int mPosition = GridView.INVALID_POSITION;
    //private int mPosition = 0;
    private int currentPosition = 0;

    private GridView gridView;
    private MovieGridAdapter movieGridAdapter;
    private SharedPreferences preferences;
    private ProgressDialog progressDialog;

    private MovieGridRecyclerAdapter movieGridRecyclerAdapter;
    private RecyclerView movieGridRecyclerView;
    private String SAVED_LAYOUT_MANAGER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_lay);
        setSupportActionBar(toolbar);

        progressDialog = new ProgressDialog(this);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.registerOnSharedPreferenceChangeListener(this);
        loadMovieTypeFromPreference(preferences);

        gridView = (GridView) findViewById(R.id.grid_layout);

        if (isOnline()) {
            getMoviesData();
        } else {
            createCustomToast();
        }

        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
            mPosition = savedInstanceState.getInt(SELECTED_KEY);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        int mCurrentPosition = gridView.getFirstVisiblePosition();
        mPosition = mCurrentPosition;
        if (mPosition != GridView.INVALID_POSITION) {
            outState.putInt(SELECTED_KEY, mPosition);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        if (mPosition != GridView.INVALID_POSITION) {
            gridView.smoothScrollToPosition(mPosition);
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void createCustomToast() {

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.toast_layout_root));

        ImageView image = (ImageView) layout.findViewById(R.id.image);
        image.setImageResource(R.drawable.ic_clear_black_24dp);
        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(getString(R.string.connection_slow));

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }


    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.settings:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                return true;
            case R.id.favMovie:
                startActivity(new Intent(MainActivity.this, FavoriteMovieActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void init() {
        movieGridAdapter = new MovieGridAdapter(this, R.layout.list_item_grid, moviesList);
        gridView.setAdapter(movieGridAdapter);
        movieGridAdapter.notifyDataSetChanged();
        //Log.v("currPost", String.valueOf(currentPosition));

/*
        int mNoOfColumns = AppConfig.calculateNoOfColumns(getApplicationContext());

        movieGridRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_movie_grid);
        //int numberOfColumns = 2;
        RecyclerView.LayoutManager gridmanger = new GridLayoutManager(this, mNoOfColumns);
        movieGridRecyclerView.setLayoutManager(gridmanger);
        movieGridRecyclerAdapter = new MovieGridRecyclerAdapter(this,moviesList);
        movieGridRecyclerView.setAdapter(movieGridRecyclerAdapter);
        movieGridRecyclerAdapter.notifyDataSetChanged();*/

        //int position = gridmanger.pos

       //int index =gridmanger.pos
        //Log.v("postionOfScroll", String.valueOf(index));

    }


    private void getMoviesData() {

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Build_url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                moviesList = new ArrayList<Movies>();
                try {
                    moviesList.clear();
                    JSONObject obj = new JSONObject(new String(responseBody));
                    if (obj != null) {
                        JSONArray array = obj.getJSONArray("results");
                        Log.v("MainActivity", String.valueOf(obj));
                        for (int i = 0; i < array.length(); i++) {
                            Movies movies = new Movies();

                            movies.setTitle(array.getJSONObject(i).getString("title"));
                            String imageUri = "https://image.tmdb.org/t/p/w500";

                            movies.setImageUri(array.getJSONObject(i).getString("poster_path"));
                            movies.setOverview(array.getJSONObject(i).getString("overview"));
                            movies.setRelease_date(array.getJSONObject(i).getString("release_date"));
                            movies.setVote_count(array.getJSONObject(i).getDouble("vote_count"));
                            movies.setBackdrop_path(array.getJSONObject(i).getString("backdrop_path"));
                            movies.setRatings(array.getJSONObject(i).getString("vote_average"));
                            movies.setiD(array.getJSONObject(i).getString("id"));

                            moviesList.add(movies);

                        }
                    }
                    init();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }

    private void loadMovieTypeFromPreference(SharedPreferences sharedPreferences) {
        movieType = sharedPreferences.getString(getString(R.string.pref_type_movie_key)
                , getString(R.string.pref_type_movie_popular_value));

        Build_url = String.format(AppConfig.BASE_URL, movieType); //popular top_rated upcoming
        Log.v("BuildURl", Build_url);
        if (isOnline()) {
            getMoviesData();
        } else {
            createCustomToast();
        }

        if (movieType.equals(getString(R.string.pref_type_movie_popular_value))) {
            getSupportActionBar().setTitle(getString(R.string.pref_type_movie_popular) + " Movies");
        } else if (movieType.equals(getString(R.string.pref_type_movie_upcoming_value))) {
            getSupportActionBar().setTitle(getString(R.string.pref_type_movie_upcoming) + " Movies");
        } else if (movieType.equals(getString(R.string.pref_type_movie_topRated_value))) {
            getSupportActionBar().setTitle(getString(R.string.pref_type_movie_topRated) + " Movies");
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        if (key.equals(getString(R.string.pref_type_movie_key))) {
            loadMovieTypeFromPreference(sharedPreferences);
        }


    }
}

