package com.cjaizer.androidlab2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<MovieData> mData;
    private Context mContext;
    private Fragment fragment;


    public RecyclerViewAdapter(Fragment fragment) {
        this.fragment = fragment;
        mData = new ArrayList<>();
    }

    void addData(List<MovieData> data) {
        mData.addAll(data);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView titleTextView;
        TextView descriptionTextView;
        ImageView imageView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.item_title);
            imageView = itemView.findViewById(R.id.item_photo);
            descriptionTextView = itemView.findViewById(R.id.item_description);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            FragmentTransaction transaction =  fragment.getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.host, MovieDetailsFragment.newInstance(mData.get(this.getAdapterPosition()).getId()));
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item, parent, false));
        mContext = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        MovieData item = mData.get(position);
        holder.titleTextView.setText(item.getTitle());
        holder.descriptionTextView.setText(item.getOverview());
        Glide.with(mContext)
                .load(NetworkHelper.IMG_BASE_URL + item.getPosterPath())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

}