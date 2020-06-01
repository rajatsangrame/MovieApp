package com.rajatsangrame.movie.paging;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rajatsangrame.movie.R;
import com.rajatsangrame.movie.data.model.Movie;

import java.util.Objects;

import static com.rajatsangrame.movie.util.Constants.IMAGE_URL;

public class MovieAdapter extends PagedListAdapter<Movie, MovieAdapter.MovieViewHolder> {

    private MoviesAdapterListener listener;
    private Fragment fragment;
    boolean isLargeView;


    public MovieAdapter(Fragment fragment, boolean isLargeView) {
        super(USER_COMPARATOR);
        this.fragment = fragment;
        this.isLargeView = isLargeView;
    }

    public void setListener(MoviesAdapterListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (!isLargeView) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_large, parent, false);
        }
        return new MovieViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bind(Objects.requireNonNull(getItem(position)));
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView movieImage;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            movieImage = itemView.findViewById(R.id.iv_movie_image);
            itemView.setOnClickListener(this);

        }

        @SuppressLint("StringFormatInvalid")
        void bind(Movie movie) {

            assert movie != null;
            final String URL = IMAGE_URL + movie.getPosterPath();

            Glide.with(fragment)
                    .load(URL)
                    .placeholder(R.color.cardBackground)
                    .into(movieImage);

        }

        @Override
        public void onClick(View view) {

            if (listener != null) {

                int index = this.getAdapterPosition();
                Movie movie = getItem(index);
                listener.onMovieItemClicked(movie, view);

            }
        }
    }

    private static final DiffUtil.ItemCallback<Movie> USER_COMPARATOR
            = new DiffUtil.ItemCallback<Movie>() {

        @Override
        public boolean areItemsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
            return oldItem == newItem;
        }
    };


    public interface MoviesAdapterListener {

        void onMovieItemClicked(Movie movie, View view);

    }
}
