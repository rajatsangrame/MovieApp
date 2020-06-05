package com.rajatsangrame.movie.di.module;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.rajatsangrame.movie.data.db.MovieDatabase;
import com.rajatsangrame.movie.data.model.Api;
import com.rajatsangrame.movie.data.model.search.SearchResult;
import com.rajatsangrame.movie.data.rest.RetrofitApi;
import com.rajatsangrame.movie.util.Utils;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Rajat Sangrame on 23/5/20.
 * http://github.com/rajatsangrame
 */

public class Repository {

    private static final String TAG = "RestaurantRepository";
    private RetrofitApi retrofitApi;
    private MutableLiveData<List<SearchResult>> liveDataSearchResult;
    private MovieDatabase database;

    public Repository(RetrofitApi retrofitApi, MovieDatabase database) {
        this.retrofitApi = retrofitApi;
        this.database = database;
        liveDataSearchResult = new MutableLiveData<>();
    }

    public MutableLiveData<List<SearchResult>> getSearchLiveData() {
        return liveDataSearchResult;
    }

    public MovieDatabase getDatabase() {
        return database;
    }

    public void fetchQuery(String query) {

        Single<Api<SearchResult>> single = retrofitApi.search(query);
        single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Api<SearchResult>, List<SearchResult>>() {
                    @Override
                    public List<SearchResult> apply(Api<SearchResult> movieApi) throws Exception {
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
                        Log.i(TAG, "accept: ");
                    }
                });
    }
}
