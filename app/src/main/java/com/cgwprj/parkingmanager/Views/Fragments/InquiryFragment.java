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
import com.cgwprj.parkingmanager.Utils.Calculator;
import com.cgwprj.parkingmanager.Utils.Converter;
import com.cgwprj.parkingmanager.Views.Acitivity.MainActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class InquiryFragment extends Fragment {

    private static final String CARINFO = "CARINFO";
    private CarInfo carInfo;

    public InquiryFragment() {
        // Required empty public constructor
    }

    public static InquiryFragment newInstance(CarInfo carInfo) {
        InquiryFragment fragment = new InquiryFragment();
        Bundle args = new Bundle();
        args.putSerializable(CARINFO, carInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        carInfo = (CarInfo) getArguments().getSerializable(CARINFO);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_inquiry, container, false);

        TextView carNumber = view.findViewById(R.id.inq_car_number);
        TextView enrollDate = view.findViewById(R.id.inq_enroll_date);
        TextView outDate = view.findViewById(R.id.inq_exit_date);
        TextView taken = view.findViewById(R.id.inq_taken_time);
        TextView fee = view.findViewById(R.id.inq_fee);
        Button exitBtn = view.findViewById(R.id.inq_exit);
        Button cancelBtn = view.findViewById(R.id.inq_cancel);

        Date enroll = Converter.getDateByString(carInfo.getRegisterTime());
        Date out = new Date();

        int takenTime = Calculator.BetweenMinutes(enroll, out);
        int feeInteger = Calculator.FeeCalculator(takenTime);

        carNumber.setText(carInfo.getCarNumber());
        enrollDate.setText(carInfo.getRegisterTime());
        outDate.setText(Converter.getStringByDate(out));
        taken.setText(Integer.toString(takenTime));
        fee.setText(Integer.toString(feeInteger));

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference()
                        .child(UserData.getInstance().getParkingLot())
                        .child(Integer.toString(carInfo.hashCode()));
                myRef.setValue(null);

                ChangeFragmentToMain();
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

    private void ChangeFragmentToMain(){
        ((MainActivity)getActivity()).ChangeFragmentToMain();
    }

}
