package com.example.flixter;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Rating;
import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.flixter.models.Movie;

import org.parceler.Parcels;

public class MovieDetailsActivity extends AppCompatActivity {

    //Movie to display
    Movie movie;

    //View objects
    TextView tvDetailsTitle;
    TextView tvDetailsOverview;
    RatingBar rbVoteAverage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        //Resolve the view Objects
        tvDetailsTitle = (TextView) findViewById(R.id.tvDetailsTitle);
        tvDetailsOverview = (TextView) findViewById(R.id.tvDetailsOverview);
        rbVoteAverage = (RatingBar) findViewById(R.id.rbVoteAverage);

        //Unwrap the movie that was passed via the intent, we use the SimpleName as its key
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s", movie.getTitle()));

        tvDetailsTitle.setText(movie.getTitle());
        tvDetailsOverview.setText(movie.getOverview());

        //Vote average is 0-10, we simply divide by 2 to convert to 0-5 scale
        float voteAverage = movie.getVoteAverage().floatValue() / 2;
        rbVoteAverage.setRating(voteAverage);
    }
}