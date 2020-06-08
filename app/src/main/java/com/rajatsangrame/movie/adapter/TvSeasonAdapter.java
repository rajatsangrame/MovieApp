package com.rajatsangrame.movie.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rajatsangrame.movie.R;
import com.rajatsangrame.movie.data.model.tv.Seasons;

import java.util.List;

import static com.rajatsangrame.movie.util.Constants.IMAGE_URL;

/**
 * Created by Rajat Sangrame on 19/12/19.
 * http://github.com/rajatsangrame
 */
public class TvSeasonAdapter extends RecyclerView.Adapter<TvSeasonAdapter.ViewHolder> {

    private List<Seasons> list;

    public void setList(List<Seasons> list) {
        if (list == null) return;
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TvSeasonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                         int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.season_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvSeasonAdapter.ViewHolder holder, int position) {

        holder.title.setText(list.get(position).getName());

        final String URL = IMAGE_URL + list.get(position).getPosterPath();
        Glide.with(holder.image.getContext())
                .load(URL)
                .placeholder(R.color.cardBackground)
                .into(holder.image);

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.iv_image);
            title = itemView.findViewById(R.id.tv_title);
        }
    }
}
