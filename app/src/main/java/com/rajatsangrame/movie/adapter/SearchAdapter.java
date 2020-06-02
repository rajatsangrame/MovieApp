package com.rajatsangrame.movie.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rajatsangrame.movie.R;
import com.rajatsangrame.movie.data.model.search.SearchResult;
import com.rajatsangrame.movie.util.Constants;

import java.util.List;

import static com.rajatsangrame.movie.util.Constants.IMAGE_URL;
import static com.rajatsangrame.movie.util.Constants.getGenreFromId;

/**
 * Created by Rajat Sangrame on 2/6/20.
 * http://github.com/rajatsangrame
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MovieViewHolder> {

    private MoviesAdapterListener listener;
    private final Fragment fragment;
    private List<SearchResult> searchResultList;

    public SearchAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    public void setMovieList(List<SearchResult> searchResults) {
        this.searchResultList = searchResults;
        notifyDataSetChanged();
    }

    public void setListener(MoviesAdapterListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        if (viewType == Constants.HEADER) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.search_item_header, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.search_item, parent, false);

        }
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        SearchResult movie = searchResultList.get(position);
        if (movie.getItemType() == Constants.HEADER) {
            holder.header.setText(movie.getMediaType().toUpperCase());
        } else {
            holder.bind(movie);
        }
    }


    @Override
    public int getItemViewType(int position) {
        SearchResult result = searchResultList.get(position);
        return result.getItemType();
    }

    @Override
    public int getItemCount() {
        return searchResultList == null ? 0 : searchResultList.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView movieImage;
        private TextView movieTitle;
        private TextView movieYear;
        private TextView movieGenre;
        private TextView header;


        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            movieTitle = itemView.findViewById(R.id.tv_movie_title);
            movieYear = itemView.findViewById(R.id.tv_movie_year);
            movieImage = itemView.findViewById(R.id.iv_movie_image);
            movieGenre = itemView.findViewById(R.id.tv_movie_genre);

            //movieImage.setOnClickListener(this);
            try {
                header = itemView.findViewById(R.id.tv_search_header);
            } catch (NullPointerException e) {

            }
            //itemView.setOnClickListener(this);

        }

        void bind(SearchResult movie) {

            assert movie != null;
            if (movie.getTitle() != null) {
                movieTitle.setText(movie.getTitle());
            }

//            String[] date = movie.getReleaseDate().split("-");
//            final String dot = "  •  ";
//            String result = itemView.getContext().getString(
//                    R.string.movie_year,
//                    movie.getVoteAverage(),
//                    dot,
//                    date[0]);
//            movieYear.setText(result);
//            List<Integer> genreList = movie.getGenreIds();
//            String genre = getGenreFromList(genreList);
//            movieGenre.setText(genre);

            final String URL = IMAGE_URL + movie.getBackdropPath();

            Glide.with(itemView.getContext())
                    .load(URL)
                    .placeholder(R.color.cardBackground)
                    .into(movieImage);

        }

        @Override
        public void onClick(View view) {

            if (listener == null) {
                return;
            }
            int index = this.getAdapterPosition();
            SearchResult movie = searchResultList.get(index);
            listener.onItemClicked(movie, view);

        }
    }

    private String getGenreFromList(List<Integer> genreList) {

        final String dot = "  •  ";
        StringBuilder genre = new StringBuilder();

        if (genreList == null) {
            return genre.toString();
        }

        if (genreList.size() > 3) {

            genreList.remove(genreList.size() - 1);
            getGenreFromList(genreList);

        }

        for (int i = 0; i < genreList.size(); i++) {

            genre.append(getGenreFromId(genreList.get(i)));
            if (i < genreList.size() - 1) {
                genre.append(dot);
            }
        }

        return genre.toString();
    }

    public interface MoviesAdapterListener {
        void onItemClicked(SearchResult movie, View view);
    }
}
