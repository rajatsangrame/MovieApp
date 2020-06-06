package com.rajatsangrame.movie.data.model.tv;

import com.google.gson.annotations.SerializedName;

public class NetworksItem{

	@SerializedName("logo_path")
	private String logoPath;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private Integer id;

	@SerializedName("origin_country")
	private String originCountry;

	public String getLogoPath(){
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