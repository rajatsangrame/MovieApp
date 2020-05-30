package com.rajatsangrame.movie.paging;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.rajatsangrame.movie.model.Api;
import com.rajatsangrame.movie.model.Movie;
import com.rajatsangrame.movie.network.RetrofitApi;
import com.rajatsangrame.movie.network.RetrofitClient;
import com.rajatsangrame.movie.util.Genre;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDataSource extends PageKeyedDataSource<Long, Movie> {

    public final static int PAGE_SIZE = 30;
    public final static long FIRST_PAGE = 1;
    private Genre genreType;

    public MovieDataSource(Genre genreType) {
        this.genreType = genreType;
    }

    @Override
    public void loadInitial(@NonNull final LoadInitialParams<Long> params,
                            @NonNull final LoadInitialCallback<Long, Movie> callback) {


        RetrofitApi retrofitApi = RetrofitClient.getInstance();
        Call<Api<Movie>> call;
        if (genreType == Genre.POPULAR) {
            call = retrofitApi.getPopularMovies(FIRST_PAGE);
        } else {
            call = retrofitApi.getUpcoming(FIRST_PAGE);
        }
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
        Call<Api<Movie>> call;
        if (genreType == Genre.POPULAR) {
            call = retrofitApi.getPopularMovies(params.key);
        } else {
            call = retrofitApi.getUpcoming(params.key);
        }
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
        Call<Api<Movie>> call;
        if (genreType == Genre.POPULAR) {
            call = retrofitApi.getPopularMovies(params.key);
        } else {
            call = retrofitApi.getUpcoming(params.key);
        }

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
