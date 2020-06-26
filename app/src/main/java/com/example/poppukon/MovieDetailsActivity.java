package com.example.poppukon;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Rating;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MovieDetailsActivity extends AppCompatActivity {

    TextView movieTitle;
    TextView movieOverview;
    ImageView movieMedia;
    String mediaURL;
    TextView movieRelease;
    ProgressBar moviePopularity;
    TextView movieReviews;
    RatingBar movieRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        //View references
        movieTitle = findViewById(R.id.movieTitle);
        movieOverview = findViewById(R.id.movieOverview);
        movieMedia = findViewById(R.id.movieMedia);
        movieRelease = findViewById(R.id.releaseDateNumber);
        moviePopularity = findViewById(R.id.popularityBar);
        movieReviews = findViewById(R.id.voteCount);
        movieRating = findViewById(R.id.ratingBar);

        //Data set from clicked item on Main Activity
        movieTitle.setText(getIntent().getStringExtra(MainActivity.KEY_MOVIE_TITLE));
        movieOverview.setText(getIntent().getStringExtra(MainActivity.KEY_MOVIE_OVERVIEW));
        mediaURL = getIntent().getStringExtra(MainActivity.KEY_MOVIE_MEDIAURL);

        movieRelease.setText(getIntent().getStringExtra(MainActivity.KEY_MOVIE_DATE));
        moviePopularity.setProgress(getIntent().getIntExtra(MainActivity.KEY_MOVIE_POPULARITY, 0));
        movieReviews.setText(Integer.toString(getIntent().getIntExtra(MainActivity.KEY_MOVIE_REVIEWS, 0)));
        movieRating.setRating(getIntent().getFloatExtra(MainActivity.KEY_MOVIE_RATING, 0));

        Log.d("MovieDetails", "Number of Reviews: " + getIntent().getIntExtra(MainActivity.KEY_MOVIE_REVIEWS, 0));
        Log.d("MovieDetails", "Rating:" + movieRating.getRating());
        //Image Rendering
        //TODO: Replace image with YouTube trailer.
        int imagePlaceholder = R.drawable.backdrop_placeholder;
        Glide.with(this)
                .load(mediaURL)
                .placeholder(imagePlaceholder)
                .into(movieMedia);
    }
}