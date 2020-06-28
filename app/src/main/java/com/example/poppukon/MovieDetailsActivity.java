package com.example.poppukon;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.poppukon.databinding.ActivityMovieDetailsBinding;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Headers;

public class MovieDetailsActivity extends AppCompatActivity {

    //Consts
    public static final String TAG = "MovieDetailsActivity"; //logging purposes
    public static final String KEY_TRAILER_ID = "movie_trailer"; //Trailer key values for reference
    int movieID;
    String VIDEOS_URL = "https://api.themoviedb.org/3/movie/";
    String trailerKey;
    ImageButton buttonYouTube;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Building URL for request
        movieID = getIntent().getIntExtra(MainActivity.KEY_MOVIE_TMDBID, 0);
        VIDEOS_URL += movieID + "/videos?api_key=" + BuildConfig.TMDB_KEY + "&language=en-US";
        Log.i(TAG, VIDEOS_URL);

        //Sets title/logo on action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.title_poppukon);
        actionBar.setDisplayShowTitleEnabled(true);

        //ViewBinding
        ActivityMovieDetailsBinding binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        buttonYouTube = binding.playButton;

        //Views are set to the display the appropriate data from the movie clicked item on Main Activity
        binding.movieTitle.setText(getIntent().getStringExtra(MainActivity.KEY_MOVIE_TITLE));
        binding.movieOverview.setText(getIntent().getStringExtra(MainActivity.KEY_MOVIE_OVERVIEW));
        binding.releaseDateNumber.setText(getIntent().getStringExtra(MainActivity.KEY_MOVIE_DATE));
        binding.voteCount.setText(Integer.toString(getIntent().getIntExtra(MainActivity.KEY_MOVIE_REVIEWS, 0)));
        binding.ratingBar.setRating(getIntent().getFloatExtra(MainActivity.KEY_MOVIE_RATING, 0));

        int popularity = getIntent().getIntExtra(MainActivity.KEY_MOVIE_POPULARITY, 0);
        binding.popularityBar.setProgress(popularity);
        binding.popularityScore.setText(Integer.toString(popularity));

        //Depending on popularity level, bar display a different color
        if (popularity <= 50){
            binding.popularityBar.setProgressDrawable(getResources().getDrawable(R.drawable.red_progress));
        }
        else if (popularity <= 100){
            binding.popularityBar.setProgressDrawable(getResources().getDrawable(R.drawable.orange_progress));
        }
        else if (popularity <= 150){
            binding.popularityBar.setProgressDrawable(getResources().getDrawable(R.drawable.green_progress));
        }
        else{
            binding.popularityBar.setProgressDrawable(getResources().getDrawable(R.drawable.blue_progress));
        }

        String mediaURL = getIntent().getStringExtra(MainActivity.KEY_MOVIE_MEDIAURL);

        //Async call to get appropriate YouTube ID for the movie
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(VIDEOS_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON res) {
                Log.d(TAG, "onSuccess: " + statusCode);
                JSONObject jsonResponse = res.jsonObject;

                try {
                    JSONObject results = jsonResponse.getJSONArray("results").getJSONObject(0);
                    trailerKey = results.getString("key");
                    Log.i(TAG, "Results: " + results.toString());

                    //Play button toggles visibility depending on video availability
                    buttonYouTube.setImageResource(R.drawable.yt_icon_rgb);
                    buttonYouTube.setClickable(true);

                } catch (JSONException e) {
                    Log.e(TAG, "Unable to parse key 'key', " + e);
                }

            }
            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e(TAG, "onFailure: " + statusCode);
            }
        });

        //Image Rendering
        //TODO: Replace image with YouTube trailer.
        int imagePlaceholder = R.drawable.backdrop_placeholder;
        Glide.with(this)
                .load(mediaURL)
                .placeholder(imagePlaceholder)
                .into(binding.movieMedia);

        //Setting listener and intent to initiate MovieTrailerActivity
        buttonYouTube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (trailerKey == null || trailerKey.trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Trailer not available", Toast.LENGTH_SHORT).show();
                } else {
                    Intent trailerIntent = new Intent(MovieDetailsActivity.this, MovieTrailerActivity.class);
                    trailerIntent.putExtra(KEY_TRAILER_ID, trailerKey);
                    startActivity(trailerIntent);
                }
            }
        });
    }
}
