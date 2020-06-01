package com.rajatsangrame.movie.data.model;

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

    public int getTotalResults() {
        return totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<T> getResults() {
        return movies;
    }

}
