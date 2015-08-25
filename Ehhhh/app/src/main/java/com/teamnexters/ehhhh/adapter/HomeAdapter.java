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
import com.teamnexters.ehhhh.fragment.HomeFragment;

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
            Intent intent = new Intent(mContext, PubDetailActivity.class);
            intent.putExtra("pub_id", title.getText().toString());
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
            holder.item_back.setBackground(mContext.getResources().getDrawable(mDataSet[position].getImageUrl()));
            holder.title.setText(mDataSet[position].getTitle());
            holder.location.setText(mDataSet[position].getLocation());
            holder.subject.setText(mDataSet[position].getSubject());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.length;
    }
}