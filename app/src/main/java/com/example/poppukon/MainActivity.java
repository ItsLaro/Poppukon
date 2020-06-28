package com.example.poppukon;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.poppukon.adapters.MovieAdapter;
import com.example.poppukon.databinding.ActivityMainBinding;
import com.example.poppukon.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    //Consts
    public static final String TAG = "MainActivity"; //logging purposes
    public static final String NOW_PLAYING_URL =
            "https://api.themoviedb.org/3/movie/now_playing?api_key=" + BuildConfig.TMDB_KEY;

    //Movie Key values for reference
    public static final String KEY_MOVIE_POSITION = "item_position";
    public static final String KEY_MOVIE_TITLE = "movie_title";
    public static final String KEY_MOVIE_OVERVIEW = "movie_overview";
    public static final String KEY_MOVIE_MEDIAURL = "movie_mediaURL";
    public static final String KEY_MOVIE_DATE = "movie_date";
    public static final String KEY_MOVIE_POPULARITY = "movie_popularity";
    public static final String KEY_MOVIE_REVIEWS = "movie_reviews";
    public static final String KEY_MOVIE_RATING = "movie_rating";
    public static final String KEY_MOVIE_TMDBID = "movie_id";

    Context thisContext = this;
    List<Movie> movies = new ArrayList<>();
    MovieAdapter moviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        View mainView = binding.getRoot();
        setContentView(mainView);

        //Adapter
        MovieAdapter.OnClickListener onClickListener = new MovieAdapter.OnClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent movieDetailsIntent = new Intent(MainActivity.this, MovieDetailsActivity.class);

                //Passing data to the intent
                movieDetailsIntent.putExtra(KEY_MOVIE_POSITION, position);
                movieDetailsIntent.putExtra(KEY_MOVIE_TITLE, movies.get(position).getTitle());
                movieDetailsIntent.putExtra(KEY_MOVIE_OVERVIEW, movies.get(position).getOverview());
                movieDetailsIntent.putExtra(KEY_MOVIE_MEDIAURL, movies.get(position).getBackdropURL());
                movieDetailsIntent.putExtra(KEY_MOVIE_DATE, movies.get(position).getReleaseDate());
                movieDetailsIntent.putExtra(KEY_MOVIE_POPULARITY, movies.get(position).getPopularityScore());
                movieDetailsIntent.putExtra(KEY_MOVIE_REVIEWS, movies.get(position).getReviewCount());
                movieDetailsIntent.putExtra(KEY_MOVIE_RATING, movies.get(position).getRatingAverage());
                movieDetailsIntent.putExtra(KEY_MOVIE_TMDBID, movies.get(position).getMovieID());

                startActivity(movieDetailsIntent);
            }
        };



        moviesAdapter = new MovieAdapter(this, movies, onClickListener);

        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
        binding.recycleView.setAdapter(moviesAdapter);


        //Requests
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON res) {
                Log.d(TAG, "onSuccess: " + statusCode);
                JSONObject jsonResponse = res.jsonObject;

                try {
                    JSONArray results = jsonResponse.getJSONArray("results");
                    movies.addAll(Movie.fromJsonArray(results));
                    moviesAdapter.notifyDataSetChanged();
                    Log.i(TAG, "Movies" + movies.toString());

                } catch (JSONException e) {
                    Log.e(TAG, "Unable to parse key 'results', " + e);
                }

            }
            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e(TAG, "onFailure: " + statusCode);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.sorting_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.sort_alphabet:
                /**
                 * Sorts by Alphabetical Ascending order.
                 */
                Collections.sort(movies, new Comparator<Movie>() {
                    @Override
                    public int compare(Movie m1, Movie m2) {
                        return m1.getTitle().compareTo(m2.getTitle());
                    }
                });

                moviesAdapter.notifyDataSetChanged();

                Log.d(TAG, "Sorted by alphabet");
                return true;

            case R.id.sort_popularity:
                /**
                 * Sorts by Popularity in descending order. This is the default.
                 */
                Collections.sort(movies, new Comparator<Movie>() {
                    @Override
                    public int compare(Movie m1, Movie m2) {
                        return m2.getPopularityScore() - m1.getPopularityScore();
                    }
                });

                moviesAdapter.notifyDataSetChanged();

                Log.d(TAG, "Sort by popularity");
                return true;

            case R.id.sort_rating:
                /**
                 * Sorts by Rating in descending order.
                 */
                Collections.sort(movies, new Comparator<Movie>() {
                    @Override
                    public int compare(Movie m1, Movie m2) {
                        if(m2.getRatingAverage() > m1.getRatingAverage()){
                            return 1;
                        }
                        if(m1.getRatingAverage() > m2.getRatingAverage()){
                            return -1;
                        }
                        return 0;
                    }
                });

                moviesAdapter.notifyDataSetChanged();

                Log.d(TAG, "Sort by rating");
                return true;

            case R.id.sort_date:
                /**
                 * Sorts by date in descending order. Latest movies first.
                 */
                Collections.sort(movies, new Comparator<Movie>() {
                    @Override
                    public int compare(Movie movie1, Movie movie2) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                        Date date1, date2;
                        try {
                            date1 = format.parse(movie1.getReleaseDate());
                            date2 = format.parse(movie2.getReleaseDate());
                            return date2.compareTo(date1);
                        } catch (ParseException e) {
                            Log.e(TAG, "Error parsing dates for comparison: " + e);
                            return 0;
                        }

                    }
                });

                moviesAdapter.notifyDataSetChanged();

                Log.d(TAG, "Sort by release date");
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}