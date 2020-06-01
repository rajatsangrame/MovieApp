package com.rajatsangrame.movie.paging;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.rajatsangrame.movie.data.model.Api;
import com.rajatsangrame.movie.data.model.Movie;
import com.rajatsangrame.movie.data.rest.RetrofitApi;
import com.rajatsangrame.movie.util.Category;
import com.rajatsangrame.movie.util.Utils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


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

        Observable<Api<Movie>> apiObservable = retrofitApi.getLavda(FIRST_PAGE);
        Single<Api<Movie>> single = getSingle(retrofitApi, category, FIRST_PAGE);
        compositeDisposable.add(single.subscribeOn(Schedulers.io())
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
        Single<Api<Movie>> single = getSingle(retrofitApi, category, params.key);
        compositeDisposable.add(single.subscribeOn(Schedulers.io())
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

        Single<Api<Movie>> single = getSingle(retrofitApi, category, params.key);
        compositeDisposable.add(single.subscribeOn(Schedulers.io())
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
                        callback.onResult(movies, params.key + 1);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }));
    }

    private Single<Api<Movie>> getSingle(RetrofitApi retrofitApi, Category category, long key) {
        switch (category) {
            case POPULAR_TV:
                return retrofitApi.getPopularTv(key);
            case NOW_PLAYING:
                return retrofitApi.getNowPlaying(key);
            case UPCOMING:
                return retrofitApi.getUpcoming(key);
            case TOP_TV:
                return retrofitApi.getTopRatedTv(key);
            default:
                // POPULAR
                return retrofitApi.getPopularMovies(key);
        }
    }

    public void clear() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }
}
