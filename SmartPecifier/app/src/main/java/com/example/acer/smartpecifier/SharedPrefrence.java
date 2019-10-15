package com.example.acer.smartpecifier;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SharedPrefrence {

    public static final String PREFS_NAME = "PRODUCT_APP";
    public static final String FAVORITES = "Product_Favorite";

    public SharedPrefrence() {
        super();
    }

    // This four methods are used for maintaining favorites.
    public void saveFavorites(Context context, List<Pecifier> favorites) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);

        editor.putString(FAVORITES, jsonFavorites);

        editor.commit();
    }

    public void addFavorite(Context context, Pecifier pecifier) {
        List<Pecifier> favorites = getFavorites(context);
        if (favorites == null)
            favorites = new ArrayList<Pecifier>();
        favorites.add(pecifier);
        saveFavorites(context, favorites);
    }

    public void removeFavorite(Context context, Pecifier pecifier) {
        ArrayList<Pecifier> favorites = getFavorites(context);
        if (favorites != null) {
            favorites.remove(pecifier);
            saveFavorites(context, favorites);
        }
    }

    public ArrayList<Pecifier> getFavorites(Context context) {
        SharedPreferences settings;
        List<Pecifier> favorites;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            Pecifier[] favoriteItems = gson.fromJson(jsonFavorites,
                    Pecifier[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<Pecifier>(favorites);
        } else
            return null;

        return (ArrayList<Pecifier>) favorites;
    }








}
