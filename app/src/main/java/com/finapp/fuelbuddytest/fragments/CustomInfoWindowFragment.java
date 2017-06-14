package com.finapp.fuelbuddytest.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.finapp.fuelbuddytest.R;
import com.finapp.fuelbuddytest.ui.Petrole;

public class CustomInfoWindowFragment extends Fragment {


    private ImageView markerLogo;
    private TextView markerAdress;
    private TextView markerPrice;
    private ImageView likeBtn;
    private ImageView directionBtn;
    private Petrole petrole;


    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.custom_info_window, container, false);

        petrole = getArguments().getParcelable("petrole");

        markerLogo = (ImageView)view.findViewById(R.id.marker_logo);
        String logoName = petrole.getIcon();
        int logoResID = getActivity().getResources().getIdentifier(logoName , "drawable", getActivity().getPackageName());
        markerLogo.setImageResource(logoResID);

        markerAdress = (TextView)view.findViewById(R.id.marker_adress);
        markerAdress.setText(petrole.getAdress());

        markerPrice = (TextView)view.findViewById(R.id.marker_price);
        markerPrice.setText(petrole.getCost());

        likeBtn = (ImageView)view.findViewById(R.id.marker_like_btn);
        directionBtn = (ImageView)view.findViewById(R.id.marker_direction_btn);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.marker_like_btn:
                        Toast.makeText(getContext(), "Like Button pressed", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.marker_direction_btn:
                        Toast.makeText(getContext(), "Direction Button pressed", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(getContext(), "Something pressed", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        likeBtn.setOnClickListener(onClickListener);
        directionBtn.setOnClickListener(onClickListener);
    }
}
