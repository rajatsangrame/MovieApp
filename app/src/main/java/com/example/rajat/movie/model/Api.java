package com.example.rajat.movie.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Rajat Sangrame on 18/12/19.
 * http://github.com/rajatsangrame
 */
public class Api<T> {

    @SerializedName("page")
    private int page;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("results")
    private List<T> movies;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<T> getMovies() {
        return movies;
    }

    public void setMovies(List<T> movies) {
        this.movies = movies;
    }
}
