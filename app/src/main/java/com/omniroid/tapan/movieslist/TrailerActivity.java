package com.omniroid.tapan.movieslist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class TrailerActivity extends AppCompatActivity {


    String trailer_url ;
    ListView mListTrailers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer);

        mListTrailers = (ListView) findViewById(R.id.list_trailer);
        Bundle bundle = getIntent().getExtras();
        String movie_id = bundle.getString(getString(R.string.extra_movie_id));
        trailer_url = String.format(AppConfig.MOVIE_TRAILER_URL,movie_id);
        //trailer_url = "https://api.themoviedb.org/3/movie/"+movie_id+"/videos?api_key=bc84ffcdd59a9135a9e4418c7805a9f9&language=en-US";
        getMovieTrailers();
    }

    private void getMovieTrailers() {

        AsyncHttpClient clientTrailer = new AsyncHttpClient();
        clientTrailer.get(trailer_url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                try {
                    JSONObject object = new JSONObject(new String(responseBody));

                    JSONArray array = object.getJSONArray("results");
                    for (int k = 0; k < array.length(); k++) {
                        Log.v("sizeOFArray", String.valueOf(array.length()));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }

}
