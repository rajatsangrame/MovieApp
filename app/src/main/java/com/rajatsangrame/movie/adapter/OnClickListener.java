package com.rajatsangrame.movie.adapter;

import android.view.View;

import com.rajatsangrame.movie.data.db.MovieDB;
import com.rajatsangrame.movie.data.db.TVDB;
import com.rajatsangrame.movie.data.model.search.SearchResult;

/**
 * Created by Rajat Sangrame on 6/6/20.
 * http://github.com/rajatsangrame
 */
public interface OnClickListener {

    void onItemClicked(MovieDB movie, View view);

    void onItemClicked(TVDB movie, View view);

    void onItemClicked(SearchResult movie, View view);

}
