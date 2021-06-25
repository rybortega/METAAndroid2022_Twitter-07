package com.example.flixter.adapters;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixter.BuildConfig;
import com.example.flixter.R;
import com.example.flixter.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class MovieTrailerActivity extends YouTubeBaseActivity {

    private final String MOVIE_API_KEY = BuildConfig.MOVIE_API_KEY;
    private final String YOUTUBE_API_KEY = BuildConfig.YOUTUBE_API_KEY;
    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_trailer);

        //Get movie info
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        final String movieId = String.valueOf(movie.getId());

        String url = "https://api.themoviedb.org/3/movie/" + movieId + "/videos?api_key="+ MOVIE_API_KEY +"&language=en-US";

        //Resolve the player view from the Layout
        YouTubePlayerView playerView = (YouTubePlayerView) findViewById(R.id.player);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                try {
                    JSONArray results = json.jsonObject.getJSONArray("results");
                    String videoID = results.getJSONObject(0).getString("key");

                    if (!videoID.isEmpty()) {
                        playerView.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
                            @Override
                            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                                YouTubePlayer youTubePlayer, boolean b) {
                                // do any work here to cue video, play video, etc.
                                Log.d("onCreate", "Playing video");
                                youTubePlayer.cueVideo(videoID);
                            }

                            @Override
                            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                                YouTubeInitializationResult youTubeInitializationResult) {
                                // Log the error
                                Log.e("MovieTrailerActivity", "Error initializing YouTube player");
                            }
                        });
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.d("onCreate", "Failed to load resource");
            }
        });
    }
}