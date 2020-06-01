package com.rajatsangrame.movie.data.model.home;

import com.google.gson.annotations.SerializedName;

public class ProductionCountries {

	@SerializedName("iso_3166_1")
	private String iso31661;

	@SerializedName("name")
	private String name;

	public String getIso31661(){
		return iso31661;
	}

	public String getName(){
		return name;
	}
}