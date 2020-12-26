package com.cjaizer.androidlab2;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class Repository {

    API api = new Retrofit.Builder()
            .baseUrl(NetworkHelper.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
            .build().create(API.class);
    ;

    public void getPopularMovies(int page, NetworkHelper.MovieListLoadCallback callback) {
        Log.d("TAGGG", "Get popular method");
        new PopularMoviesTask(page, callback).execute();
    }

    private class PopularMoviesTask extends AsyncTask<Void, Void, Void> {

        private int mPage;
        private NetworkHelper.MovieListLoadCallback mCallback;

        PopularMoviesTask(int page, NetworkHelper.MovieListLoadCallback callback) {
            mPage = page;
            mCallback = callback;
            Log.d("TAGGG", "Starting asynctask");
        }

        @Override
        protected Void doInBackground(Void... voids) {
            api.getPopularMovies(mPage, NetworkHelper.API_KEY).enqueue(new Callback<MoviePageModel>() {
                @Override
                public void onResponse(@NonNull Call<MoviePageModel> call, @NonNull Response<MoviePageModel> response) {
                    mCallback.onLoadSuccess(response, response.body().getMoviesList());
                }

                @Override
                public void onFailure(@NonNull Call<MoviePageModel> call, @NonNull Throwable t) {
                    mCallback.onLoadFail(call);
                }
            });
            return null;
        }
    }

    public void getMovieDetails(int id, NetworkHelper.MovieDetailsLoadCallback callback) {
        new MovieDetailsTask(id, callback).execute();
    }

    private class MovieDetailsTask extends AsyncTask<Void, Void, Void> {

        private int mId;
        private NetworkHelper.MovieDetailsLoadCallback mCallback;

        MovieDetailsTask(int id, NetworkHelper.MovieDetailsLoadCallback callback) {
            mId = id;
            mCallback = callback;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            api.getMovieDetails(mId, NetworkHelper.API_KEY).enqueue(new Callback<MovieDetails>() {
                @Override
                public void onResponse(@NonNull Call<MovieDetails> call, @NonNull Response<MovieDetails> response) {
                    mCallback.onLoadSuccess(response, response.body());
                }

                @Override
                public void onFailure(@NonNull Call<MovieDetails> call, @NonNull Throwable t) {
                    mCallback.onLoadFail(call);
                }
            });
            return null;
        }
    }
}