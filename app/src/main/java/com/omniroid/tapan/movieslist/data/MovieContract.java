package com.omniroid.tapan.movieslist.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by DELL on 5/9/2017.
 */

public class MovieContract {

    //The authority
    public static final String AUTHORITY = "com.omniroid.tapan.movieslist";


    // The base content URI = "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    //Dircetory of the path
    public static final String PATH_MOVIES = "movies";


    public static final class MoviesEntry implements BaseColumns {

        //content uri for moviesEntry
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();

        //Movies table and column name
        public static final String TABLE_NAME = "movies";

        //no need to create id here because its implements BaseColumn
        public static final String COLUMN_MOVIE_TITLE = "title";
        public static final String COLUMN_MOVIE_ID = "movieid";
        public static final String COLUMN_MOVIE_DESCRIPTION = "description";
        public static final String COLUMN_MOVIE_THUMBNAIL = "thumbnail";
        public static final String COLUMN_MOVIE_BACKDROP = "backdrop";
        public static final String COLUMN_MOVIE_RELEASE_DATE = "release";
        public static final String COLUMN_MOVIE_RATINGS = "ratings";


    }


}
