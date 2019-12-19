package com.example.rajat.movie.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;

import com.example.rajat.movie.Helper;
import com.example.rajat.movie.R;
import com.example.rajat.movie.databinding.ActivityMainBinding;
import com.example.rajat.movie.model.Movie;
import com.example.rajat.movie.paging.MovieAdapter;
import com.example.rajat.movie.paging.MovieViewModel;


public class MainActivity extends AppCompatActivity implements MovieAdapter.MoviesAdapterListener {

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_main);
        mBinding.layoutProgress.progressBar.setVisibility(View.VISIBLE);

        loadData();

    }

    void loadData() {

        if (!Helper.isNetworkAvailable(this)) {
            mBinding.layoutProgress.progressBar.setVisibility(View.GONE);
            showAlert();
            return;
        }

        final MovieAdapter adapter = new MovieAdapter(this);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MovieViewModel itemViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        itemViewModel.userPagedList.observe(this, new Observer<PagedList<Movie>>() {
            @Override
            public void onChanged(PagedList<Movie> users) {

                adapter.submitList(users);

            }
        });
        mBinding.recyclerView.setAdapter(adapter);
        mBinding.layoutProgress.progressBar.setVisibility(View.GONE);

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

                        loadData();
                    }
                });
        builder.show();
    }

    @Override
    public void onMovieItemClicked(Movie movie, View view) {

        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("id", movie.getId());
        intent.putExtra("title", movie.getTitle());
        startActivity(intent);
    }

}

