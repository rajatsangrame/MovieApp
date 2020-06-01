package com.rajatsangrame.movie.di.module;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;


import com.rajatsangrame.movie.di.qualifier.ApplicationContext;
import com.rajatsangrame.movie.data.rest.RetrofitApi;

import java.util.List;


/**
 * Created by Rajat Sangrame on 23/5/20.
 * http://github.com/rajatsangrame
 */

public class RestaurantRepository {

    RetrofitApi retrofitApi;
    MutableLiveData<List<Object>> restaurantsItems;

    public RestaurantRepository(@ApplicationContext Context context, RetrofitApi retrofitApi) {
        this.retrofitApi = retrofitApi;
        restaurantsItems = new MutableLiveData<>();
        fetchData();
    }

    public MutableLiveData<List<Object>> getRestaurantList() {
        return restaurantsItems;
    }

    private void fetchData() {
    }
}
