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
import com.teamnexters.ehhhh.common.ItemData;

/**
 * Created by csk on 2015-07-23.
 */
public class PubAdapter extends RecyclerView.Adapter<PubAdapter.ViewHolder> {

    private static final String TAG = "LocationAdapter";
    static Context mContext;

    private ItemData[] itemsData;

    public PubAdapter(ItemData[] itemsData) {
        this.itemsData = itemsData;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textPubName;
        public TextView textAddress;
        public TextView textPhone;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textPubName = (TextView) itemView.findViewById(R.id.item_pub_name);
            textAddress = (TextView) itemView.findViewById(R.id.item_address);
            textPhone = (TextView) itemView.findViewById(R.id.item_phone);
        }

        @Override
        public void onClick(View v) {

            // Edit by csk 2015-08-11 : 펍 상세화면 추가
            Intent intent = new Intent(mContext, PubDetailActivity.class);
            intent.putExtra("pub_id", textPubName.getText().toString());
            mContext.startActivity(intent);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pub, parent, false);
        mContext = parent.getContext();

        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d(TAG, "Element " + position + " set.");

        try {
            holder.textPubName.setText(itemsData[position].getPubName());
            holder.textAddress.setText(itemsData[position].getAddress());
            holder.textPhone.setText(itemsData[position].getPhone());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return itemsData.length;
    }
}