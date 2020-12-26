package com.cjaizer.androidlab2;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class MoviePageModel implements Serializable {

    @SerializedName("page")
    private int page;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("results")
    private ArrayList<MovieData> movieModel;

    public MoviePageModel(int page, int totalResults, int totalPages, ArrayList<MovieData> movieModel) {
        this.page = page;
        this.totalResults = totalResults;
        this.totalPages = totalPages;
        this.movieModel = movieModel;
    }

    public ArrayList<MovieData> getMoviesList() {
        return movieModel;
    }

}