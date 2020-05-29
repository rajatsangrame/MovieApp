package com.rajatsangrame.movie.network;

import com.rajatsangrame.movie.model.Api;
import com.rajatsangrame.movie.model.Movie;
import com.rajatsangrame.movie.model.MovieDetail;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Rajat Sangrame on 26/11/19.
 * http://github.com/rajatsangrame
 */
public interface RetrofitApi {

    @GET("/3/movie/popular")
    Call<Api<Movie>> getPopularMovies(@Query("page") long page);

    @GET("/3/movie/upcoming")
    Call<Api<Movie>> getUpcoming(@Query("page") long page);

    @GET("/3/movie/now_playing")
    Call<Api<Movie>> getNowPlaying(@Query("page") long page);

    @GET("/3/tv/popular")
    Call<Api<Movie>> getPopularTv(@Query("page") long page);

    @GET("/3/tv/top_rated")
    Call<Api<Movie>> getTopRatedTv(@Query("page") long page);

    @GET("/3/movie/{id}")
    Call<MovieDetail> getMovieDetails(@Path("id") int id);

    @GET("/3/tv/{tv_id}")
    Call<MovieDetail> getTvDetails(@Path("id") int id);
}