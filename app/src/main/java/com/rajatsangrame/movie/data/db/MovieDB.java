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

@Entity(tableName = "movie")
public class MovieDB {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "fetch_category")
    private String fetchCategory;

    @ColumnInfo(name = "poster_path")
    private String posterPath;

    @ColumnInfo(name = "backdrop_path")
    private String backdropPath;

    @ColumnInfo(name = "overview")
    private String overview;

    @ColumnInfo(name = "original_language")
    private String originalLanguage;

    @ColumnInfo(name = "original_title")
    private String originalTitle;

    @ColumnInfo(name = "popularity")
    private double popularity;

    @ColumnInfo(name = "vote_average")
    private double voteAverage;

    @ColumnInfo(name = "entry_timestamp")
    private long entryTimeStamp;

    @ColumnInfo(name = "is_saved")
    private long isSaved;

    @Ignore
    public MovieDB(int id) {
        this.id = id;
    }

    public MovieDB(int id,
                   String title,
                   String fetchCategory,
                   String posterPath,
                   double popularity,
                   double voteAverage,
                   long entryTimeStamp) {

        this.id = id;
        this.title = title;
        this.fetchCategory = fetchCategory;
        this.posterPath = posterPath;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFetchCategory() {
        return fetchCategory;
    }

    public void setFetchCategory(String fetchCategory) {
        this.fetchCategory = fetchCategory;
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

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
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

    public long getEntryTimeStamp() {
        return entryTimeStamp;
    }

    public void setEntryTimeStamp(long entryTimeStamp) {
        this.entryTimeStamp = entryTimeStamp;
    }

    public long getIsSaved() {
        return isSaved;
    }

    public void setIsSaved(long isSaved) {
        this.isSaved = isSaved;
    }

    /*
    @ColumnInfo(name = "release_date")
    private String releaseDate;

    @ColumnInfo(name = "adult")
    private boolean adult;

    @ColumnInfo(name = "vote_count")
    private int voteCount;

    @ColumnInfo(name = "media_type")
    private String mediaType;

    @ColumnInfo(name = "imdb_id")
    private String imdbId;

    @ColumnInfo(name = "revenue")
    private int revenue;

    @ColumnInfo(name = "budget")
    private int budget;

    @ColumnInfo(name = "runtime")
    private int runtime;

    @ColumnInfo(name = "tag_line")
    private String tagLine;

    @ColumnInfo(name = "homepage")
    private String homepage;

    @ColumnInfo(name = "status")
    private String status;

-----------------------------------------------------

    @ColumnInfo(name = "spoken_languages")
    //private List<SpokenLanguages> spokenLanguages;
    private String spokenLanguages;

    @ColumnInfo(name = "production_companies")
    //private List<ProductionCompanies> productionCompanies;
    private String productionCompanies;

    @ColumnInfo(name = "genres")
    //private List<Genre> genres;
    private String genres;

    @ColumnInfo(name = "production_countries")
    //private List<ProductionCountries> productionCountries;
    private String productionCountries;
    */
}
