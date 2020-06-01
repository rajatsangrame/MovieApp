package com.rajatsangrame.movie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rajatsangrame.movie.R;
import com.rajatsangrame.movie.data.model.home.ProductionCompanies;

import java.util.List;

import static com.rajatsangrame.movie.util.Constants.IMAGE_URL;

/**
 * Created by Rajat Sangrame on 19/12/19.
 * http://github.com/rajatsangrame
 */
public class ProductionCompaniesAdapter extends
        RecyclerView.Adapter<ProductionCompaniesAdapter.ViewHolder> {

    private List<ProductionCompanies> list;
    private Context context;

    public ProductionCompaniesAdapter(List<ProductionCompanies> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductionCompaniesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                    int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.production_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductionCompaniesAdapter.ViewHolder holder, int position) {

        holder.title.setText(list.get(position).getName());

        final String URL = IMAGE_URL + list.get(position).getLogoPath();
        Glide.with(context)
                .load(URL)
                .error(context.getResources()
                        .getDrawable(R.color.cardBackground))
                .into(holder.image);

    }

    @Override
    public int getItemCount() {
        return list.size();
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
