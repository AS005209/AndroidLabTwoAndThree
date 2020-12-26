package com.cjaizer.androidlab2;


import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

class NetworkHelper {

    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String API_KEY = "9bd3b90bce7cf27fc87c740e0442a798";
    public static final String IMG_BASE_URL = "http://image.tmdb.org/t/p/w342/";
    public static final String IMG_BIG_SIZE_URL = "http://image.tmdb.org/t/p/w780/";

    public interface MovieListLoadCallback {
        void onLoadFail(Call call);
        void onLoadSuccess(Response response, List<MovieData> movieModels);
    }

    public interface MovieDetailsLoadCallback {
        void onLoadFail(Call call);
        void onLoadSuccess(Response response, MovieDetails movieDetails);
    }
}
