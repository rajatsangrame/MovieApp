package com.rajatsangrame.movie.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rajatsangrame.movie.R;
import com.rajatsangrame.movie.data.model.home.Movie;
import com.rajatsangrame.movie.data.model.search.SearchResult;

import java.util.List;

import static com.rajatsangrame.movie.util.Constants.IMAGE_URL;

/**
 * Created by Rajat Sangrame on 2/6/20.
 * http://github.com/rajatsangrame
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MovieViewHolder> {

    private MoviesAdapterListener listener;
    private final Fragment fragment;
    private List<SearchResult> movieList;

    public SearchAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    public void setMovieList(List<SearchResult> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    public void setListener(MoviesAdapterListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_list_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        SearchResult movie = movieList.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return movieList == null ? 0 : movieList.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView movieImage;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            movieImage = itemView.findViewById(R.id.iv_movie_image);
            itemView.setOnClickListener(this);

        }

        void bind(SearchResult movie) {

            assert movie != null;
            final String URL = IMAGE_URL + movie.getPosterPath();

            Glide.with(itemView.getContext())
                    .load(URL)
                    .into(movieImage);

        }

        @Override
        public void onClick(View view) {

            if (listener == null) {
                return;
            }
            int index = this.getAdapterPosition();
            SearchResult movie = movieList.get(index);
            listener.onItemClicked(movie, view);

        }
    }

    public interface MoviesAdapterListener {
        void onItemClicked(SearchResult movie, View view);
    }
}
