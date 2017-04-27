package com.omniroid.tapan.movieslist;

/**
 * Created by hp on 2/1/2017.
 */

public class AppConfig {

    private static String API_KEY = "";

    public static final String BASE_URL = "https://api.themoviedb.org/3/movie/%s?api_key=" + API_KEY + "&language=en-US&page=1";
    public static final String MOVIE_DETAIL_URL = "https://api.themoviedb.org/3/movie/%s?language=en-US&api_key=" + API_KEY;

}
