package com.rajatsangrame.movie.data;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.rajatsangrame.movie.data.db.MovieDB;
import com.rajatsangrame.movie.data.db.MovieDatabase;
import com.rajatsangrame.movie.data.db.TVDB;
import com.rajatsangrame.movie.data.model.ApiResponse;
import com.rajatsangrame.movie.data.model.movie.MovieDetail;
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
    private MutableLiveData<MovieDB> liveDataMovieDetail;

    public Repository(RetrofitApi retrofitApi, MovieDatabase database) {
        this.retrofitApi = retrofitApi;
        this.database = database;
        this.ioExecutor = Executors.newSingleThreadExecutor();
        liveDataSearchResult = new MutableLiveData<>();
        liveDataMovieDetail = new MutableLiveData<>();
    }

    public MutableLiveData<List<SearchResult>> getSearchLiveData() {
        return liveDataSearchResult;
    }

    public MutableLiveData<MovieDB> getLiveDataMovieDetail() {
        return liveDataMovieDetail;
    }

    public MovieDatabase getDatabase() {
        return database;
    }

    public void insertBulkMovie(List<MovieDB> movieList, final InsertCallback insertCallback) {
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

    public void insertBulkTV(List<TVDB> movieList, final InsertCallback insertCallback) {
        ioExecutor.execute(new Runnable() {
            @Override
            public void run() {
                database.tvDao().bulkInsert(movieList);
                if (insertCallback != null) {
                    insertCallback.insertFinished();
                }
            }
        });
    }

    public void addDetail(MovieDB movieDB, final InsertCallback insertCallback) {
        ioExecutor.execute(new Runnable() {
            @Override
            public void run() {
                MovieDB db = database.movieDao().getMovieFromId(movieDB.getId());
                if (db == null) {

                } else {

                }
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

    public void fetchMovieDetail(int id, CompositeDisposable disposable, final ApiCallback callback) {

        Single<MovieDetail> single = retrofitApi.getMovieDetails(id);
        disposable.add(single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<MovieDetail, MovieDB>() {
                    @Override
                    public MovieDB apply(MovieDetail movieDetail) throws Exception {
                        return Utils.getMovieDetail(movieDetail, Repository.this);
                    }
                })
                .subscribe(new Consumer<MovieDB>() {
                    @Override
                    public void accept(MovieDB movieDB) throws Exception {
                        addDetail(movieDB, null);
                        liveDataMovieDetail.setValue(movieDB);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (callback != null) {
                            callback.onError(throwable.getMessage());
                        }
                    }
                }));
    }

    public MovieDB getMovieDB(MovieDetail movieDetail) {
        int id = movieDetail.getId();
        MovieDB movieDB = new MovieDB(id);
        movieDB.setPosterPath(movieDetail.getPosterPath());
        movieDB.setBackdropPath(movieDetail.getBackdropPath());
        movieDB.setTitle(movieDetail.getTitle());
        movieDB.setOriginalTitle(movieDetail.getOriginalTitle());
        movieDB.setPopularity(movieDetail.getPopularity());
        movieDB.setVoteAverage(movieDetail.getVoteAverage());
        movieDB.setOverview(movieDetail.getOverview());
        return movieDB;
    }

    public interface InsertCallback {
        void insertFinished();
    }
}
