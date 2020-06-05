package com.rajatsangrame.movie.data.rest;

import com.rajatsangrame.movie.data.model.ApiResponse;
import com.rajatsangrame.movie.data.model.home.Movie;
import com.rajatsangrame.movie.data.model.home.MovieDetail;
import com.rajatsangrame.movie.data.model.search.SearchResult;

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
    Single<ApiResponse<Movie>> getPopularMovies(@Query("page") long page);

    @GET("/3/tv/popular")
    Single<ApiResponse<Movie>> getPopularTv(@Query("page") long page);

    @GET("/3/movie/now_playing")
    Single<ApiResponse<Movie>> getNowPlaying(@Query("page") long page);

    @GET("/3/movie/top_rated")
    Single<ApiResponse<Movie>> getTopRatedMovie(@Query("page") long page);

    @GET("/3/tv/top_rated")
    Single<ApiResponse<Movie>> getTopRatedTv(@Query("page") long page);

    @GET("/3/movie/{id}")
    Single<MovieDetail> getMovieDetails(@Path("id") int id);

    @GET("/3/movie/{id}/similar")
    Single<ApiResponse<Movie>> getSimilarMovies(@Path("id") int id);

    @GET("/3/tv/{tv_id}")
    Single<MovieDetail> getTvDetails(@Path("id") int id);

    @GET("/3/tv/{tv_id}")
    Single<ApiResponse<Movie>> getSimilarTv(@Path("id") int id);

    @GET("/3/search/multi")
    Single<ApiResponse<SearchResult>> search(@Query("query") String query);

}