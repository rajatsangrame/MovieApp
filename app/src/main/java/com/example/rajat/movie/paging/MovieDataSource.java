package com.example.rajat.movie.paging;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.example.rajat.movie.model.Api;
import com.example.rajat.movie.model.Movie;
import com.example.rajat.movie.network.RetrofitApi;
import com.example.rajat.movie.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDataSource extends PageKeyedDataSource<Long, Movie> {

    public static int PAGE_SIZE = 355;
    public static long FIRST_PAGE = 1;


    @Override
    public void loadInitial(@NonNull final LoadInitialParams<Long> params,
                            @NonNull final LoadInitialCallback<Long, Movie> callback) {


        RetrofitApi retrofitApi = RetrofitClient.getInstance();
        Call<Api<Movie>> call = retrofitApi.getPopularMovies(FIRST_PAGE);

        call.enqueue(new Callback<Api<Movie>>() {
            @Override
            public void onResponse(Call<Api<Movie>> call, Response<Api<Movie>> response) {

                Api<Movie> apiResponse = response.body();
                if (apiResponse != null) {
                    List<Movie> responseItems = apiResponse.getMovies();
                    callback.onResult(responseItems, null, FIRST_PAGE + 1);
                }

            }

            @Override
            public void onFailure(Call<Api<Movie>> call, Throwable t) {

            }

        });

    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Long> params,
                           @NonNull final LoadCallback<Long, Movie> callback) {

        RetrofitApi retrofitApi = RetrofitClient.getInstance();
        Call<Api<Movie>> call = retrofitApi.getPopularMovies(params.key);
        call.enqueue(new Callback<Api<Movie>>() {
            @Override
            public void onResponse(Call<Api<Movie>> call, Response<Api<Movie>> response) {

                Api<Movie> apiResponse = response.body();
                if (apiResponse != null) {
                    List<Movie> responseItems = apiResponse.getMovies();
                    long key;
                    if (params.key > 1) {
                        key = params.key - 1;
                    } else {
                        key = 0;
                    }
                    callback.onResult(responseItems, key);
                }

            }

            @Override
            public void onFailure(Call<Api<Movie>> call, Throwable t) {

            }
        });
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Long> params,
                          @NonNull final LoadCallback<Long, Movie> callback) {

        RetrofitApi retrofitApi = RetrofitClient.getInstance();
        Call<Api<Movie>> call = retrofitApi.getPopularMovies(params.key);

        call.enqueue(new Callback<Api<Movie>>() {
            @Override
            public void onResponse(Call<Api<Movie>> call, Response<Api<Movie>> response) {

                Api<Movie> apiResponse = response.body();
                if (apiResponse != null) {
                    List<Movie> responseItems = apiResponse.getMovies();
                    callback.onResult(responseItems, params.key + 1);
                }
            }

            @Override
            public void onFailure(Call<Api<Movie>> call, Throwable t) {

            }
        });
    }
}
