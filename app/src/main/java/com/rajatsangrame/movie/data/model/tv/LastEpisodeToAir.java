package com.rajatsangrame.movie.data.model.tv;

import com.google.gson.annotations.SerializedName;

public class LastEpisodeToAir{

	@SerializedName("production_code")
	private String productionCode;

	@SerializedName("air_date")
	private String airDate;

	@SerializedName("overview")
	private String overview;

	@SerializedName("episode_number")
	private int episodeNumber;

	@SerializedName("show_id")
	private int showId;

	@SerializedName("vote_average")
	private double voteAverage;

	@SerializedName("name")
	private String name;

	@SerializedName("season_number")
	private int seasonNumber;

	@SerializedName("id")
	private int id;

	@SerializedName("still_path")
	private String stillPath;

	@SerializedName("vote_count")
	private int voteCount;

	public String getProductionCode(){
		return productionCode;
	}

	public String getAirDate(){
		return airDate;
	}

	public String getOverview(){
		return overview;
	}

	public int getEpisodeNumber(){
		return episodeNumber;
	}

	public int getShowId(){
		return showId;
	}

	public double getVoteAverage(){
		return voteAverage;
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

	public String getStillPath(){
		return stillPath;
	}

	public int getVoteCount(){
		return voteCount;
	}
}