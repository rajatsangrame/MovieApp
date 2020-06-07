package com.rajatsangrame.movie.ui.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.rajatsangrame.movie.App;
import com.rajatsangrame.movie.R;
import com.rajatsangrame.movie.adapter.ProductionCompaniesAdapter;
import com.rajatsangrame.movie.data.db.movie.MovieDB;
import com.rajatsangrame.movie.data.db.tv.TVDB;
import com.rajatsangrame.movie.data.rest.ApiCallback;
import com.rajatsangrame.movie.databinding.ActivityDetailBinding;
import com.rajatsangrame.movie.data.model.movie.Genre;
import com.rajatsangrame.movie.data.model.movie.ProductionCompanies;
import com.rajatsangrame.movie.data.model.movie.SpokenLanguages;
import com.rajatsangrame.movie.di.component.DaggerDetailActivityComponent;
import com.rajatsangrame.movie.di.component.DetailActivityComponent;
import com.rajatsangrame.movie.util.ViewModelFactory;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
        String type = bundle.getString("type");
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
                public void onChanged(MovieDB movieDB) {
                    binding.setMovie(movieDB);
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
                        //fetchData(id);
                    }
                });
        builder.show();
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

    }
}
