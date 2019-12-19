package com.example.rajat.movie.paging;

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
import com.example.rajat.movie.R;
import com.example.rajat.movie.model.Movie;

import java.util.List;
import java.util.Objects;

import static com.example.rajat.movie.Constants.IMAGE_URL;
import static com.example.rajat.movie.Constants.getGenreFromId;


public class MovieAdapter extends PagedListAdapter<Movie, MovieAdapter.MovieViewHolder> {

    private MoviesAdapterListener mListener;

    public MovieAdapter(MoviesAdapterListener listener) {
        super(USER_COMPARATOR);
        mListener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new MovieViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bind(Objects.requireNonNull(getItem(position)));
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView movieTitle;
        private TextView movieYear;
        private TextView movieGenre;
        private ImageView movieImage;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            movieTitle = itemView.findViewById(R.id.tv_movie_title);
            movieYear = itemView.findViewById(R.id.tv_movie_year);
            movieImage = itemView.findViewById(R.id.iv_movie_image);
            movieGenre = itemView.findViewById(R.id.tv_movie_genre);

            movieImage.setOnClickListener(this);
            itemView.setOnClickListener(this);

        }

        @SuppressLint("StringFormatInvalid")
        void bind(Movie movie) {

            assert movie != null;
            if (movie.getTitle() != null) {
                movieTitle.setText(movie.getTitle());
            }

            String[] date = movie.getReleaseDate().split("-");
            final String dot = "  •  ";
            String result = itemView.getContext().getString(
                    R.string.movie_year,
                    movie.getVoteAverage(),
                    dot,
                    date[0]);
            movieYear.setText(result);
            List<Integer> genreList = movie.getGenreIds();
            String genre = getGenreFromList(genreList);
            movieGenre.setText(genre);

            final String URL = IMAGE_URL + movie.getBackdropPath();

            Glide.with(itemView.getContext())
                    .load(URL)
                    .apply(new RequestOptions().transforms(new CenterCrop(),
                            new RoundedCorners(8))
                            .error(itemView.getContext().getResources()
                                    .getDrawable(R.color.cardBackground)))
                    .into(movieImage);

        }

        @Override
        public void onClick(View view) {

            int index = this.getAdapterPosition();
            Movie movie = getItem(index);
            mListener.onMovieItemClicked(movie, view);

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
