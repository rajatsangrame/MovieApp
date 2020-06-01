package com.rajatsangrame.movie.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.rajatsangrame.movie.ui.detail.DetailActivity;
import com.rajatsangrame.movie.paging.MovieAdapter;
import com.rajatsangrame.movie.ui.home.HomeFragment;
import com.rajatsangrame.movie.R;
import com.rajatsangrame.movie.databinding.ActivityMainBinding;
import com.rajatsangrame.movie.data.model.Movie;


public class MainActivity extends AppCompatActivity implements MovieAdapter.MoviesAdapterListener {

    private ActivityMainBinding mBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        addHomeFragment();

    }

    private void addHomeFragment() {
        if (getSupportFragmentManager().findFragmentByTag(HomeFragment.TAG) == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container_fragment, HomeFragment.newInstance(), HomeFragment.TAG)
                    .commit();
        }
    }

    void loadData() {

//        if (!Helper.isNetworkAvailable(this)) {
//            mBinding.mainLayout.layoutProgress.progressBar.setVisibility(View.GONE);
//            showAlert();
//            return;
//        }
//
//        final MovieAdapterNew adapter = new MovieAdapterNew(this);
//        final MovieAdapterNew adapter2 = new MovieAdapterNew(this);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
//        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
//        layoutManager2.setOrientation(RecyclerView.HORIZONTAL);
//        mBinding.mainLayout.recyclerView.setLayoutManager(layoutManager);
//        mBinding.mainLayout.recyclerView2.setLayoutManager(layoutManager2);
//        MovieViewModel itemViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
//
//        itemViewModel.userPagedList.observe(this, new Observer<PagedList<Movie>>() {
//            @Override
//            public void onChanged(PagedList<Movie> users) {
//                adapter.submitList(users);
//
//            }
//        });
//        itemViewModel.userPagedList2.observe(this, new Observer<PagedList<Movie>>() {
//            @Override
//            public void onChanged(PagedList<Movie> users) {
//                adapter2.submitList(users);
//
//            }
//        });
//        mBinding.mainLayout.recyclerView.setAdapter(adapter);
//        mBinding.mainLayout.recyclerView2.setAdapter(adapter2);
//        mBinding.mainLayout.layoutProgress.progressBar.setVisibility(View.GONE);

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

