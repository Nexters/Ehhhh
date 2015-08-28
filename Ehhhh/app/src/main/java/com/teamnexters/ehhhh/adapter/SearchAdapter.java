package com.teamnexters.ehhhh.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teamnexters.ehhhh.R;
import com.teamnexters.ehhhh.activity.PubDetailActivity;
import com.teamnexters.ehhhh.common.PubInfo;

import java.util.ArrayList;

/**
 * Created by csk on 2015-08-29.
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private static final String TAG = "SearchAdapter";
    static Context mContext;
    boolean isAllview;

    private ArrayList<PubInfo> mDataSet;

    public SearchAdapter(ArrayList<PubInfo> mDataSet) {
        this.mDataSet = mDataSet;
        this.isAllview = false;
    }

    public void setViewType(boolean isAll) {
        this.isAllview = isAll;
    }

    public void refreshView() {
        this.notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textPubName;
        public TextView textAddress;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textPubName = (TextView) itemView.findViewById(R.id.title_txt);
            textAddress = (TextView) itemView.findViewById(R.id.location_txt);
        }

        @Override
        public void onClick(View v) {
            // Edit by csk 2015-08-11 : 펍 상세화면 추가
            // Edit by 슬기 2015-08-25 : 파라미터 수정
            Intent intent = new Intent(mContext, PubDetailActivity.class);
            PubInfo pub = (PubInfo) textPubName.getTag();
            intent.putExtra("pubId", pub.getObjectId());
            intent.putExtra("name", pub.getName());
            intent.putExtra("nameEng", pub.getNameEng());
            intent.putExtra("phone", pub.getPhone());
            intent.putExtra("district", pub.getDistrict());//구,구분
            intent.putExtra("adress", pub.getAdress());
            intent.putExtra("time", pub.getTime());//영업시간
            intent.putExtra("info1", pub.getInfo1());
            intent.putExtra("info2", pub.getInfo2());
            intent.putExtra("etc", pub.getEtc());
            intent.putExtra("bookmark", pub.getBookmarkYN());
            mContext.startActivity(intent);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        mContext = parent.getContext();

        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d(TAG, "Element " + position + " set.");

        try {
            holder.textPubName.setText(mDataSet.get(position).getName());
            holder.textAddress.setText(mDataSet.get(position).getAdress());
            holder.textPubName.setTag(mDataSet.get(position));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        int totalCnt = mDataSet.size();
        if (totalCnt < 5) {
            return mDataSet.size();
        }

        if (isAllview) {
            return mDataSet.size();
        } else {
            return 5;
        }
    }
}