package com.cjaizer.androidlab2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class MainFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private Repository repository;
    private RecyclerViewScrollListener endlessScrollListener;

    public MainFragment() {
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_main, container, false);
        init(root);

        endlessScrollListener = new RecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                repository.getPopularMovies(page, new NetworkHelper.MovieListLoadCallback() {
                    @Override
                    public void onLoadFail(Call call) {
                        Log.d("TAGGG", String.valueOf(call));
                    }

                    @Override
                    public void onLoadSuccess(Response response, List<MovieData> movieModels) {
                        Log.d("TAGGG", String.valueOf(response.code()));
                        Log.d("TAGGG", String.valueOf(movieModels));
                        adapter.addData(movieModels);
                    }
                });
            }

        };
        recyclerView.addOnScrollListener(endlessScrollListener);

        return root;
    }

    void init(View view) {
        recyclerView = view.findViewById(R.id.movies_recycler_view);

        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerViewAdapter(this);
        recyclerView.setAdapter(adapter);

        repository = new Repository();
        repository.getPopularMovies(1, new NetworkHelper.MovieListLoadCallback() {
            @Override
            public void onLoadFail(Call call) {
                Log.d("TAGGG", String.valueOf(call));
            }

            @Override
            public void onLoadSuccess(Response response, List<MovieData> movieModels) {
                Log.d("TAGGG", String.valueOf(response.code()));
                Log.d("TAGGG", String.valueOf(movieModels));
                adapter.addData(movieModels);
            }
        });
    }
}