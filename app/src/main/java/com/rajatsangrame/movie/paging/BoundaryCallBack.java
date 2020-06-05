package com.rajatsangrame.movie.paging;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PagedList;

import com.rajatsangrame.movie.data.db.MovieDB;
import com.rajatsangrame.movie.data.db.MovieDao;
import com.rajatsangrame.movie.data.model.ApiResponse;
import com.rajatsangrame.movie.data.model.home.Movie;
import com.rajatsangrame.movie.data.rest.Category;
import com.rajatsangrame.movie.data.rest.RetrofitApi;
import com.rajatsangrame.movie.util.Utils;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
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
    private final Executor ioExecutor;
    private final MovieDao movieDao;
    private int lastRequestedPage = 1;
    boolean isRequestInProgress;

    public BoundaryCallBack(RetrofitApi retrofitApi, MovieDao movieDao, Category category) {
        this.retrofitApi = retrofitApi;
        this.category = category;
        this.movieDao = movieDao;
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

        int pageLimit = 5;
        if (lastRequestedPage > pageLimit) {
            Log.d(TAG, "requestAndSaveData: page limit");
            return;
        }

        if (isRequestInProgress) {
            Log.d(TAG, "requestAndSaveData: isRequestInProgress");
            return;
        }

        isRequestInProgress = true;
        Single<ApiResponse<Movie>> single = Utils.getSingle(retrofitApi, category, lastRequestedPage);
        single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<ApiResponse<Movie>, List<MovieDB>>() {
                    @Override
                    public List<MovieDB> apply(ApiResponse<Movie> movieApi) throws Exception {
                        return Utils.getMovieList(movieApi, category);
                    }
                }).subscribe(new SingleObserver<List<MovieDB>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(List<MovieDB> movieList) {
                ioExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        movieDao.bulkInsert(movieList);
                        lastRequestedPage++;
                        isRequestInProgress = false;
                    }
                });
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }
}
