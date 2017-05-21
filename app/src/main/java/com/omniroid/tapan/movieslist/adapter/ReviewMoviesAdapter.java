package com.omniroid.tapan.movieslist.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.omniroid.tapan.movieslist.R;
import com.omniroid.tapan.movieslist.model.ReviewMovies;

import java.util.List;

/**
 * Created by DELL on 5/17/2017.
 */

public class ReviewMoviesAdapter extends RecyclerView.Adapter<ReviewMoviesAdapter.MyViewHolder> {

    Context context;
    List<ReviewMovies> reviewMoviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mTextAuthor, mTextContent, mTextUrl;

        ScrollView scrollView;

        public MyViewHolder(View itemView) {
            super(itemView);

            mTextAuthor = (TextView) itemView.findViewById(R.id.review_text_author);
            mTextContent = (TextView) itemView.findViewById(R.id.review_text_content);
            mTextUrl = (TextView) itemView.findViewById(R.id.review_text_url);
            scrollView = (ScrollView) itemView.findViewById(R.id.scroll_reviews);

        }
    }

    public ReviewMoviesAdapter(Context context, List<ReviewMovies> reviewMoviesList) {
        this.context = context;
        this.reviewMoviesList = reviewMoviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_item_review, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final ReviewMovies reviewMovies = reviewMoviesList.get(position);

        holder.mTextAuthor.setText(reviewMovies.getAuthor());
        holder.mTextContent.setText(reviewMovies.getContent());
        holder.mTextUrl.setText(context.getString(R.string.link_in_description));
        holder.mTextUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(reviewMovies.getUrl()));
                context.startActivity(browserIntent);
            }
        });

        holder.scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                view.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });


    }

    @Override
    public int getItemCount() {
        return reviewMoviesList.size();
    }


}
