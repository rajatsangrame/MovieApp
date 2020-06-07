package com.rajatsangrame.movie.data.db;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rajatsangrame.movie.data.model.movie.Genre;
import com.rajatsangrame.movie.data.model.movie.ProductionCompanies;
import com.rajatsangrame.movie.data.model.movie.SpokenLanguages;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Rajat Sangrame on 4/6/20.
 * http://github.com/rajatsangrame
 */
public class DbTypeConverter implements Serializable {

    @TypeConverter
    public List<Genre> genreList(String jsonString) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Genre>>() {
        }.getType();
        return gson.fromJson(jsonString, type);
    }

    @TypeConverter
    public String genreString(List<Genre> genreList) {
        Gson gson = new Gson();
        return gson.toJson(genreList);
    }

    @TypeConverter
    public List<ProductionCompanies> productionCompaniesList(String jsonString) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<ProductionCompanies>>() {
        }.getType();
        return gson.fromJson(jsonString, type);
    }

    @TypeConverter
    public String productionCompaniesString(List<ProductionCompanies> genreList) {
        Gson gson = new Gson();
        return gson.toJson(genreList);
    }

    @TypeConverter
    public List<SpokenLanguages> spokenLanguagesList(String jsonString) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<SpokenLanguages>>() {
        }.getType();
        return gson.fromJson(jsonString, type);
    }

    @TypeConverter
    public String spokenLanguagesString(List<SpokenLanguages> genreList) {
        Gson gson = new Gson();
        return gson.toJson(genreList);
    }

}

