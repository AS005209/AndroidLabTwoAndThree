package com.cjaizer.androidlab2;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

interface API {

    @GET("movie/popular")
    Call<MoviePageModel> getPopularMovies(@Query("page") int page, @Query("api_key") String userKey);

    @GET("movie/{movie_id}")
    Call<MovieDetails> getMovieDetails(@Path("movie_id") int id, @Query("api_key") String userKey);

}