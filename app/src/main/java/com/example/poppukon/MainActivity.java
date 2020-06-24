package com.example.poppukon;

        import androidx.appcompat.app.AppCompatActivity;

        import android.os.Bundle;
        import android.util.Log;
        import android.widget.Toast;

        import com.codepath.asynchttpclient.AsyncHttpClient;
        import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity"; //logging purposes
    public static final String NOW_PLAYING_URL =
            "https://api.themoviedb.org/3/movie/now_playing?api_key=" + BuildConfig.TMDB_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, NOW_PLAYING_URL);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON res) {
                Log.d(TAG, "onSuccess: " + statusCode);
                JSONObject jsonResponse = res.jsonObject;

                try {
                    JSONArray results = jsonResponse.getJSONArray("results");
                    Log.i(TAG, "Parsed from 'results': " + results.toString());
                } catch (JSONException e) {
                    Log.e(TAG, "Unable to parse key 'results', " + e);
                }

            }
            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure: " + statusCode);
            }
        });
    }
}