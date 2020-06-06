package com.rajatsangrame.movie.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.rajatsangrame.movie.R;
import com.rajatsangrame.movie.data.model.search.SearchResult;
import com.rajatsangrame.movie.databinding.SearchItemBinding;
import com.rajatsangrame.movie.databinding.SearchItemHeaderBinding;
import com.rajatsangrame.movie.util.Constants;

import java.util.List;

/**
 * Created by Rajat Sangrame on 2/6/20.
 * http://github.com/rajatsangrame
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MovieViewHolder> {

    private OnClickListener listener;
    private final Fragment fragment;
    private List<SearchResult> searchResultList;

    public SearchAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    public void setMovieList(List<SearchResult> searchResults) {
        this.searchResultList = searchResults;
        notifyDataSetChanged();
    }

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == Constants.HEADER) {
            SearchItemHeaderBinding headerBinding = DataBindingUtil.inflate(layoutInflater,
                    R.layout.search_item_header, parent, false);
            return new MovieViewHolder(headerBinding);

        } else {
            SearchItemBinding binding = DataBindingUtil.inflate(layoutInflater,
                    R.layout.search_item, parent, false);
            return new MovieViewHolder(binding);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        SearchResult movie = searchResultList.get(position);
        holder.bind(movie);
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

        SearchItemBinding binding;
        SearchItemHeaderBinding headerBinding;

        public MovieViewHolder(SearchItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public MovieViewHolder(SearchItemHeaderBinding binding) {
            super(binding.getRoot());
            this.headerBinding = binding;
        }

        void bind(SearchResult result) {

            if (binding != null) {
                if (result.getMediaType().equals("person")) {
                    binding.llPerson.setVisibility(View.VISIBLE);
                } else if (result.getMediaType().equals("tv")) {
                    binding.llTv.setVisibility(View.VISIBLE);
                } else {
                    binding.llMovie.setVisibility(View.VISIBLE);
                }
                binding.setSearch(result);
                binding.executePendingBindings();
                return;
            }

            headerBinding.setSearch(result);
            headerBinding.executePendingBindings();
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

    public interface OnClickListener {
        void onItemClicked(SearchResult movie, View view);
    }

}
