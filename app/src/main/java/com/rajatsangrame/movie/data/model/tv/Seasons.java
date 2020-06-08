package com.rajatsangrame.movie.data.model.tv;

import com.google.gson.annotations.SerializedName;

public class Seasons {

	@SerializedName("air_date")
	private String airDate;

	@SerializedName("overview")
	private String overview;

	@SerializedName("episode_count")
	private int episodeCount;

	@SerializedName("name")
	private String name;

	@SerializedName("season_number")
	private int seasonNumber;

	@SerializedName("id")
	private int id;

	@SerializedName("poster_path")
	private String posterPath;

	public String getAirDate(){
		return airDate;
	}

	public String getOverview(){
		return overview;
	}

	public int getEpisodeCount(){
		return episodeCount;
	}

	public String getName(){
		return name;
	}

	public int getSeasonNumber(){
		return seasonNumber;
	}

	public int getId(){
		return id;
	}

	public String getPosterPath(){
		return posterPath;
	}
}