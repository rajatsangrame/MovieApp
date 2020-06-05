package com.rajatsangrame.movie.data;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.rajatsangrame.movie.data.db.MovieDB;
import com.rajatsangrame.movie.data.db.MovieDatabase;
import com.rajatsangrame.movie.data.model.ApiResponse;
import com.rajatsangrame.movie.data.model.search.SearchResult;
import com.rajatsangrame.movie.data.rest.ApiCallback;
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
 * Created by Rajat Sangrame on 23/5/20.
 * http://github.com/rajatsangrame
 */

public class Repository {

    private static final String TAG = "Repository";
    private final RetrofitApi retrofitApi;
    private final MovieDatabase database;
    private final Executor ioExecutor;
    private MutableLiveData<List<SearchResult>> liveDataSearchResult;

    public Repository(RetrofitApi retrofitApi, MovieDatabase database) {
        this.retrofitApi = retrofitApi;
        this.database = database;
        this.ioExecutor = Executors.newSingleThreadExecutor();
        liveDataSearchResult = new MutableLiveData<>();
    }

    public MutableLiveData<List<SearchResult>> getSearchLiveData() {
        return liveDataSearchResult;
    }

    public MovieDatabase getDatabase() {
        return database;
    }

    public void insertBulk(List<MovieDB> movieList, final InsertCallback insertCallback) {
        ioExecutor.execute(new Runnable() {
            @Override
            public void run() {
                database.movieDao().bulkInsert(movieList);
                if (insertCallback != null) {
                    insertCallback.insertFinished();
                }
            }
        });
    }

    public void fetchQuery(String query, CompositeDisposable disposable, final ApiCallback callback) {

        Single<ApiResponse<SearchResult>> single = retrofitApi.search(query);
        disposable.add(single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<ApiResponse<SearchResult>, List<SearchResult>>() {
                    @Override
                    public List<SearchResult> apply(ApiResponse<SearchResult> movieApi) throws Exception {
                        return Utils.prepareListForSearchAdapter(movieApi);
                    }
                })
                .subscribe(new Consumer<List<SearchResult>>() {
                    @Override
                    public void accept(List<SearchResult> movies) throws Exception {
                        liveDataSearchResult.setValue(movies);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (callback != null) {
                            callback.onError(throwable.getMessage());
                        }
                        Log.i(TAG, "accept: ");
                    }
                }));
    }

    public interface InsertCallback {
        void insertFinished();
    }
}
