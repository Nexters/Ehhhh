package com.teamnexters.ehhhh.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamnexters.ehhhh.R;


public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragement";
    private static final String KEY_LAYOUT_MANAGER = "layoutmanager";

    protected LayoutManagerType mLayoutManagerType;
    protected RecyclerView mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected HomeAdapter mAdapter;

    public static HomeFragment newInstance() {

        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);

        return fragment;
    }

    private enum LayoutManagerType {
        LINEAR_LAYOUT_MANAGER
    }

    public class ItemData {
        private String title;
        private int imageUrl;

        public ItemData(String title, int imageUrl) {
            this.title = title;
            this.imageUrl = imageUrl;
        }

        // getters & setters
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(int imageUrl) {
            this.imageUrl = imageUrl;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.l_fragment_recyclerview, container, false);
        rootView.setTag(TAG);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        if (savedInstanceState != null) {
            mLayoutManagerType = (LayoutManagerType) savedInstanceState.getSerializable(KEY_LAYOUT_MANAGER);
        }

        ItemData mDataset[] = {new ItemData("이달의 추천펍", R.drawable.beer),
                new ItemData("이달의 보틀샵", R.drawable.beer),
                new ItemData("현식의 추천펍", R.drawable.beer)};

        setRecyclerViewLayoutManager(mLayoutManagerType);
        mAdapter = new HomeAdapter(mDataset);
        mRecyclerView.setAdapter(mAdapter);


        return rootView;
    }


    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case LINEAR_LAYOUT_MANAGER:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, mLayoutManagerType);
        super.onSaveInstanceState(savedInstanceState);
    }
}
