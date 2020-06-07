package com.rajatsangrame.movie.data.db.movie;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.rajatsangrame.movie.data.db.DbTypeConverter;
import com.rajatsangrame.movie.data.model.movie.Genre;
import com.rajatsangrame.movie.data.model.movie.ProductionCompanies;
import com.rajatsangrame.movie.data.model.movie.SpokenLanguages;

import java.util.List;


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

    @ColumnInfo(name = "popularity")
    private double popularity;

    @ColumnInfo(name = "vote_average")
    private double voteAverage;

    @ColumnInfo(name = "entry_timestamp")
    private long entryTimeStamp;

    @ColumnInfo(name = "saved")
    private boolean saved;

    public MovieDB(int id) {
        this.id = id;
    }

    @Ignore
    public MovieDB(int id,
                   String title,
                   String fetchCategory,
                   String posterPath,
                   String backdropPath,
                   String overview,
                   double popularity,
                   double voteAverage,
                   long entryTimeStamp) {

        this.id = id;
        this.title = title;
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

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    /* Fetched in Detail API */

    @ColumnInfo(name = "original_language")
    private String originalLanguage;

    @ColumnInfo(name = "original_title")
    private String originalTitle;

    @ColumnInfo(name = "release_date")
    private String releaseDate;

    @ColumnInfo(name = "adult")
    private boolean adult;

    @ColumnInfo(name = "imdb_id")
    private String imdbId;

    @ColumnInfo(name = "homepage")
    private String homepage;

    @TypeConverters(DbTypeConverter.class)
    @ColumnInfo(name = "spoken_languages")
    private List<SpokenLanguages> spokenLanguages;

    @TypeConverters(DbTypeConverter.class)
    @ColumnInfo(name = "production_companies")
    private List<ProductionCompanies> productionCompanies;

    @TypeConverters(DbTypeConverter.class)
    @ColumnInfo(name = "genres")
    private List<Genre> genres;

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

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<ProductionCompanies> getProductionCompanies() {
        return productionCompanies;
    }

    public void setProductionCompanies(List<ProductionCompanies> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public List<SpokenLanguages> getSpokenLanguages() {
        return spokenLanguages;
    }

    public void setSpokenLanguages(List<SpokenLanguages> spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }
}
