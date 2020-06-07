package com.rajatsangrame.movie.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.rajatsangrame.movie.R;
import com.rajatsangrame.movie.data.db.movie.MovieDB;
import com.rajatsangrame.movie.data.db.tv.TVDB;
import com.rajatsangrame.movie.databinding.SavedItemBinding;

import java.util.List;

/**
 * Created by Rajat Sangrame on 2/6/20.
 * http://github.com/rajatsangrame
 */

public class SavedAdapter<T> extends RecyclerView.Adapter<SavedAdapter.ViewHolder> {

    private OnClickListener listener;
    private List<T> saveItemList;

    public SavedAdapter() {
    }

    public OnClickListener getListener() {
        return listener;
    }

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    public List<T> getSaveItemList() {
        return saveItemList;
    }

    public void setSaveItemList(List<T> saveItemList) {
        this.saveItemList = saveItemList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SavedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        SavedItemBinding binding = DataBindingUtil.inflate(layoutInflater,
                R.layout.saved_item, parent, false);
        return new SavedAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedAdapter.ViewHolder holder, int position) {
        if (saveItemList.get(position) instanceof MovieDB) {
            holder.binding.setMovie((MovieDB) saveItemList.get(position));
            return;
        }
        holder.binding.setTv((TVDB) saveItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return saveItemList != null ? saveItemList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        SavedItemBinding binding;

        public ViewHolder(SavedItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if (saveItemList.get(getAdapterPosition()) instanceof MovieDB) {
                listener.onItemClicked((MovieDB) saveItemList.get(getAdapterPosition()), view);
                return;
            }

            if (saveItemList.get(getAdapterPosition()) instanceof TVDB) {
                listener.onItemClicked((TVDB) saveItemList.get(getAdapterPosition()), view);
            }
        }
    }
}
