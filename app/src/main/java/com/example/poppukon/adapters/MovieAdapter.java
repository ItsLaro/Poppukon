package com.example.poppukon.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.media.Rating;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.poppukon.R;
import com.example.poppukon.models.Movie;

import org.w3c.dom.Text;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    Context context;
    List<Movie> movies;
    OnClickListener clickListener;

    public interface  OnClickListener{
        void onItemClick(int position);
    }

    public MovieAdapter(Context context, List<Movie> movies, OnClickListener clickListener) {
        this.context = context;
        this.movies = movies;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View movieView = LayoutInflater.from(context).inflate(R.layout.movie_item, parent,false);
        return new ViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.bind(movie);

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView itemTitle;
        TextView itemOverview;
        ImageView itemPoster;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.itemTitle);
            itemOverview = itemView.findViewById(R.id.itemOverview);
            itemPoster = itemView.findViewById(R.id.itemPoster);

        }

        public void bind(Movie movie) {
            /**
             * Sets text of views inside ViewHolder to values from a movie element inside movies
             */
            itemTitle.setText(movie.getTitle());
            itemOverview.setText(movie.getOverview());

            //Poster/Backdrop
            //Binds listener for tap to title of ViewHolder
            itemTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClick(getAdapterPosition());
                }
            });

            //These depends on phone orientation
            String imageURL;
            int imagePlaceholder;

            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                imageURL = movie.getPosterURL();
                imagePlaceholder = R.drawable.poster_placeholder;
            }
            else{
                imageURL = movie.getBackdropURL();
                imagePlaceholder = R.drawable.backdrop_placeholder;
            }
            Glide.with(context)
                    .load(imageURL)
                    .placeholder(imagePlaceholder)
                    .into(itemPoster);
        }
    }
}
