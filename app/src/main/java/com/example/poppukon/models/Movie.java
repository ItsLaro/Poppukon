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
    private String backdropPath;
    private String releaseDate;
    private int popularityScore;
    private int reviewCount;
    private float ratingAverage;


    public Movie(@NotNull JSONObject jsonObject) throws JSONException {
        /**
         * @param: JSONObject with the following keys: 'poster_path', 'title','overview'
         * @return: Movie object
         */
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        posterPath = jsonObject.getString("poster_path");
        backdropPath = jsonObject.getString("backdrop_path");
        releaseDate = jsonObject.getString("release_date");
        popularityScore = jsonObject.getInt("popularity");
        reviewCount = jsonObject.getInt("vote_count");
        ratingAverage = (float) jsonObject.getDouble("vote_average") / 2;
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

    public String getTitle() {

        return title;
    }

    public String getOverview() {

        return overview;
    }

    public String getPosterURL() {
        //TODO: As opposed to hard-coding the url, should build it by calling CONFIG_URL and indexing and concatenating the data.
        return "https://image.tmdb.org/t/p/w342" + posterPath;
    }

    public String getBackdropURL() {
        //TODO: As opposed to hard-coding the url, should build it by calling CONFIG_URL and indexing and concatenating the data.
        return "https://image.tmdb.org/t/p/w342" + backdropPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public int getPopularityScore() {
        return popularityScore;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public float getRatingAverage() {
        return ratingAverage;
    }


    private void fetchImageConfig(){
        //TODO: Return full URL for TMDB's Configuration API corespoding to desired images.

    }
}
