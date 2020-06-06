package com.rajatsangrame.movie.data.model.movie;

import com.google.gson.annotations.SerializedName;

public class Genre {

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	public String getName(){
		return name;
	}

	public int getId(){
		return id;
	}
}