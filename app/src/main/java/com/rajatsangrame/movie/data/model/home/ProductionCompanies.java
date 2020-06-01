package com.rajatsangrame.movie.data.model.home;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class ProductionCompanies {

    @SerializedName("logo_path")
    private String logoPath;

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private int id;

    @SerializedName("origin_country")
    private String originCountry;

    public String getLogoPath() {
        return logoPath;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        ProductionCompanies item = (ProductionCompanies) obj;
        return item.getId() == this.getId();
    }
}