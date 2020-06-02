package com.rajatsangrame.movie.data.model.search;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class SearchResult {

    @SerializedName("overview")
    private String overview;

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("original_title")
    private String originalTitle;

    @SerializedName("video")
    private Boolean video;

    @SerializedName("title")
    private String title;

    @SerializedName("genre_ids")
    private List<Integer> genreIds;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("media_type")
    private String mediaType;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("popularity")
    private double popularity;

    @SerializedName("vote_average")
    private double voteAverage;

    @SerializedName("id")
    private int id;

    @SerializedName("adult")
    private Boolean adult;

    @SerializedName("vote_count")
    private int voteCount;

    public String getOverview() {
        return overview;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public boolean isVideo() {
        return video;
    }

    public String getTitle() {
        return title;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getMediaType() {
        return mediaType;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public double getPopularity() {
        return popularity;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public int getId() {
        return id;
    }

    public boolean isAdult() {
        return adult;
    }

    public int getVoteCount() {
        return voteCount;
    }

    // ***************** TV ******************

    @SerializedName("first_air_date")
    private String firstAirDate;

    @SerializedName("origin_country")
    private List<String> originCountry;

    @SerializedName("original_name")
    private String originalName;

    @SerializedName("name")
    private String name;

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public List<String> getOriginCountry() {
        return originCountry;
    }

    public String getOriginalName() {
        return originalName;
    }


    // ***************** Person ******************

    @SerializedName("gender")
    private int gender;

    @SerializedName("known_for_department")
    private String knownForDepartment;

    @SerializedName("known_for")
    private List<KnownForItem> knownFor;

    @SerializedName("profile_path")
    private Object profilePath;

    public int getGender() {
        return gender;
    }

    public String getKnownForDepartment() {
        return knownForDepartment;
    }

    public List<KnownForItem> getKnownFor() {
        return knownFor;
    }

    public String getName() {
        return name;
    }

    public Object getProfilePath() {
        return profilePath;
    }

    private int itemType;

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }
}