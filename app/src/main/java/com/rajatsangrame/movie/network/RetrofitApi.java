package com.rajatsangrame.movie.network;

import com.rajatsangrame.movie.model.Api;
import com.rajatsangrame.movie.model.Movie;
import com.rajatsangrame.movie.model.MovieDetail;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Rajat Sangrame on 26/11/19.
 * http://github.com/rajatsangrame
 */
public interface RetrofitApi {

    @GET("/3/movie/popular")
    Single<Api<Movie>> getPopularMovies(@Query("page") long page);

    @GET("/3/tv/popular")
    Single<Api<Movie>> getPopularTv(@Query("page") long page);

    @GET("/3/movie/now_playing")
    Single<Api<Movie>> getNowPlaying(@Query("page") long page);

    @GET("/3/movie/upcoming")
    Single<Api<Movie>> getUpcoming(@Query("page") long page);

    @GET("/3/tv/top_rated")
    Single<Api<Movie>> getTopRatedTv(@Query("page") long page);

    @GET("/3/movie/{id}")
    Single<MovieDetail> getMovieDetails(@Path("id") int id);

    @GET("/3/tv/{tv_id}")
    Single<MovieDetail> getTvDetails(@Path("id") int id);
}