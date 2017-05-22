package com.omniroid.tapan.movieslist;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by hp on 2/1/2017.
 */

public class AppConfig {

    public static final String API_KEY = "bc84ffcdd59a9135a9e4418c7805a9f9";
    public static final String YOUTUBE_API_KEY = "AIzaSyDUUpnbjyF2zrVleUHVs7nchnvty50Cy74";


    public static final String MOVIE_TRAILER_URL = "https://api.themoviedb.org/3/movie/%s/videos?api_key=" + API_KEY;




        public static final String BASE_URL = "https://api.themoviedb.org/3/movie/%s?api_key=" + API_KEY + "&language=en-US&page=1";
        public static final String MOVIE_DETAIL_URL = "https://api.themoviedb.org/3/movie/%s?language=en-US&api_key=" + API_KEY;
        public static final String REVIEW_MOVIE_URL = "https://api.themoviedb.org/3/movie/%s/reviews?api_key=" + API_KEY + "&language=en-US&page=1";
        public static final String CREDITS_MOVIE_URL = "https://api.themoviedb.org/3/movie/%s/credits?api_key=" + API_KEY;


    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 180);
        return noOfColumns;
    }

}
