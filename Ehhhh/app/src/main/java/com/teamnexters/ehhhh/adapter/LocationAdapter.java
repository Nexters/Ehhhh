package com.teamnexters.ehhhh.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.teamnexters.ehhhh.R;
import com.teamnexters.ehhhh.fragment.LocationFragment;
import com.teamnexters.ehhhh.activity.PubActivity;

/**
 * Created by HyeonSi on 2015-07-22.
 */
public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {

    private static final String TAG = "LocationAdapter";
    static Context mContext;

    private LocationFragment.ItemData[] itemsData;

    public LocationAdapter(LocationFragment.ItemData[] itemsData) {
        this.itemsData = itemsData;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public LinearLayout imageView;
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imageView = (LinearLayout) itemView.findViewById(R.id.layout_location);
            textView = (TextView) itemView.findViewById(R.id.item_title);
        }

        @Override
        public void onClick(View v) {
            //Toast.makeText(mContext, textView.getText(), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(mContext, PubActivity.class);
            intent.putExtra("location", textView.getText().toString());
            mContext.startActivity(intent);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location, parent, false);
        mContext = parent.getContext();

        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d(TAG, "Element " + position + " set.");

        try {
            holder.imageView.setBackground(mContext.getResources().getDrawable(itemsData[position].getImageUrl()));
            holder.textView.setText(itemsData[position].getTitle());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return itemsData.length;
    }
}
