package com.rajatsangrame.movie.data.model.movie;

import com.google.gson.annotations.SerializedName;

public class SpokenLanguages {

	@SerializedName("name")
	private String name;

	@SerializedName("iso_639_1")
	private String iso6391;

	public String getName(){
		return name;
	}

	public String getIso6391(){
		return iso6391;
	}
}