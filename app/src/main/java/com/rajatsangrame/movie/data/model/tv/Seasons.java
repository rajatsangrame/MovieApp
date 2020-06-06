package com.rajatsangrame.movie.data.model.tv;

import com.google.gson.annotations.SerializedName;

public class Seasons {

	@SerializedName("air_date")
	private Object airDate;

	@SerializedName("overview")
	private String overview;

	@SerializedName("episode_count")
	private Integer episodeCount;

	@SerializedName("name")
	private String name;

	@SerializedName("season_number")
	private Integer seasonNumber;

	@SerializedName("id")
	private Integer id;

	@SerializedName("poster_path")
	private Object posterPath;

	public Object getAirDate(){
		return airDate;
	}

	public String getOverview(){
		return overview;
	}

	public Integer getEpisodeCount(){
		return episodeCount;
	}

	public String getName(){
		return name;
	}

	public Integer getSeasonNumber(){
		return seasonNumber;
	}

	public Integer getId(){
		return id;
	}

	public Object getPosterPath(){
		return posterPath;
	}
}