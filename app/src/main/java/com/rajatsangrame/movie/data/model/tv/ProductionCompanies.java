package com.rajatsangrame.movie.data.model.tv;

import com.google.gson.annotations.SerializedName;

public class ProductionCompanies {

	@SerializedName("logo_path")
	private Object logoPath;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private Integer id;

	@SerializedName("origin_country")
	private String originCountry;

	public Object getLogoPath(){
		return logoPath;
	}

	public String getName(){
		return name;
	}

	public Integer getId(){
		return id;
	}

	public String getOriginCountry(){
		return originCountry;
	}
}