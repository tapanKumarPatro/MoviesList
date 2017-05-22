package com.omniroid.tapan.movieslist.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.omniroid.tapan.movieslist.DescriptionActivity;
import com.omniroid.tapan.movieslist.R;
import com.omniroid.tapan.movieslist.model.Movies;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by DELL on 5/21/2017.
 */

public class MovieGridRecyclerAdapter extends RecyclerView.Adapter<MovieGridRecyclerAdapter.GridRecyclerViewHolder> {

    private Context context;
    private List<Movies> listOfMovies;


    class GridRecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextTitle;
        private ImageView mImageView;
        private LinearLayout mLinearLayout;
        private CardView card_view;

        public GridRecyclerViewHolder(View view) {
            super(view);

            mTextTitle = (TextView) view.findViewById(R.id.tv_movie_title);
            mImageView = (ImageView) view.findViewById(R.id.img_view_poster);
            mLinearLayout = (LinearLayout) view.findViewById(R.id.ll_click_item_des);
            card_view = (CardView) view.findViewById(R.id.card_view);
        }
    }

    public MovieGridRecyclerAdapter(Context context, List<Movies> listOfMovies) {
        this.context = context;
        this.listOfMovies = listOfMovies;
    }

    @Override
    public GridRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.list_item_grid, parent, false); //list_item_grid, item_list_fav_movies

        return new GridRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GridRecyclerViewHolder holder, int position) {

        final Movies movies = listOfMovies.get(position);


        Typeface custom_font = Typeface.createFromAsset(context.getAssets(), "fonts/caviardreams.ttf");

        holder.mTextTitle.setTypeface(custom_font);
        holder.mTextTitle.setText(movies.getTitle());


        String imageUri = "https://image.tmdb.org/t/p/w342";

        assert movies != null;
        final String url = imageUri + String.valueOf(movies.getImageUri());

        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.user_placeholder)
                .error(R.drawable.user_placeholder_error)
                .fit()
                .into(holder.mImageView);

        String image_backdrop_uri = "https://image.tmdb.org/t/p/w500";

        final String backdrop_Url = image_backdrop_uri + String.valueOf(movies.getBackdrop_path());

        /*if (context instanceof ReciptHistoryActivity) {
            ((ReciptHistoryActivity) context).startTranGettingData(transItem.getVendorTxCode()
                    ,transItem.getOrderNo(),transItem.getPmethod(),transItem.getDate()
                    ,transItem.getTime(),transItem.getAmount());
        }*/

        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(),movies.getTitle(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, DescriptionActivity.class);
                intent.putExtra(context.getString(R.string.extra_movie_title), movies.getTitle());
                intent.putExtra(context.getString(R.string.extra_movie_overview), movies.getOverview());
                intent.putExtra(context.getString(R.string.extra_movie_releaseDate), movies.getRelease_date());
                intent.putExtra(context.getString(R.string.extra_image_url), url);
                intent.putExtra(context.getString(R.string.extra_movie_ratings), movies.getRatings());
                intent.putExtra(context.getString(R.string.extra_backdrop_url), backdrop_Url);
                intent.putExtra(context.getString(R.string.extra_movie_id), movies.getiD());
                context.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return listOfMovies.size();
    }

}



