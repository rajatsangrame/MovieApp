package com.rajatsangrame.movie.data.model.tv;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class TV {

	@SerializedName("first_air_date")
	private String firstAirDate;

	@SerializedName("overview")
	private String overview;

	@SerializedName("original_language")
	private String originalLanguage;

	@SerializedName("genre_ids")
	private List<Integer> genreIds;

	@SerializedName("poster_path")
	private String posterPath;

	@SerializedName("origin_country")
	private List<String> originCountry;

	@SerializedName("backdrop_path")
	private String backdropPath;

	@SerializedName("original_name")
	private String originalName;

	@SerializedName("popularity")
	private Double popularity;

	@SerializedName("vote_average")
	private Double voteAverage;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private Integer id;

	@SerializedName("vote_count")
	private Integer voteCount;

	public String getFirstAirDate(){
		return firstAirDate;
	}

	public String getOverview(){
		return overview;
	}

	public String getOriginalLanguage(){
		return originalLanguage;
	}

	public List<Integer> getGenreIds(){
		return genreIds;
	}

	public String getPosterPath(){
		return posterPath;
	}

	public List<String> getOriginCountry(){
		return originCountry;
	}

	public String getBackdropPath(){
		return backdropPath;
	}

	public String getOriginalName(){
		return originalName;
	}

	public Double getPopularity(){
		return popularity;
	}

	public Double getVoteAverage(){
		return voteAverage;
	}

	public String getName(){
		return name;
	}

	public Integer getId(){
		return id;
	}

	public Integer getVoteCount(){
		return voteCount;
	}
}