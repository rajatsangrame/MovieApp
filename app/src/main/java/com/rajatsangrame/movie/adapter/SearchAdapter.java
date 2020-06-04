package com.rajatsangrame.movie.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rajatsangrame.movie.R;
import com.rajatsangrame.movie.data.model.search.SearchResult;
import com.rajatsangrame.movie.databinding.SearchItemBinding;
import com.rajatsangrame.movie.databinding.SearchItemHeaderBinding;
import com.rajatsangrame.movie.paging.MovieAdapter;
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
    private SearchItemBinding binding;
    private SearchItemHeaderBinding headerBinding;

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

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == Constants.HEADER) {
            headerBinding = DataBindingUtil.inflate(layoutInflater,
                    R.layout.search_item_header, parent, false);
            return new MovieViewHolder(headerBinding.getRoot());

        } else {
            binding = DataBindingUtil.inflate(layoutInflater,
                    R.layout.search_item, parent, false);
            return new MovieViewHolder(binding.getRoot());

        }
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        SearchResult movie = searchResultList.get(position);
        if (movie.getItemType() == Constants.HEADER) {
            headerBinding.tvSearchHeader.setText(movie.getMediaType().toUpperCase());
        } else {
            holder.bind(movie);
        }
    }


    @Override
    public int getItemViewType(int position) {
        SearchResult result = searchResultList.get(position);
        if (result.getItemType() == Constants.HEADER) {
            return Constants.HEADER;
        } else if (result.getItemType() == Constants.ITEM) {
            return Constants.ITEM;
        } else {
            return super.getItemViewType(position);
        }
    }

    @Override
    public int getItemCount() {
        return searchResultList == null ? 0 : searchResultList.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

        }

        void bind(SearchResult result) {

            if (result.getMediaType().equals("person")) {
                binding.llPerson.setVisibility(View.VISIBLE);
                binding.llMovie.setVisibility(View.GONE);

                binding.tvPersonName.setText(result.getName());
                binding.tvPersonKnown.setText(result.getKnownForDepartment());
                return;
            }

            binding.llMovie.setVisibility(View.VISIBLE);
            binding.llPerson.setVisibility(View.GONE);
            if (result.getMediaType().equals("tv")) {
                binding.tvMovieTitle.setText(result.getName());
            }else{
                binding.tvMovieTitle.setText(result.getTitle());
            }
            List<Integer> genreList = result.getGenreIds();
            String genre = getGenreFromList(genreList);
            binding.tvMovieGenre.setText(genre);
            final String URL = Constants.IMAGE_URL + result.getBackdropPath();
            Glide.with(itemView.getContext())
                    .load(URL)
                    .placeholder(R.color.cardBackground)
                    .into(binding.ivMovieImage);

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
