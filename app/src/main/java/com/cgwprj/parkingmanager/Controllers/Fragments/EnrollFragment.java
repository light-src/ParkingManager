package com.cgwprj.parkingmanager.Controllers.Fragments;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cgwprj.parkingmanager.Data.UserData;
import com.cgwprj.parkingmanager.Models.CarInfo;
import com.cgwprj.parkingmanager.R;
import com.cgwprj.parkingmanager.Utils.DateConverter;
import com.cgwprj.parkingmanager.Controllers.Acitivity.MainActivity;
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

        final View view = inflater.inflate(R.layout.fragment_enroll, container, false);

        final TextView car = view.findViewById(R.id.enroll_car_number);
        TextView enrollDate = view.findViewById(R.id.enroll_enroll_date);
        Button enrollBtn = view.findViewById(R.id.enroll_enter);
        Button cancelBtn = view.findViewById(R.id.enroll_cancel);

        Date now = new Date();
        final String nowString = DateConverter.getStringByDate(now);

        Resources resources = getResources();

        car.setText(resources.getString(R.string.menu_car_number)+ " : " + carNumber);
        enrollDate.setText(resources.getString(R.string.menu_enroll_time) + " : " + nowString);

        enrollBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CarInfo carInfo = new CarInfo();
                carInfo.setRegisterTime(nowString);
                carInfo.setCarNumber(carNumber);

                sendCarInfoToFireBase(carInfo);
                ChangeFragmentToMain();
                Toast.makeText(view.getContext(), carInfo.getCarNumber() + " 입차 하였습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeFragmentToMain();
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


    private void ChangeFragmentToMain(){
        ((MainActivity)getActivity()).ChangeFragmentToMain();
    }
}
