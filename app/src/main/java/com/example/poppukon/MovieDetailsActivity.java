package com.example.poppukon;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Rating;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.poppukon.databinding.ActivityMovieDetailsBinding;

public class MovieDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ViewBinding to reduce boilerplate of finding Views by R.id.name
        ActivityMovieDetailsBinding binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //Data set from clicked item on Main Activity assigned to the appropriate view
        binding.movieTitle.setText(getIntent().getStringExtra(MainActivity.KEY_MOVIE_TITLE));
        binding.movieOverview.setText(getIntent().getStringExtra(MainActivity.KEY_MOVIE_OVERVIEW));
        binding.releaseDateNumber.setText(getIntent().getStringExtra(MainActivity.KEY_MOVIE_DATE));
        binding.popularityBar.setProgress(getIntent().getIntExtra(MainActivity.KEY_MOVIE_POPULARITY, 0));
        binding.voteCount.setText(Integer.toString(getIntent().getIntExtra(MainActivity.KEY_MOVIE_REVIEWS, 0)));
        binding.ratingBar.setRating(getIntent().getFloatExtra(MainActivity.KEY_MOVIE_RATING, 0));

        String mediaURL = getIntent().getStringExtra(MainActivity.KEY_MOVIE_MEDIAURL);
        //Image Rendering
        //TODO: Replace image with YouTube trailer.
        int imagePlaceholder = R.drawable.backdrop_placeholder;
        Glide.with(this)
                .load(mediaURL)
                .placeholder(imagePlaceholder)
                .into(binding.movieMedia);
    }
}