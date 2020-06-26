package com.example.poppukon;

        import androidx.appcompat.app.AppCompatActivity;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import android.content.Context;
        import android.content.Intent;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Toast;

        import com.codepath.asynchttpclient.AsyncHttpClient;
        import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
        import com.example.poppukon.adapters.MovieAdapter;
        import com.example.poppukon.databinding.ActivityMainBinding;
        import com.example.poppukon.models.Movie;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;
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

    public static final int  EDIT_TEXT_CODE = 1;

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
            //TODO: Finish implementing!
            @Override
            public void onItemClick(int position) {
                Intent movieDetailsIntent = new Intent(MainActivity.this, MovieDetailsActivity.class);

                //Passing data to the intent
                //TODO: Pass in appropriate data
                movieDetailsIntent.putExtra(KEY_MOVIE_POSITION, position);
                movieDetailsIntent.putExtra(KEY_MOVIE_TITLE, movies.get(position).getTitle());
                movieDetailsIntent.putExtra(KEY_MOVIE_OVERVIEW, movies.get(position).getOverview());
                movieDetailsIntent.putExtra(KEY_MOVIE_MEDIAURL, movies.get(position).getBackdropURL());
                movieDetailsIntent.putExtra(KEY_MOVIE_DATE, movies.get(position).getReleaseDate());
                movieDetailsIntent.putExtra(KEY_MOVIE_POPULARITY, movies.get(position).getPopularityScore());
                movieDetailsIntent.putExtra(KEY_MOVIE_REVIEWS, movies.get(position).getReviewCount());
                movieDetailsIntent.putExtra(KEY_MOVIE_RATING, movies.get(position).getRatingAverage());

                startActivityForResult(movieDetailsIntent, EDIT_TEXT_CODE); //TODO: Research different purposes
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
}