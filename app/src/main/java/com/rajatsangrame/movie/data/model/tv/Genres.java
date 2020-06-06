package com.rajatsangrame.movie.data.model.tv;

import com.google.gson.annotations.SerializedName;

public class Genres {

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private Integer id;

	public String getName(){
		return name;
	}

	public Integer getId(){
		return id;
	}
}