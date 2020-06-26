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

    MovieItemBinding binding;

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
        binding = MovieItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        View movieView = binding.getRoot();
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
            RelativeLayout movieItemContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.itemTitle);
            itemOverview = itemView.findViewById(R.id.itemOverview);
            itemPoster = itemView.findViewById(R.id.itemPoster);
            movieItemContainer = itemView.findViewById(R.id.movieItemContainer);

        }

        public void bind(@NotNull Movie movie) {
            /**
             * Sets text of views inside ViewHolder to values from a movie element inside movies
             */
            itemTitle.setText(movie.getTitle());
            itemOverview.setText(movie.getOverview());

            //Poster/Backdrop
            //Binds listener for tap to title of ViewHolder
            movieItemContainer.setOnClickListener(new View.OnClickListener() {
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
                    .into(itemPoster);
        }
    }
}
