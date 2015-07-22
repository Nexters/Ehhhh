package com.teamnexters.ehhhh.fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.teamnexters.ehhhh.R;

/**
 * Created by HyeonSi on 2015-07-22.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private static final String TAG = "MainRecyclerAdapter";
    static Context mContext;

    private HomeFragment.ItemData[] mDataSet;

    public HomeAdapter(HomeFragment.ItemData[] itemDatas) {
        this.mDataSet = itemDatas;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView imageView;
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imageView = (ImageView) itemView.findViewById(R.id.pub_photo);
            textView = (TextView) itemView.findViewById(R.id.pub_title);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(mContext, ((TextView) v.findViewById(R.id.pub_title)).getText(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false);
        mContext = parent.getContext();

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d(TAG, "Element " + position + " set.");

        try {
            holder.imageView.setImageResource(mDataSet[position].getImageUrl());
            holder.textView.setText(mDataSet[position].getTitle());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.length;
    }
}