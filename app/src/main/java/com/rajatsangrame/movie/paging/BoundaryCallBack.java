package com.rajatsangrame.movie.paging;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PagedList;

import com.rajatsangrame.movie.data.Repository;
import com.rajatsangrame.movie.data.db.MovieDB;
import com.rajatsangrame.movie.data.model.ApiResponse;
import com.rajatsangrame.movie.data.model.home.Movie;
import com.rajatsangrame.movie.data.rest.Category;
import com.rajatsangrame.movie.data.rest.RetrofitApi;
import com.rajatsangrame.movie.util.Utils;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Rajat Sangrame on 5/6/20.
 * http://github.com/rajatsangrame
 */
public class BoundaryCallBack extends PagedList.BoundaryCallback<MovieDB> {

    private static final String TAG = "BoundaryCallBack";
    private final Category category;
    private final RetrofitApi retrofitApi;
    private final Repository repository;
    private int lastRequestedPage = 1;
    boolean isRequestInProgress;
    private CompositeDisposable compositeDisposable;
    private Executor ioExecutor;

    public BoundaryCallBack(RetrofitApi retrofitApi, Repository repository,
                            CompositeDisposable disposable, Category category) {
        this.retrofitApi = retrofitApi;
        this.repository = repository;
        this.compositeDisposable = disposable;
        this.category = category;
        this.ioExecutor = Executors.newSingleThreadExecutor();

    }

    @Override
    public void onZeroItemsLoaded() {
        requestAndSaveData();
    }

    @Override
    public void onItemAtFrontLoaded(@NonNull MovieDB itemAtFront) {
        requestAndSaveData();
    }

    @Override
    public void onItemAtEndLoaded(@NonNull MovieDB itemAtEnd) {
        requestAndSaveData();
    }

    private void requestAndSaveData() {

        Log.d(TAG, "requestAndSaveData: started");
        int pageLimit = 5;
        if (lastRequestedPage > pageLimit) {
            Log.i(TAG, "requestAndSaveData: page limit");
            return;
        }

        if (isRequestInProgress) {
            Log.i(TAG, "requestAndSaveData: isRequestInProgress");
            return;
        }

        isRequestInProgress = true;
        Single<ApiResponse<Movie>> single = Utils.getSingle(retrofitApi, category, lastRequestedPage);
        compositeDisposable.add(single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<ApiResponse<Movie>, List<MovieDB>>() {
                    @Override
                    public List<MovieDB> apply(ApiResponse<Movie> movieApi) throws Exception {
                        return Utils.getMovieList(movieApi, category);
                    }
                }).subscribe(new Consumer<List<MovieDB>>() {
                    @Override
                    public void accept(List<MovieDB> movieDBList) throws Exception {
                        repository.insertBulk(movieDBList, new Repository.InsertCallback() {
                            @Override
                            public void insertFinished() {
                                lastRequestedPage++;
                                isRequestInProgress = false;
                            }
                        });
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }));
    }
}
