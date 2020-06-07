package com.rajatsangrame.movie.data;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rajatsangrame.movie.data.db.movie.MovieDB;
import com.rajatsangrame.movie.data.db.MovieDatabase;
import com.rajatsangrame.movie.data.db.tv.TVDB;
import com.rajatsangrame.movie.data.model.ApiResponse;
import com.rajatsangrame.movie.data.model.movie.MovieDetail;
import com.rajatsangrame.movie.data.model.search.SearchResult;
import com.rajatsangrame.movie.data.model.tv.TvDetail;
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

    public void insertBulkTV(List<TVDB> tvdbList, final InsertCallback insertCallback) {
        ioExecutor.execute(new Runnable() {
            @Override
            public void run() {
                database.tvDao().bulkInsert(tvdbList);
                if (insertCallback != null) {
                    insertCallback.insertFinished();
                }
            }
        });
    }

    public void insertTV(TVDB tvdb, final InsertCallback insertCallback) {
        ioExecutor.execute(new Runnable() {
            @Override
            public void run() {
                database.tvDao().insert(tvdb);
                if (insertCallback != null) {
                    insertCallback.insertFinished();
                }
            }
        });
    }

    public void insertMovie(MovieDB movieDB, final InsertCallback insertCallback) {
        ioExecutor.execute(new Runnable() {
            @Override
            public void run() {
                database.movieDao().insert(movieDB);
                if (insertCallback != null) {
                    insertCallback.insertFinished();
                }
            }
        });
    }

    //region DetailActivity
    public LiveData<MovieDB> getLiveDataMovieDetail(int id) {
        return database.movieDao().getLiveMovieFromId(id);
    }

    public LiveData<TVDB> getLiveDataTVDetail(int id) {
        return database.tvDao().getLiveMovieFromId(id);
    }

    public void addMovieDetail(MovieDB movieDB, final InsertCallback insertCallback) {
        ioExecutor.execute(new Runnable() {
            @Override
            public void run() {
                MovieDB oldItem = database.movieDao().getMovieFromId(movieDB.getId());
                if (oldItem == null) {
                    /*Case when called form Search Fragment*/
                    database.movieDao().insert(movieDB);
                } else {
                    MovieDB newItem = Utils.updateMovieDB(oldItem, movieDB);
                    database.movieDao().update(newItem);
                }
                if (insertCallback != null) {
                    insertCallback.insertFinished();
                }
            }
        });
    }

    public void addTVDetail(TVDB tvdb, final InsertCallback insertCallback) {
        ioExecutor.execute(new Runnable() {
            @Override
            public void run() {
                TVDB oldItem = database.tvDao().getTVFromId(tvdb.getId());
                if (oldItem == null) {
                    /*Case when called form Search Fragment*/
                    database.tvDao().insert(tvdb);
                } else {
                    TVDB newItem = Utils.updateTVDB(oldItem, tvdb);
                    database.tvDao().update(newItem);
                }
                if (insertCallback != null) {
                    insertCallback.insertFinished();
                }
            }
        });
    }

    public void fetchMovieDetail(int id, CompositeDisposable disposable, final ApiCallback callback) {

        Single<MovieDetail> single = retrofitApi.getMovieDetails(id);
        disposable.add(single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<MovieDetail, MovieDB>() {
                    @Override
                    public MovieDB apply(MovieDetail movieDetail) throws Exception {
                        return Utils.getMovieDetail(movieDetail);
                    }
                })
                .subscribe(new Consumer<MovieDB>() {
                    @Override
                    public void accept(MovieDB movieDB) throws Exception {
                        addMovieDetail(movieDB, null);
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

    public void fetchTVDetail(int id, CompositeDisposable disposable, final ApiCallback callback) {

        Single<TvDetail> single = retrofitApi.getTvDetails(id);
        disposable.add(single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<TvDetail, TVDB>() {
                    @Override
                    public TVDB apply(TvDetail tvDetail) throws Exception {
                        return Utils.getTVDetail(tvDetail);
                    }
                })
                .subscribe(new Consumer<TVDB>() {
                    @Override
                    public void accept(TVDB tvdb) throws Exception {
                        addTVDetail(tvdb, null);
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
    //endregion

    //region SearchFragment
    public MutableLiveData<List<SearchResult>> getSearchLiveData() {
        return liveDataSearchResult;
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
                        liveDataSearchResult.postValue(movies);
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
    //endregion

    //region SavedFragment
    public LiveData<List<MovieDB>> getLiveDataSavedMovie() {
        return database.movieDao().getAllSaved();
    }

    public LiveData<List<TVDB>> getLiveDataSavedTV() {
        return database.tvDao().getAllSaved();
    }
    //endregion

    public interface InsertCallback {
        void insertFinished();
    }
}
