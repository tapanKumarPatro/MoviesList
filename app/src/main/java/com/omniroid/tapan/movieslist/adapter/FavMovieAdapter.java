package com.omniroid.tapan.movieslist.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.omniroid.tapan.movieslist.FavMovieDetailActivity;
import com.omniroid.tapan.movieslist.R;
import com.omniroid.tapan.movieslist.data.MovieContract;
import com.squareup.picasso.Picasso;


/**
 * Created by DELL on 5/16/2017.
 */

public class FavMovieAdapter extends RecyclerView.Adapter<FavMovieAdapter.TaskViewHolder>  {

    private Cursor mCursor;
    private Context context;

    public FavMovieAdapter(Context context) {
        this.context = context;
    }

    class TaskViewHolder extends RecyclerView.ViewHolder{

        ImageView mImageThumb;
        TextView mTextMovieTitle;

        public TaskViewHolder(View itemView) {
            super(itemView);

            mImageThumb = (ImageView) itemView.findViewById(R.id.img_view_poster); //img_view_poster, mImageThumb
            mTextMovieTitle = (TextView) itemView.findViewById(R.id.tv_movie_title); //tv_movie_title, tv_fav_movie_title

        }
    }


    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the task_layout to a view
        View view = LayoutInflater.from(context)
                .inflate(R.layout.list_item_grid, parent, false); //list_item_grid, item_list_fav_movies

        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {


        int idIndex = mCursor.getColumnIndex(MovieContract.MoviesEntry._ID);
        int titleIndex = mCursor.getColumnIndex(MovieContract.MoviesEntry.COLUMN_MOVIE_TITLE);
        int descrIndex = mCursor.getColumnIndex(MovieContract.MoviesEntry.COLUMN_MOVIE_DESCRIPTION);
        int thumbIndex = mCursor.getColumnIndex(MovieContract.MoviesEntry.COLUMN_MOVIE_THUMBNAIL);
        int backDropIndex = mCursor.getColumnIndex(MovieContract.MoviesEntry.COLUMN_MOVIE_BACKDROP);
        int releaseDateIndex = mCursor.getColumnIndex(MovieContract.MoviesEntry.COLUMN_MOVIE_RELEASE_DATE);
        int ratingsIndex = mCursor.getColumnIndex(MovieContract.MoviesEntry.COLUMN_MOVIE_RATINGS);


        mCursor.moveToPosition(position);


        int id = mCursor.getInt(idIndex);
        final String movie_title = mCursor.getString(titleIndex);
        final String movie_descript = mCursor.getString(descrIndex);
        final String movie_thumb = mCursor.getString(thumbIndex);
        final String movie_backdrop = mCursor.getString(backDropIndex);
        final String movie_releaseDate = mCursor.getString(releaseDateIndex);
        final String movie_ratings = mCursor.getString(ratingsIndex);

        /*if (DescriptionActivity.movie_Title.equals(movie_title)){
            isFavorite = "YES";
        }*/

        holder.itemView.setTag(id);

        holder.mTextMovieTitle.setText(movie_title);
        Picasso.with(context)
                .load(movie_thumb)
                .placeholder(R.drawable.user_placeholder)
                .error(R.drawable.user_placeholder_error)
                .fit()
                .into(holder.mImageThumb);

        holder.mImageThumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FavMovieDetailActivity.class);
                intent.putExtra(context.getString(R.string.extra_movie_title), movie_title);
                intent.putExtra(context.getString(R.string.extra_movie_overview), movie_descript);
                intent.putExtra(context.getString(R.string.extra_movie_releaseDate), movie_releaseDate);
                intent.putExtra(context.getString(R.string.extra_image_url), movie_thumb);
                intent.putExtra(context.getString(R.string.extra_movie_ratings), movie_ratings);
                intent.putExtra(context.getString(R.string.extra_backdrop_url), movie_backdrop);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }

    public Cursor swapCursor(Cursor c) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == c) {
            return null; // bc nothing has changed
        }
        Cursor temp = mCursor;
        this.mCursor = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }


}
