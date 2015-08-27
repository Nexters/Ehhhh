package com.teamnexters.ehhhh.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.teamnexters.ehhhh.R;
import com.teamnexters.ehhhh.activity.PubDetailActivity;
import com.teamnexters.ehhhh.activity.PubSubMapActivity;
import com.teamnexters.ehhhh.common.PubInfo;

import java.util.ArrayList;

/**
 * Created by csk on 2015-07-23.
 */
public class PubAdapter extends RecyclerView.Adapter<PubAdapter.ViewHolder> {

    private static final String TAG = "PubAdapter";
    static Context mContext;

    private ArrayList<PubInfo> mDataSet;

    public PubAdapter(ArrayList<PubInfo> mDataSet) {
        this.mDataSet = mDataSet;
    }

    public String getAdapterAddress(int pos) {
        return mDataSet.get(pos).getAdress();
    }

    public String getAdapterPubName(int pos) {
        return mDataSet.get(pos).getName();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textPubName;
        public TextView textAddress;
        public TextView textPhone;
       // public ImageView mMapBtn;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            itemView.setClickable(true);
            init();
        }

        private void init() {
            textPubName = (TextView) itemView.findViewById(R.id.item_pub_name);
            textAddress = (TextView) itemView.findViewById(R.id.item_address);
            textPhone = (TextView) itemView.findViewById(R.id.item_phone);
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

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pub, parent, false);
        mContext = parent.getContext();
        parent.setSelected(true);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d(TAG, "Element " + position + " set.");

        try {
            holder.textPubName.setText(mDataSet.get(position).getName());
            holder.textAddress.setText(mDataSet.get(position).getAdress());
            holder.textPhone.setText(mDataSet.get(position).getPhone());
            holder.textPubName.setTag(mDataSet.get(position));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

}