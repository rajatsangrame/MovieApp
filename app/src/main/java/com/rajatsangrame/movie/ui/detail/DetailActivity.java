package com.rajatsangrame.movie.ui.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.rajatsangrame.movie.R;
import com.rajatsangrame.movie.databinding.ActivityDetailBinding;
import com.rajatsangrame.movie.data.model.home.Genre;
import com.rajatsangrame.movie.data.model.home.MovieDetail;
import com.rajatsangrame.movie.data.model.home.ProductionCompanies;
import com.rajatsangrame.movie.data.model.home.SpokenLanguages;

import java.util.List;


public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding mBinding;
    private MovieDetail movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        int id = bundle.getInt("id");
        String title = bundle.getString("title");
        mBinding.toolbar.setTitle(title);

        fetchData(id);
    }

    private void fetchData(int id) {

//        mBinding.layoutProgress.progressBar.setVisibility(View.VISIBLE);
//
//        RetrofitApi api = RetrofitClient.getInstance();
//        api.getMovieDetails(id).enqueue(new Callback<MovieDetail>() {
//
//            @SuppressLint("StringFormatMatches")
//            @Override
//            public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {
//
//                if (response.body() == null) {
//                    return;
//                }
//
//                mBinding.layoutProgress.progressBar.setVisibility(View.GONE);
//                movie = response.body();
//
//                mBinding.layoutDetail.tvDesc.setText(movie.getOverview());
//                Glide.with(DetailActivity.this)
//                        .load(Constants.IMAGE_URL + movie.getPosterPath())
//                        .into(mBinding.imageViewCollapsing);
//
//
//                String genre = getGenreFromList(movie.getGenres());
//                mBinding.layoutDetail.tvGenre.setText(genre);
//
//                String[] date = movie.getReleaseDate().split("-");
//                final String dot = "  •  ";
//                int timeDuration = movie.getRuntime();
//                String yearAndTime = getString(
//                        R.string.movie_year_and_time,
//                        date[0],
//                        dot,
//                        getRunTime(timeDuration)
//                );
//                mBinding.layoutDetail.tvYearDuration.setText(yearAndTime);
//
//                String ratingAndLike = getString(
//                        R.string.movie_rating_and_likes,
//                        movie.getVoteCount(),
//                        "    ",
//                        movie.getVoteAverage()
//                );
//
//                mBinding.layoutDetail.tvPopularity.setText(ratingAndLike);
//
//                List<ProductionCompanies> itemList = movie.getProductionCompanies();
//                if (itemList == null || itemList.isEmpty()) {
//                    return;
//                }
//
//                filterCompaniesList(itemList);
//
//                ProductionCompaniesAdapter adapter = new ProductionCompaniesAdapter(itemList,
//                        DetailActivity.this);
//                mBinding.layoutDetail.rvCompanies.setLayoutManager(
//                        new LinearLayoutManager(DetailActivity.this));
//                mBinding.layoutDetail.rvCompanies.setAdapter(adapter);
//
//                List<SpokenLanguages> languages = movie.getSpokenLanguages();
//
//                if (languages == null || languages.isEmpty()) {
//                    return;
//                }
//
//                filterLanguageList(languages);
//                String lang = getLanguages(languages);
//                mBinding.layoutDetail.tvLanguages.setText(lang);
//
//                if (movie.getHomepage() == null) {
//                    mBinding.layoutDetail.ivLink.setVisibility(View.INVISIBLE);
//
//                }
//
//                if (movie.getImdbId() == null) {
//                    mBinding.layoutDetail.ivImdb.setVisibility(View.INVISIBLE);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MovieDetail> call, Throwable t) {
//
//                mBinding.layoutProgress.progressBar.setVisibility(View.GONE);
//                showAlert();
//
//            }
//        });

    }

    private String getLanguages(List<SpokenLanguages> languages) {

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < languages.size(); i++) {

            final String dot = "  •  ";
            builder.append(dot);
            builder.append(languages.get(i).getName());

            if (i != languages.size() - 1) {
                builder.append("\n\n");
            }
        }

        return builder.toString();
    }

    private void filterCompaniesList(List<ProductionCompanies> list) {

        for (int i = 0; i < list.size(); i++) {

            if (list.get(i).getLogoPath() == null) {

                list.remove(i);
                filterCompaniesList(list);
                break;

            }
        }
    }

    private void filterLanguageList(List<SpokenLanguages> list) {

        for (int i = 0; i < list.size(); i++) {

            if (list.get(i).getName().isEmpty()) {

                list.remove(i);
                filterLanguageList(list);
                break;

            }
        }
    }

    private void showAlert() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false)
                .setMessage("No Internet Connection.")
                .setTitle("Alert")
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Bundle bundle = getIntent().getExtras();
                        assert bundle != null;
                        int id = bundle.getInt("id");
                        fetchData(id);
                    }
                });
        builder.show();
    }

    private String getGenreFromList(List<Genre> genreList) {

        final String dot = "  •  ";
        StringBuilder genre = new StringBuilder();

        if (genreList == null) {
            return genre.toString();
        }

        for (int i = 0; i < genreList.size(); i++) {

            genre.append(genreList.get(i).getName());

            if (i < genreList.size() - 1) {
                genre.append(dot);
            }
        }
        return genre.toString();
    }

    private String getRunTime(int time) {

        int hours = time / 60; //since both are ints, you get an int
        int minutes = time % 60;
        //System.out.printf("%d:%02d", hours, minutes);
        return String.format("%dh %02dmin", hours, minutes);
    }

    public void openLink(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.iv_imdb:
                String URL = "https://www.imdb.com/title/" + movie.getImdbId();
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
                startActivity(intent);
                break;
            case R.id.iv_link:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(movie.getHomepage()));
                startActivity(intent);
                break;
        }
    }
}
