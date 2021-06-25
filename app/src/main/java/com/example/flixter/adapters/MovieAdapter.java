package com.example.flixter.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixter.MovieDetailsActivity;
import com.example.flixter.R;
import com.example.flixter.models.Movie;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    //Usually involves inflating a layout form XML and returning the holder
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    //Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder " + position);
        //Get the movie at the passed position
        Movie movie = movies.get(position);

        //Bind the movie into the VH
        holder.bind(movie);
    }

    //returns the totla count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvDetailsTitle);
            tvOverview = itemView.findViewById(R.id.tvDetailsOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            itemView.setOnClickListener(this);
        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageUrl;
            //if phone is in landscape, set imageUrl = back drop image, otherwise set to poster image
            imageUrl = context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ?
                movie.getBackdropPath() : movie.getPosterPath();


            int transformRadius = 30;
            int transformMargin = 10;

            //Use Glide to load image into imageView
            Glide.with(context)
                    .load(imageUrl).centerCrop()
                    .transform(new RoundedCornersTransformation(transformRadius, transformMargin))
                    .placeholder(R.drawable.placeholder_portrait).into(ivPoster);
        }

        //When the user clicks on a row, show MovieDetailsActivity for the selected movie
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            //We should make sure the position is actually in the view
            if (position != RecyclerView.NO_POSITION) {

                //Get the movie at the correct position
                Movie movie = movies.get(position);

                //Create intent for the new activity
                Intent intent = new Intent(context, MovieDetailsActivity.class);

                //Serialize the movie using Parceler
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));

                //Show the activity
                context.startActivity(intent);

            }

        }
    }
}
