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
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.bumptech.glide.Glide;
import com.example.poppukon.R;
import com.example.poppukon.databinding.MovieItemBinding;
import com.example.poppukon.models.Movie;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

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
        MovieItemBinding binding = MovieItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
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
        MovieItemBinding binding;

        public ViewHolder(@NonNull MovieItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(@NotNull Movie movie) {
            /**
             * Sets text of views inside ViewHolder to values from a movie element inside movies
             */
            binding.itemTitle.setText(movie.getTitle());
            binding.itemOverview.setText(movie.getOverview());

            //Poster/Backdrop
            //Binds listener for tap to title of ViewHolder
            binding.movieItemContainer.setOnClickListener(new View.OnClickListener() {
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
            int radius = 30; // corner radius
            int margin = 10; // crop margin
            Glide.with(context)
                    .load(imageURL)
                    .transform(new RoundedCornersTransformation(radius, margin))
                    .placeholder(imagePlaceholder)
                    .into(binding.itemPoster);
        }
    }
}
