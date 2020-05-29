package com.rajatsangrame.movie.paging;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.rajatsangrame.movie.R;
import com.rajatsangrame.movie.model.Movie;

import java.util.List;
import java.util.Objects;

import static com.rajatsangrame.movie.Constants.IMAGE_URL;
import static com.rajatsangrame.movie.Constants.getGenreFromId;


public class MovieAdapterNew extends PagedListAdapter<Movie, MovieAdapterNew.MovieViewHolder> {

    private MoviesAdapterListener mListener;

    public MovieAdapterNew(MoviesAdapterListener listener) {
        super(USER_COMPARATOR);
        mListener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_new, parent, false);
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

            Glide.with(itemView.getContext())
                    .load(URL)
                    .into(movieImage);

        }

        @Override
        public void onClick(View view) {

            int index = this.getAdapterPosition();
            Movie movie = getItem(index);
            mListener.onMovieItemClicked(movie, view);

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
