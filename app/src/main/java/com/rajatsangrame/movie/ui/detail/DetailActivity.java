package com.rajatsangrame.movie.ui.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.rajatsangrame.movie.App;
import com.rajatsangrame.movie.R;
import com.rajatsangrame.movie.data.db.MovieDB;
import com.rajatsangrame.movie.data.rest.ApiCallback;
import com.rajatsangrame.movie.databinding.ActivityDetailBinding;
import com.rajatsangrame.movie.data.model.home.Genre;
import com.rajatsangrame.movie.data.model.home.ProductionCompanies;
import com.rajatsangrame.movie.data.model.home.SpokenLanguages;
import com.rajatsangrame.movie.di.component.DaggerDetailActivityComponent;
import com.rajatsangrame.movie.di.component.DetailActivityComponent;
import com.rajatsangrame.movie.di.module.DetailActivityModule;
import com.rajatsangrame.movie.util.ViewModelFactory;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;


public class DetailActivity extends AppCompatActivity implements ApiCallback {

    @Inject
    ViewModelFactory viewModelFactory;

    private DetailViewModel detailViewModel;
    private ActivityDetailBinding binding;
    private LiveData<MovieDB> movieDb;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        getDependency();
        detailViewModel = new ViewModelProvider(this, viewModelFactory).get(DetailViewModel.class);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        int id = bundle.getInt("id");
        String title = bundle.getString("title");
        binding.toolbar.setTitle(title);

        fetchData(id);
    }

    private void getDependency() {

        DetailActivityComponent component = DaggerDetailActivityComponent
                .builder()
                .applicationComponent(App.get(this).getComponent())
                .build();
        component.injectDetailActivity(this);

    }

    private void fetchData(int id) {
        detailViewModel.fetchMovieDetail(id, compositeDisposable, this);
        detailViewModel.getMovieDetail().observe(this, new Observer<MovieDB>() {
            @Override
            public void onChanged(MovieDB movieDB) {
                binding.setMovie(movieDB);
            }
        });
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
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
//        Intent intent;
//        switch (view.getId()) {
//            case R.id.iv_imdb:
//                String URL = "https://www.imdb.com/title/" + movie.getImdbId();
//                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
//                startActivity(intent);
//                break;
//            case R.id.iv_link:
//                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(movie.getHomepage()));
//                startActivity(intent);
//                break;
//        }
    }

    @Override
    public void onSuccess(List<MovieDB> movieDBList) {

    }

    @Override
    public void onSuccess(String message) {

    }

    @Override
    public void onError(String errorMessage) {

        //Todo: Show Alert
    }
}
