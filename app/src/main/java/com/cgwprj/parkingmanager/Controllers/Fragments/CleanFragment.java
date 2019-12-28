package com.cgwprj.parkingmanager.Controllers.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.cgwprj.parkingmanager.Data.UserData;
import com.cgwprj.parkingmanager.Models.CarInfo;
import com.cgwprj.parkingmanager.Models.CarInquiryInfo;
import com.cgwprj.parkingmanager.R;
import com.cgwprj.parkingmanager.Controllers.Acitivity.MainActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CleanFragment extends android.support.v4.app.Fragment{

    FirebaseFirestore db;
    List<CarInfo> carInfos;
    SimpleAdapter simpleAdapter;
    Map<String, CarInfo> clickedNumber;

    List<HashMap<String,String>> DataList;
    TextView carNumberView;

    public CleanFragment() {

    }

    public void setCarInfos(List<CarInfo> carInfos){
        this.carInfos = carInfos;
    }

    public static CleanFragment newInstance() {
        CleanFragment fragment = new CleanFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final Fragment fragment = this;
        clickedNumber = new HashMap<>();
        db = FirebaseFirestore.getInstance();
        DataList = new ArrayList<>();

        final View view = inflater.inflate(R.layout.fragment_clean, container, false);

        carNumberView = view.findViewById(R.id.clean_selected_car_number);
        final ListView listView = view.findViewById(R.id.clean_car_list_view);
        listView.clearAnimation();

        Button cleanBtn = view.findViewById(R.id.clean_btn);

        setCarInfos(((MainActivity) getActivity()).getCarInfos());

        if(carInfos != null)
            for (CarInfo c : carInfos) {
                HashMap<String,String> map = new HashMap<>();
                map.put("CarNumber", c.getCarNumber());
                map.put("Register", c.getRegisterTime());
                DataList.add(map);
            }
        else
            carInfos = new ArrayList<>();

        simpleAdapter = new SimpleAdapter(getContext(),DataList,android.R.layout.simple_list_item_2,new String[]{"CarNumber","Register"},new int[]{android.R.id.text1,android.R.id.text2});

        listView.setAdapter(simpleAdapter);
        simpleAdapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((MainActivity) getActivity()).setSearchBar(carInfos.get(position).getCarNumber());

                CarInfo curInfo = carInfos.get(position);

                if(clickedNumber.get(curInfo.getCarNumber()) != null){
                    listView.getChildAt(position).setBackgroundColor(Color.TRANSPARENT);
                    clickedNumber.remove(curInfo.getCarNumber());
                }
                else{
                    listView.getChildAt(position).setBackgroundColor(Color.LTGRAY);

                    clickedNumber.put(curInfo.getCarNumber(), curInfo);
                }

                String carNumberViewText = "선택된 차 갯수 : " + Integer.toString(clickedNumber.size());
                carNumberView.setText(carNumberViewText);
            }
        });

        cleanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (CarInfo carInfo : carInfos) {
                    if (clickedNumber.get(carInfo.getCarNumber()) == null) {
                        CarInquiryInfo carInquiryInfo = new CarInquiryInfo(carInfo);

                        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference()
                                .child(UserData.getInstance().getParkingLot())
                                .child(Integer.toString(carInfo.hashCode()));
                        myRef.setValue(null);

                        db.collection("PARKINGLOT")
                                .document(UserData.getInstance().getParkingLot())
                                .collection(Integer.toHexString(carInfo.getCarNumber().hashCode()))
                                .document(Integer.toString(carInquiryInfo.hashCode()))
                                .set(carInquiryInfo);

                        Toast.makeText(view.getContext(), carInfo.getCarNumber() + " 출차 하였습니다.", Toast.LENGTH_SHORT).show();
                    }
                }

                ((MainActivity) getActivity()).ChangeFragmentToMain();
            }
        });

        String carNumberViewText = "선택된 차 갯수 : " + Integer.toString(clickedNumber.size());
        carNumberView.setText(carNumberViewText);

        return view;
    }



}
