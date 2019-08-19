package com.cgwprj.parkingmanager.Views.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cgwprj.parkingmanager.Data.UserData;
import com.cgwprj.parkingmanager.Models.CarInfo;
import com.cgwprj.parkingmanager.R;
import com.cgwprj.parkingmanager.Utils.Converter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class EnrollFragment extends Fragment {

    private static final String CARINFO = "CARNUMBER";
    private String carNumber;

    public EnrollFragment() {
        // Required empty public constructor
    }

    public static EnrollFragment newInstance(String carNumber) {
        EnrollFragment fragment = new EnrollFragment();
        Bundle args = new Bundle();
        args.putString(CARINFO, carNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        carNumber = getArguments().getString(CARINFO);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_enroll, container, false);

        final TextView car = view.findViewById(R.id.enroll_car_number);
        TextView enrollDate = view.findViewById(R.id.enroll_enroll_date);
        Button enrollBtn = view.findViewById(R.id.enroll_enter);

        Date now = new Date();
        final String nowString = Converter.getStringByDate(now);

        car.setText(carNumber);
        enrollDate.setText(nowString);

        enrollBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CarInfo carInfo = new CarInfo();
                carInfo.setRegisterTime(nowString);
                carInfo.setCarNumber(carNumber);

                sendCarInfoToFireBase(carInfo);
            }
        });

        return view;
    }

    private void sendCarInfoToFireBase(CarInfo carInfo){
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference()
                .child(UserData.getInstance().getParkingLot())
                .child(Integer.toString(carInfo.hashCode()));
        myRef.setValue(carInfo);
    }

}
