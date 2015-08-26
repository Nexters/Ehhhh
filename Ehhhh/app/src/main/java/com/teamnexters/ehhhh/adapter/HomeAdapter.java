package com.teamnexters.ehhhh.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.teamnexters.ehhhh.R;
import com.teamnexters.ehhhh.activity.PubDetailActivity;
import com.teamnexters.ehhhh.common.Recommend;
import com.teamnexters.ehhhh.fragment.HomeFragment;

import java.util.ArrayList;

/**
 * Created by HyeonSi on 2015-07-22.
 * Edit by 슬기 2015-08-25.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private static final String TAG = "MainRecyclerAdapter";
    static Context mContext;

    private ArrayList<Recommend> mDataSet;

    public HomeAdapter(ArrayList<Recommend> mDataSet) {
        this.mDataSet = mDataSet;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final LinearLayout item_back;
        public TextView title, location, subject;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            item_back = (LinearLayout) itemView.findViewById(R.id.item_back);
            title = (TextView) itemView.findViewById(R.id.pub_title);
            location = (TextView) itemView.findViewById(R.id.pub_location);
            subject = (TextView) itemView.findViewById(R.id.pub_subject);
        }

        @Override
        public void onClick(View v) {
            // Edit by csk 2015-08-11 : 펍 상세화면 추가
            // Edit by 슬기 2015-08-26 : 파라미터 수정
            Intent intent = new Intent(mContext, PubDetailActivity.class);
            Recommend recommend = (Recommend) subject.getTag();
            intent.putExtra("pubId", recommend.getPubId());
            intent.putExtra("name", recommend.pub.getName());
            intent.putExtra("nameEng", recommend.pub.getNameEng());
            intent.putExtra("phone", recommend.pub.getPhone());
            intent.putExtra("district", recommend.pub.getDistrict());//구,구분
            intent.putExtra("adress", recommend.pub.getAdress());
            intent.putExtra("time", recommend.pub.getTime());//영업시간
            intent.putExtra("info1", recommend.pub.getInfo1());
            intent.putExtra("info2", recommend.pub.getInfo2());
            intent.putExtra("etc", recommend.pub.getEtc());
            intent.putExtra("bookmark", recommend.pub.getBookmarkYN());

            mContext.startActivity(intent);
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
            //holder.item_back.setBackground(mContext.getResources().getDrawable(mDataSet[position].getImageUrl()));
            holder.item_back.setBackground(mContext.getResources().getDrawable(R.drawable.back_main_beer));
            holder.title.setText(mDataSet.get(position).pub.getName());
            holder.location.setText(mDataSet.get(position).pub.getDistrict());
            holder.subject.setText(mDataSet.get(position).getSubject());
            holder.subject.setTag(mDataSet.get(position));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}