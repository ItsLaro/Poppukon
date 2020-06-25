package com.example.poppukon;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Rating;
import android.os.Bundle;
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

        //TODO: Data need to be
        movieRelease.setText(getIntent().getStringExtra(MainActivity.KEY_MOVIE_DATE));
        moviePopularity.setProgress(getIntent().getIntExtra(MainActivity.KEY_MOVIE_POPULARITY, 0));
        movieReviews.setText(getIntent().getStringExtra(MainActivity.KEY_MOVIE_REVIEWS));
        movieRating.setRating(getIntent().getIntExtra(MainActivity.KEY_MOVIE_RATING, 0));

        //Image Rendering
        //TODO: Replace image with YouTube trailer.
        int imagePlaceholder = R.drawable.backdrop_placeholder;
        Glide.with(this)
                .load(mediaURL)
                .placeholder(imagePlaceholder)
                .into(movieMedia);
    }
}