package com.example.poppukon.models;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.poppukon.BuildConfig;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;


public class Movie {

    public static final String TAG = "Movie";

    public static final String CONFIG_URL =
            "https://api.themoviedb.org/3/configuration?api_key=" + BuildConfig.TMDB_KEY;

    private String title;
    private String overview;
    private String posterPath;

    public Movie(@NotNull JSONObject jsonObject) throws JSONException {
        /**
         * @param: JSONObject with the following keys: 'poster_path', 'title','overview'
         * @return: Movie object
         */
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
    }

    public static List<Movie> fromJsonArray(@NotNull JSONArray movieJsonArray) throws JSONException {
        /**
         * @param: JSONArray of parsable JSONObjects into Movie objects.
         * @return: List,Movie>
         */
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < movieJsonArray.length(); i++){
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }
        return movies;
    }

    public String getPosterURL() {
        //TODO: As opposed to hard-coding the url, should build it by calling CONFIG_URL and indexing and concatenating the data.
        return "https://image.tmdb.org/t/p/w342" + posterPath;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    private void fetchImageConfig(){

    }
}
