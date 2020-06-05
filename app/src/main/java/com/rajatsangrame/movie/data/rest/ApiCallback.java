package com.rajatsangrame.movie.data.rest;

import com.rajatsangrame.movie.data.model.home.Movie;

import java.util.List;

/**
 * Created by Rajat Sangrame on 5/6/20.
 * http://github.com/rajatsangrame
 */
public interface ApiCallback {

    void onSuccess(List<Movie> movieList);

    void onError(String errorMessage);
}
