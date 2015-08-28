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
import com.teamnexters.ehhhh.activity.PubActivity;
import com.teamnexters.ehhhh.common.PubInfo;

import java.util.ArrayList;

/**
 * Created by HyeonSi on 2015-07-22.
 * Edit by 슬기 2015-08-25.
 */
public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {

    private static final String TAG = "LocationAdapter";
    static Context mContext;

    private ArrayList<PubInfo> itemsData;

    public LocationAdapter(ArrayList<PubInfo> itemsData) {
        this.itemsData = itemsData;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public LinearLayout imageView;
        public TextView textView;
        public TextView pubCount;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imageView = (LinearLayout) itemView.findViewById(R.id.layout_location);
            textView = (TextView) itemView.findViewById(R.id.item_title);
            pubCount = (TextView) itemView.findViewById(R.id.item_subtitle);
        }

        @Override
        public void onClick(View v) {
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
            //holder.imageView.setBackground(mContext.getResources().getDrawable(itemsData.get(position).getImageUrl()));
            int imgUrl = R.drawable.back_gangnam;

            String district = itemsData.get(position).getDistrict();

            if (district.equals("동대문구")) {
                imgUrl = R.drawable.back_ddm;
            } else if (district.equals("강남구")) {
                imgUrl = R.drawable.back_gangnam;
            } else if (district.equals("구로구")) {
                imgUrl = R.drawable.back_guro;
            } else if (district.equals("관악구")) {
                imgUrl = R.drawable.back_gwanak;
            } else if (district.equals("종로구")) {
                imgUrl = R.drawable.back_jongro;
            } else if (district.equals("노원구")) {
                imgUrl = R.drawable.back_nowon;
            } else if (district.equals("마포구")) {
                imgUrl = R.drawable.back_mapo;
            } else if (district.equals("서대문구")) {
                imgUrl = R.drawable.back_sdm;
            } else if (district.equals("서초구")) {
                imgUrl = R.drawable.back_sucho;
            } else if (district.equals("성동구")) {
                imgUrl = R.drawable.back_sungdong;
            } else if (district.equals("영등포구")) {
                imgUrl = R.drawable.back_youndeungpo;
            } else if (district.equals("용산구")) {
                imgUrl = R.drawable.back_youngsan;
            }

            holder.imageView.setBackground(mContext.getResources().getDrawable(imgUrl));
            holder.textView.setText(district);
            holder.pubCount.setText(itemsData.get(position).getCount() + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return itemsData.size();
    }
}