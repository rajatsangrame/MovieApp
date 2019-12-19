package com.example.rajat.movie.network;

import com.example.rajat.movie.model.Api;
import com.example.rajat.movie.model.Movie;
import com.example.rajat.movie.model.MovieDetail;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Rajat Sangrame on 26/11/19.
 * http://github.com/rajatsangrame
 */
public interface RetrofitApi {

    @GET("movie/popular")
    Call<Api<Movie>> getPopularMovies();

    @GET("movie/popular")
    Call<Api<Movie>> getPopularMovies(@Query("page") long page);

    @GET("movie/{id}")
    Call<MovieDetail> getMovieDetails(@Path("id") int id);

    /*
    @GET("movie/top_rated")
    Call<Api<Movie>> getTopRatedMovies();

    @GET("movie/{id}/reviews")
    Call<Reviews> getMovieReviews(@Path(value = "id", encoded = true) String id);

    @GET("movie/{id}/videos")
    Call<Trailer> getMovieTrailers(@Path(value = "id", encoded = true) String id);
    */
}