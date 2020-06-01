package com.rajatsangrame.movie.di.module;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;


import com.rajatsangrame.movie.data.model.Api;
import com.rajatsangrame.movie.data.model.Movie;
import com.rajatsangrame.movie.di.qualifier.ApplicationContext;
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

public class RestaurantRepository {

    private static final String TAG = "RestaurantRepository";
    RetrofitApi retrofitApi;
    MutableLiveData<List<Movie>> moviesLiveData;

    public RestaurantRepository(@ApplicationContext Context context, RetrofitApi retrofitApi) {
        this.retrofitApi = retrofitApi;
        moviesLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<Movie>> getSearchLiveData(String query) {

        Single<Api<Movie>> single = retrofitApi.search(query);
        single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Api<Movie>, List<Movie>>() {
                    @Override
                    public List<Movie> apply(Api<Movie> movieApi) throws Exception {
                        return Utils.getListResult(movieApi);
                    }
                })
                .subscribe(new Consumer<List<Movie>>() {
                    @Override
                    public void accept(List<Movie> movies) throws Exception {
                        moviesLiveData.postValue(movies);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "accept: ");
                    }
                });
        return moviesLiveData;
    }
}
