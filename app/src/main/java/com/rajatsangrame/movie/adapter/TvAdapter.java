package com.rajatsangrame.movie.adapter;

import android.annotation.SuppressLint;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.rajatsangrame.movie.data.db.tv.TVDB;
import com.rajatsangrame.movie.databinding.HomeListItemBinding;


public class TvAdapter extends PagedListAdapter<TVDB, TvAdapter.MovieViewHolder> {

    private OnClickListener listener;
    private Fragment fragment;

    public TvAdapter(Fragment fragment) {
        super(USER_COMPARATOR);
        this.fragment = fragment;
    }

    public void setListener(OnClickListener listener) {
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
        holder.binding.setTv(getItem(position));
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
            TVDB movie = getItem(getAdapterPosition());
            listener.onItemClicked(movie, view);

        }
    }

    private static final DiffUtil.ItemCallback<TVDB> USER_COMPARATOR
            = new DiffUtil.ItemCallback<TVDB>() {

        @Override
        public boolean areItemsTheSame(@NonNull TVDB oldItem, @NonNull TVDB newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull TVDB oldItem, @NonNull TVDB newItem) {
            //return oldItem == newItem;
            return oldItem.getId() == newItem.getId();
        }
    };

}
