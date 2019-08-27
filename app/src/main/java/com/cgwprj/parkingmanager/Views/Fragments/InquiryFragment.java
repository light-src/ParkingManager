package com.cgwprj.parkingmanager.Views.Fragments;

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
import com.cgwprj.parkingmanager.Models.CarInquiryInfo;
import com.cgwprj.parkingmanager.R;
import com.cgwprj.parkingmanager.Views.Acitivity.MainActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class InquiryFragment extends Fragment {

    private static final String CARINFO = "CARINFO";
    private CarInfo carInfo;
    FirebaseFirestore db;

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

        final View view = inflater.inflate(R.layout.fragment_inquiry, container, false);

        final TextView carNumber = view.findViewById(R.id.inq_car_number);
        TextView enrollDate = view.findViewById(R.id.inq_enroll_date);
        TextView outDate = view.findViewById(R.id.inq_exit_date);
        TextView taken = view.findViewById(R.id.inq_taken_time);
        TextView fee = view.findViewById(R.id.inq_fee);
        Button exitBtn = view.findViewById(R.id.inq_exit);
        Button cancelBtn = view.findViewById(R.id.inq_cancel);

        Resources resources = getResources();
        db = FirebaseFirestore.getInstance();

        final CarInquiryInfo carInquiryInfo = new CarInquiryInfo(carInfo);

        carNumber.setText(resources.getString(R.string.menu_car_number) + " : " + carInquiryInfo.getCarNumber());
        enrollDate.setText(resources.getString(R.string.menu_enroll_time)+ " : " + carInquiryInfo.getEnrollTime());
        outDate.setText(resources.getString(R.string.menu_exit_time)+ " : " + carInquiryInfo.getUnregisterTime());
        taken.setText(resources.getString(R.string.menu_taken_time)+ " : " + carInquiryInfo.getTakenTime());
        fee.setText(resources.getString(R.string.menu_fee)+ " : " + carInquiryInfo.getFee());



        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference()
                        .child(UserData.getInstance().getParkingLot())
                        .child(Integer.toString(carInfo.hashCode()));
                myRef.setValue(null);

                ChangeFragmentToMain();

                db.collection("PARKINGLOT")
                        .document(UserData.getInstance().getParkingLot())
                        .collection(Integer.toHexString(carInfo.getCarNumber().hashCode()))
                        .add(carInquiryInfo);

                Toast.makeText(view.getContext(), carInfo.getCarNumber() + " 출차 하였습니다.", Toast.LENGTH_SHORT).show();
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
