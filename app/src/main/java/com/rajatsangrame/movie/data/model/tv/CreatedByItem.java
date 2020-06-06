package com.rajatsangrame.movie.data.model.tv;

import com.google.gson.annotations.SerializedName;

public class CreatedByItem{

	@SerializedName("gender")
	private Integer gender;

	@SerializedName("credit_id")
	private String creditId;

	@SerializedName("name")
	private String name;

	@SerializedName("profile_path")
	private Object profilePath;

	@SerializedName("id")
	private Integer id;

	public Integer getGender(){
		return gender;
	}

	public String getCreditId(){
		return creditId;
	}

	public String getName(){
		return name;
	}

	public Object getProfilePath(){
		return profilePath;
	}

	public Integer getId(){
		return id;
	}
}