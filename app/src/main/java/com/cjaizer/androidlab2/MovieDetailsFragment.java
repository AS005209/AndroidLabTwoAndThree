package com.cjaizer.androidlab2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Response;

public class MovieDetailsFragment extends Fragment {

    private TextView title;
    private TextView tag;
    private TextView description;
    private TextView budget;
    private TextView revenue;
    private TextView release;
    private ImageView poster;
    private Repository repository = new Repository();
    private int id;

    public MovieDetailsFragment() {

    }

    public static MovieDetailsFragment newInstance(int param1) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putInt("id", param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt("id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_movie_details, container, false);
        init(root);
        repository.getMovieDetails(id, new NetworkHelper.MovieDetailsLoadCallback() {
            @Override
            public void onLoadFail(Call call) {
                Toast.makeText(getContext(),"Error",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLoadSuccess(Response response, MovieDetails movieDetails) {
                setDetails(movieDetails);
            }
        });
        return root;
    }

    private void init(View view) {
        title = view.findViewById(R.id.movie_details_title);
        tag = view.findViewById(R.id.movie_details_tag);
        description = view.findViewById(R.id.movie_details_description);
        poster = view.findViewById(R.id.movie_details_poster);
        budget = view.findViewById(R.id.movie_details_budget);
        revenue = view.findViewById(R.id.movie_details_revenue);
        release = view.findViewById(R.id.movie_details_release);
    }

    public void setDetails(final MovieDetails details) {
        title.setVisibility(View.VISIBLE);
        title.setText(details.getTitle());
        tag.setVisibility(View.VISIBLE);
        tag.setText(details.getTagline());
        description.setVisibility(View.VISIBLE);
        description.setText(details.getOverview());
        poster.setVisibility(View.VISIBLE);
        budget.setText("Budget - " + details.getBudget() + " $");
        revenue.setText("Revenue - " + details.getRevenue() + " $");
        release.setText("Release date: " + details.getReleaseDate());

        Glide.with(this)
                .load(NetworkHelper.IMG_BIG_SIZE_URL.concat(details.getPosterPath()))
                .into(poster);
    }
}