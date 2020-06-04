package com.rajatsangrame.movie.paging;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.rajatsangrame.movie.data.model.home.Movie;
import com.rajatsangrame.movie.databinding.HomeListItemBinding;


public class MovieAdapter extends PagedListAdapter<Movie, MovieAdapter.MovieViewHolder> {

    private MoviesAdapterListener listener;
    private Fragment fragment;

    public MovieAdapter(Fragment fragment) {
        super(USER_COMPARATOR);
        this.fragment = fragment;
    }

    public void setListener(MoviesAdapterListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        HomeListItemBinding binding = HomeListItemBinding.inflate(layoutInflater, parent, false);
        return new MovieViewHolder(binding);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.binding.setMovie(getItem(position));
        holder.binding.executePendingBindings();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        HomeListItemBinding binding;

        public MovieViewHolder(HomeListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if (listener == null) {
                return;
            }
            Movie movie = getItem(getAdapterPosition());
            listener.onMovieItemClicked(movie, view);

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
