package com.rajatsangrame.movie.data.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
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
    private Integer id;

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
    private Double popularity;

    @ColumnInfo(name = "vote_average")
    private Double voteAverage;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "vote_count")
    private Integer voteCount;

    @ColumnInfo(name = "fetch_category")
    private String fetchCategory;

    @ColumnInfo(name = "entry_timestamp")
    private long entryTimeStamp;

    @ColumnInfo(name = "saved")
    private boolean saved;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
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
    private List<Integer> genreIds;
     */
}
