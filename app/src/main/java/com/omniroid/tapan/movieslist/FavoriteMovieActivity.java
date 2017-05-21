package com.omniroid.tapan.movieslist;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.omniroid.tapan.movieslist.adapter.FavMovieAdapter;
import com.omniroid.tapan.movieslist.data.MovieContract;

public class FavoriteMovieActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {


    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int MOVIE_LOADER_ID = 0;

    private FavMovieAdapter mAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_movie);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_fav);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("FavoriteMovies");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        int mNoOfColumns = AppConfig.calculateNoOfColumns(getApplicationContext());

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewTasks);
        //int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, mNoOfColumns));
        mAdapter = new FavMovieAdapter(this);
        recyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // Here is where you'll implement swipe to delete

                int id = (int) viewHolder.itemView.getTag();

                // Build appropriate uri with String row id appended
                String stringId = Integer.toString(id);
                Uri uri = MovieContract.MoviesEntry.CONTENT_URI;
                uri = uri.buildUpon().appendPath(stringId).build();

                // COMPLETED (2) Delete a single row of data using a ContentResolver
                getContentResolver().delete(uri, null, null);

                // COMPLETED (3) Restart the loader to re-query for all tasks after a deletion
                getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID, null, FavoriteMovieActivity.this);


            }
        }).attachToRecyclerView(recyclerView);

        getSupportLoaderManager().initLoader(MOVIE_LOADER_ID, null, this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // re-queries for all tasks
        getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

       return new AsyncTaskLoader<Cursor>(this) {

           Cursor mMovieData = null;

           @Override
           protected void onStartLoading() {
               if (mMovieData != null) {
                   // Delivers any previously loaded data immediately
                   deliverResult(mMovieData);
               } else {
                   // Force a new load
                   forceLoad();
               }
           }

           @Override
           public Cursor loadInBackground() {


               try {
                   return getContentResolver().query(MovieContract.MoviesEntry.CONTENT_URI,
                           null,
                           null,
                           null,
                           MovieContract.MoviesEntry._ID);

               } catch (Exception e) {
                   Log.e(TAG, "Failed to asynchronously load data.");
                   e.printStackTrace();
                   return null;
               }
           }

           @Override
           public void deliverResult(Cursor data) {
               mMovieData = data;
               super.deliverResult(data);
           }
       };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
