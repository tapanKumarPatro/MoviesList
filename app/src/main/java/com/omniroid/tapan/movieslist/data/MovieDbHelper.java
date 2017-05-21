package com.omniroid.tapan.movieslist.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.omniroid.tapan.movieslist.data.MovieContract.MoviesEntry;

/**
 * Created by DELL on 5/9/2017.
 */

public class MovieDbHelper extends SQLiteOpenHelper {

    //The name of database
    private static final String DATABASE_NAME = "moviesDb.db";

    //The Database Version
    private static final int VERSION = 1;

    //constructor
    public MovieDbHelper(Context context) {
        super(context,DATABASE_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String CREATE_TABLE =  "CREATE TABLE "  + MoviesEntry.TABLE_NAME + " (" +
                MoviesEntry._ID                         + " INTEGER PRIMARY KEY, " +
                MoviesEntry.COLUMN_MOVIE_ID             + " TEXT NOT NULL, " +
                MoviesEntry.COLUMN_MOVIE_TITLE          + " TEXT NOT NULL, " +
                MoviesEntry.COLUMN_MOVIE_DESCRIPTION    + " TEXT NOT NULL, " +
                MoviesEntry.COLUMN_MOVIE_THUMBNAIL      + " TEXT NOT NULL, " +
                MoviesEntry.COLUMN_MOVIE_BACKDROP       + " TEXT NOT NULL, " +
                MoviesEntry.COLUMN_MOVIE_RELEASE_DATE   + " TEXT NOT NULL, " +
                MoviesEntry.COLUMN_MOVIE_RATINGS        + " TEXT NOT NULL);";


        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MoviesEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
