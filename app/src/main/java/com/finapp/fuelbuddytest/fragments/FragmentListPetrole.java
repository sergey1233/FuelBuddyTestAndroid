package com.finapp.fuelbuddytest.fragments;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.finapp.fuelbuddytest.activities.MainActivity;
import com.finapp.fuelbuddytest.ui.Petrole;
import com.finapp.fuelbuddytest.R;
import com.finapp.fuelbuddytest.activities.RecyclerAdapter;

import java.util.ArrayList;


public class FragmentListPetrole extends Fragment implements RecyclerAdapter.OnClickListener {

    private static final String TAG = "FragmentListPetrole";

    private RecyclerView recyclerView;
    private ArrayList<Petrole> allPetroles;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ImageView triangle;

    public FragmentListPetrole() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_petrole, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.recycle_view);
        allPetroles = getArguments().getParcelableArrayList("petroles");

        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RecyclerAdapter(allPetroles, getActivity(), this);
        recyclerView.setAdapter(mAdapter);

        triangle = (ImageView)view.findViewById(R.id.triangle);
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels;
        ViewGroup.MarginLayoutParams lMargin = (ViewGroup.MarginLayoutParams) triangle.getLayoutParams();
        if (getArguments().getInt("type") == 0) {
            lMargin.leftMargin = (int)dpWidth / 4;
        }
        else {
            lMargin.leftMargin = (int)dpWidth / 4 * 3;
        }
        triangle.setLayoutParams(lMargin);

        return view;
    }

    public void createMarker(int position) {
        Log.d(TAG, " create marker from fragment");
    }

    @Override
    public void onItemClicked(int position) {
        createMarker(position);
        ((MainActivity)getActivity()).setMarker(position);
    }

    public void print(String text, float value) {
        Log.d(text, String.valueOf(value));
    }

    public void print(String text) {
        Log.d(TAG, text);
    }
}
