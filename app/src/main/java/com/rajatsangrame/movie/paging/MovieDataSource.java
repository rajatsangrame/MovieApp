package com.rajatsangrame.movie.paging;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.rajatsangrame.movie.data.model.ApiResponse;
import com.rajatsangrame.movie.data.model.movie.Movie;
import com.rajatsangrame.movie.data.rest.RetrofitApi;
import com.rajatsangrame.movie.data.rest.Category;
import com.rajatsangrame.movie.util.Utils;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @deprecated This class was used in online network paging. Not used in network = db
 * checkout branch `without-db` for the use case
 */
@Deprecated
public class MovieDataSource extends PageKeyedDataSource<Long, Movie> {

    private static final String TAG = "MovieDataSource";
    public final static int PAGE_SIZE = 10;
    public final static long FIRST_PAGE = 1;
    private Category category;
    private RetrofitApi retrofitApi;
    private CompositeDisposable compositeDisposable;


    public MovieDataSource(Category category, RetrofitApi retrofitApi) {
        this.category = category;
        this.retrofitApi = retrofitApi;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void loadInitial(@NonNull final LoadInitialParams<Long> params,
                            @NonNull final LoadInitialCallback<Long, Movie> callback) {

        Single<ApiResponse<Movie>> single = Utils.getSingleMovie(retrofitApi, category, FIRST_PAGE);
        compositeDisposable.add(single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<ApiResponse<Movie>, List<Movie>>() {
                    @Override
                    public List<Movie> apply(ApiResponse<Movie> movieApi) throws Exception {
                        return Utils.getListResult(movieApi);
                    }
                })
                .subscribe(new Consumer<List<Movie>>() {
                    @Override
                    public void accept(List<Movie> movies) throws Exception {
                        callback.onResult(movies, null, FIRST_PAGE + 1);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }));
    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Long> params,
                           @NonNull final LoadCallback<Long, Movie> callback) {
        Single<ApiResponse<Movie>> single = Utils.getSingleMovie(retrofitApi, category, params.key);
        compositeDisposable.add(single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<ApiResponse<Movie>, List<Movie>>() {
                    @Override
                    public List<Movie> apply(ApiResponse<Movie> movieApi) throws Exception {
                        return Utils.getListResult(movieApi);
                    }
                })
                .subscribe(new Consumer<List<Movie>>() {
                    @Override
                    public void accept(List<Movie> movies) throws Exception {
                        long key;
                        if (params.key > 1) {
                            key = params.key - 1;
                        } else {
                            key = 0;
                        }
                        callback.onResult(movies, key);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }));
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Long> params,
                          @NonNull final LoadCallback<Long, Movie> callback) {

        Single<ApiResponse<Movie>> single = Utils.getSingleMovie(retrofitApi, category, params.key);
        compositeDisposable.add(single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<ApiResponse<Movie>, List<Movie>>() {
                    @Override
                    public List<Movie> apply(ApiResponse<Movie> movieApi) throws Exception {
                        return Utils.getListResult(movieApi);
                    }
                })
                .subscribe(new Consumer<List<Movie>>() {
                    @Override
                    public void accept(List<Movie> movies) throws Exception {
                        callback.onResult(movies, params.key + 1);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }));
    }

    public void clear() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }
}
