package com.rajatsangrame.movie.paging;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PagedList;

import com.rajatsangrame.movie.data.Repository;
import com.rajatsangrame.movie.data.db.MovieDB;
import com.rajatsangrame.movie.data.db.TVDB;
import com.rajatsangrame.movie.data.model.ApiResponse;
import com.rajatsangrame.movie.data.model.movie.Movie;
import com.rajatsangrame.movie.data.model.tv.TV;
import com.rajatsangrame.movie.data.rest.Category;
import com.rajatsangrame.movie.data.rest.RetrofitApi;
import com.rajatsangrame.movie.util.Utils;

import java.util.List;

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
public class BoundaryCallBack<T> extends PagedList.BoundaryCallback<T> {

    private static final String TAG = "BoundaryCallBack";
    private final Category category;
    private final RetrofitApi retrofitApi;
    private final Repository repository;
    private int lastRequestedPage = 1;
    boolean isRequestInProgress;
    private CompositeDisposable compositeDisposable;

    public BoundaryCallBack(RetrofitApi retrofitApi, Repository repository,
                            CompositeDisposable disposable, Category category) {
        this.retrofitApi = retrofitApi;
        this.repository = repository;
        this.compositeDisposable = disposable;
        this.category = category;
    }

    @Override
    public void onZeroItemsLoaded() {
        if (category == Category.POPULAR_TV || category == Category.TOP_TV) {
            requestTVData();
            return;
        }
        requestMovieData();
    }

    @Override
    public void onItemAtFrontLoaded(@NonNull T itemAtFront) {
        requestData(itemAtFront);
    }

    @Override
    public void onItemAtEndLoaded(@NonNull T itemAtEnd) {
        requestData(itemAtEnd);
    }

    void requestData(T object) {
        if (object instanceof MovieDB) {
            requestMovieData();
        } else {
            requestTVData();
        }
    }

    private void requestMovieData() {

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
        Single<ApiResponse<Movie>> single = Utils.getSingleMovie(retrofitApi, category, lastRequestedPage);
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
                        repository.insertBulkMovie(movieDBList, new Repository.InsertCallback() {
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


    private void requestTVData() {

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
        Single<ApiResponse<TV>> single2 = Utils.getSingleTV(retrofitApi, category, lastRequestedPage);
        compositeDisposable.add(single2.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<ApiResponse<TV>, List<TVDB>>() {
                    @Override
                    public List<TVDB> apply(ApiResponse<TV> movieApi) throws Exception {
                        return Utils.getTVList(movieApi, category);
                    }
                }).subscribe(new Consumer<List<TVDB>>() {
                    @Override
                    public void accept(List<TVDB> tvdbList) throws Exception {
                        repository.insertBulkTV(tvdbList, new Repository.InsertCallback() {
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
