package com.example.poppukon.models;

import android.content.Context;
import android.util.AttributeSet;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Movie {


    String posterPath;
    String title;
    String overview;

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

}
