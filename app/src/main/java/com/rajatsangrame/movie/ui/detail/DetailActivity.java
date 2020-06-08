package com.rajatsangrame.movie.ui.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.rajatsangrame.movie.App;
import com.rajatsangrame.movie.R;
import com.rajatsangrame.movie.data.db.movie.MovieDB;
import com.rajatsangrame.movie.data.db.tv.TVDB;
import com.rajatsangrame.movie.data.rest.ApiCallback;
import com.rajatsangrame.movie.databinding.ActivityDetailBinding;
import com.rajatsangrame.movie.di.component.DaggerDetailActivityComponent;
import com.rajatsangrame.movie.di.component.DetailActivityComponent;
import com.rajatsangrame.movie.util.ViewModelFactory;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class DetailActivity extends AppCompatActivity implements ApiCallback {

    @Inject
    ViewModelFactory viewModelFactory;

    private DetailViewModel detailViewModel;
    private ActivityDetailBinding binding;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private int id;
    private String type;
    private MovieDB movieDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        getDependency();
        detailViewModel = new ViewModelProvider(this, viewModelFactory).get(DetailViewModel.class);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        id = bundle.getInt("id");
        type = bundle.getString("type");
        String title = bundle.getString("title");
        binding.toolbar.setTitle(title);

        fetchData(id, type);
    }

    private void getDependency() {

        DetailActivityComponent component = DaggerDetailActivityComponent
                .builder()
                .applicationComponent(App.get(this).getComponent())
                .build();
        component.injectDetailActivity(this);

    }

    private void fetchData(int id, String type) {

        if (type.equals("movie")) {
            detailViewModel.fetchMovieDetail(id, compositeDisposable, this);
            detailViewModel.getMovieDetail(id).observe(this, new Observer<MovieDB>() {
                @Override
                public void onChanged(MovieDB movie) {
                    binding.setMovie(movie);
                    movieDB = movie;
                }
            });
        } else {
            detailViewModel.fetchTVDetail(id, compositeDisposable, this);
            detailViewModel.getTvDetail(id).observe(this, new Observer<TVDB>() {
                @Override
                public void onChanged(TVDB tvdb) {
                    binding.setTv(tvdb);
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }

    public void openLink(View view) {

        Intent intent;
        switch (view.getId()) {
            case R.id.iv_imdb:
                String URL = "https://www.imdb.com/title/" + movieDB.getImdbId();
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
                startActivity(intent);
                break;
            case R.id.iv_link:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(movieDB.getHomepage()));
                startActivity(intent);
                break;
        }
    }


    @Override
    public void onSuccess(List<MovieDB> movieDBList) {

    }

    @Override
    public void onSuccess(String message) {

    }

    @Override
    public void onError(String errorMessage) {

    }

    public void onSaveButtonClicked(View view) {
        if (type.equals("movie")) {
            detailViewModel.updateMovieSave(id, null);
        } else {
            detailViewModel.updateTVSave(id, null);
        }
    }
}
