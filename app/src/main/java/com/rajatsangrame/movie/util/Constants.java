package com.rajatsangrame.movie.util;


import java.util.Hashtable;

public class Constants {

    public static final int NOW_PLAYING = 2;
    public static final int HEADER = 11;
    public static final int ITEM = 12;

    public final static String IMAGE_URL = "https://image.tmdb.org/t/p/w185";
    private static Hashtable<Integer, String> hashtable;

    public static String getGenreFromId(int id) {

        if (hashtable == null) {
            hashtable = new Hashtable<>();
            generateHashTable();
        }
        return hashtable.get(id);
    }

    private static void generateHashTable() {

        hashtable.put(28, "Action");
        hashtable.put(12, "Adventure");
        hashtable.put(16, "Animation");
        hashtable.put(35, "Comedy");
        hashtable.put(80, "Crime");
        hashtable.put(99, "Documentary");
        hashtable.put(18, "Drama");
        hashtable.put(10751, "Family");
        hashtable.put(14, "Fantasy");
        hashtable.put(36, "History");
        hashtable.put(27, "Horror");
        hashtable.put(10402, "Music");
        hashtable.put(9648, "Mystery");
        hashtable.put(10749, "Romance");
        hashtable.put(878, "Science Fiction");
        hashtable.put(10770, "TV Movie");
        hashtable.put(53, "Thriller");
        hashtable.put(10752, "War");
        hashtable.put(37, "Western");
    }

}