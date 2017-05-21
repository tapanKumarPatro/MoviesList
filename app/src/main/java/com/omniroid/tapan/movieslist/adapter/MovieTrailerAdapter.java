package com.omniroid.tapan.movieslist.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.omniroid.tapan.movieslist.AppConfig;
import com.omniroid.tapan.movieslist.R;
import com.omniroid.tapan.movieslist.model.MovieTrailer;

import java.util.List;

/**
 * Created by DELL on 4/28/2017.
 */

public class MovieTrailerAdapter extends RecyclerView.Adapter<MovieTrailerAdapter.MyViewHolder> implements YouTubePlayer.OnInitializedListener {

    List<MovieTrailer> movieTrailerList;

    String key_trailer;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mTextTrailerTitle;

        YouTubePlayerView youTubePlayerView;

        public MyViewHolder(View itemView) {
            super(itemView);

            mTextTrailerTitle = (TextView) itemView.findViewById(R.id.tv_trailer_title);
            youTubePlayerView = (YouTubePlayerView) itemView.findViewById(R.id.youtube_video_player);
            youTubePlayerView.initialize(AppConfig.YOUTUBE_API_KEY, MovieTrailerAdapter.this);

        }

    }

    public MovieTrailerAdapter(List<MovieTrailer> movieTrailerList) {
        this.movieTrailerList = movieTrailerList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_trailer_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        MovieTrailer movieTrailer = movieTrailerList.get(position);

        key_trailer = movieTrailer.getKey();
        holder.mTextTrailerTitle.setText(movieTrailer.getName());

    }

    @Override
    public int getItemCount() {
        return movieTrailerList.size();
    }



    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {


        youTubePlayer.setPlayerStateChangeListener(playerStateChangeListner);

        youTubePlayer.setPlaybackEventListener(playBackEventListner);

        if (!b){
            youTubePlayer.cueVideo(key_trailer);
        }

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

        //Log.v("Failed","fail");
    }

    private YouTubePlayer.PlaybackEventListener playBackEventListner = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onPlaying() {

        }

        @Override
        public void onPaused() {

        }

        @Override
        public void onStopped() {

        }

        @Override
        public void onBuffering(boolean b) {

        }

        @Override
        public void onSeekTo(int i) {

        }
    };

    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListner = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {

        }

        @Override
        public void onAdStarted() {

        }

        @Override
        public void onVideoStarted() {

        }

        @Override
        public void onVideoEnded() {

        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

        }
    };

}


