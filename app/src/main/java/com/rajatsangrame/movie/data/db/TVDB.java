package com.rajatsangrame.movie.data.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Created by Rajat Sangrame on 22/4/20.
 * http://github.com/rajatsangrame
 * <p>
 * Ref: https://android.jlelse.eu/android-architecture-components-room-relationships-bf473510c14a
 */

@Entity(tableName = "tv")
public class TVDB {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "first_air_date")
    private String firstAirDate;

    @ColumnInfo(name = "overview")
    private String overview;

    @ColumnInfo(name = "original_language")
    private String originalLanguage;

    @ColumnInfo(name = "poster_path")
    private String posterPath;

    @ColumnInfo(name = "backdrop_path")
    private String backdropPath;

    @ColumnInfo(name = "original_name")
    private String originalName;

    @ColumnInfo(name = "popularity")
    private double popularity;

    @ColumnInfo(name = "vote_average")
    private double voteAverage;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "vote_count")
    private int voteCount;

    @ColumnInfo(name = "fetch_category")
    private String fetchCategory;

    @ColumnInfo(name = "entry_timestamp")
    private long entryTimeStamp;

    @ColumnInfo(name = "saved")
    private boolean saved;

    public TVDB(int id) {
        this.id = id;
    }

    @Ignore
    public TVDB(int id,
                   String name,
                   String fetchCategory,
                   String posterPath,
                   String backdropPath,
                   String overview,
                   double popularity,
                   double voteAverage,
                   long entryTimeStamp) {

        this.id = id;
        this.name = name;
        this.fetchCategory = fetchCategory;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.overview = overview;
        this.popularity = popularity;
        this.voteAverage = voteAverage;
        this.entryTimeStamp = entryTimeStamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public String getFetchCategory() {
        return fetchCategory;
    }

    public void setFetchCategory(String fetchCategory) {
        this.fetchCategory = fetchCategory;
    }

    public long getEntryTimeStamp() {
        return entryTimeStamp;
    }

    public void setEntryTimeStamp(long entryTimeStamp) {
        this.entryTimeStamp = entryTimeStamp;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    /*
    @ColumnInfo(name = "origin_country")
    private List<String> originCountry;

    @ColumnInfo(name = "genre_ids")
    private List<int> genreIds;
     */
}
