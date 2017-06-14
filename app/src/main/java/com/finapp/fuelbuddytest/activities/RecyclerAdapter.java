package com.finapp.fuelbuddytest.activities;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.finapp.fuelbuddytest.R;
import com.finapp.fuelbuddytest.ui.Petrole;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>  {

    private ArrayList<Petrole> allPetroles;
    private Activity context;
    private OnClickListener mListener;
    private int positionClick = -1;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout linearClick;
        public TextView priceTV;
        public TextView timeTV;
        public ImageView logoIM;
        public TextView titleTV;
        public TextView adressTV;
        public ImageView directionIM;
        public TextView distanceTV;

        public ViewHolder(View v) {
            super(v);
            linearClick = (LinearLayout)v.findViewById(R.id.click_linear);
            priceTV = (TextView)v.findViewById(R.id.price);
            timeTV = (TextView)v.findViewById(R.id.time);
            logoIM = (ImageView) v.findViewById(R.id.logo);
            titleTV = (TextView)v.findViewById(R.id.title);
            adressTV = (TextView)v.findViewById(R.id.adress);
            directionIM = (ImageView) v.findViewById(R.id.direction);
            distanceTV = (TextView)v.findViewById(R.id.distance);
        }

    }

    public RecyclerAdapter(ArrayList<Petrole> petroles, Activity context, OnClickListener onClickListener) {
        this.allPetroles = petroles;
        this.context = context;
        this.mListener = onClickListener;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.petrole_raw, parent, false);


        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.linearClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                positionClick = position;
                notifyDataSetChanged();
                mListener.onItemClicked(position);
            }
        });
        if (positionClick == position) {
            holder.linearClick.setSelected(true);
        }
        else {
            holder.linearClick.setSelected(false);
        }

        holder.priceTV.setText(allPetroles.get(position).getCost());
        holder.timeTV.setText(allPetroles.get(position).getTime());

        String logoName = allPetroles.get(position).getIcon();
        int logoResID = context.getResources().getIdentifier(logoName , "drawable", context.getPackageName());
        holder.logoIM.setImageResource(logoResID);

        holder.titleTV.setText(allPetroles.get(position).getTitle());
        holder.adressTV.setText(allPetroles.get(position).getAdress());
        holder.distanceTV.setText(allPetroles.get(position).getDistance() + " км");;

        String directionName = "icon_direction";
        int directionResID = context.getResources().getIdentifier(directionName , "drawable", context.getPackageName());
        holder.directionIM.setImageResource(directionResID);
    }

    @Override
    public int getItemCount() {
        return allPetroles.size();
    }

    public interface OnClickListener {
        void onItemClicked(int position);
    }
}
