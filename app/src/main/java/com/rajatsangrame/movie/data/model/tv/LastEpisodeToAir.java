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
	private Integer episodeNumber;

	@SerializedName("show_id")
	private Integer showId;

	@SerializedName("vote_average")
	private Integer voteAverage;

	@SerializedName("name")
	private String name;

	@SerializedName("season_number")
	private Integer seasonNumber;

	@SerializedName("id")
	private Integer id;

	@SerializedName("still_path")
	private String stillPath;

	@SerializedName("vote_count")
	private Integer voteCount;

	public String getProductionCode(){
		return productionCode;
	}

	public String getAirDate(){
		return airDate;
	}

	public String getOverview(){
		return overview;
	}

	public Integer getEpisodeNumber(){
		return episodeNumber;
	}

	public Integer getShowId(){
		return showId;
	}

	public Integer getVoteAverage(){
		return voteAverage;
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

	public String getStillPath(){
		return stillPath;
	}

	public Integer getVoteCount(){
		return voteCount;
	}
}