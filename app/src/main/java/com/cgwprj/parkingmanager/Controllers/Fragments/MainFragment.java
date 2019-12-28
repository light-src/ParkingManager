package com.cgwprj.parkingmanager.Controllers.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.cgwprj.parkingmanager.Models.CarInfo;
import com.cgwprj.parkingmanager.R;
import com.cgwprj.parkingmanager.Controllers.Acitivity.MainActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class MainFragment extends android.support.v4.app.Fragment{

    List<CarInfo> carInfos;
    SimpleAdapter simpleAdapter;

    List<HashMap<String,String>> DataList = new ArrayList<>();
    TextView carNumberView;

    public MainFragment() {

    }

    public void setCarInfos(List<CarInfo> carInfos){
        this.carInfos = carInfos;
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    public void addCarInfoToList(CarInfo c){
        HashMap<String,String> map = new HashMap<>();
        map.put("CarNumber", c.getCarNumber());
        map.put("Register", c.getRegisterTime());
        DataList.add(map);

        Collections.sort(DataList, new Comparator<HashMap<String, String>>() {
            @Override
            public int compare(HashMap<String, String> o1, HashMap<String, String> o2) {
                return o1.get("CarNumber").compareTo(o2.get("CarNumber"));
            }
        });

        notifyDataSetChanged();
    }

    public void deleteCarInfoFromList(CarInfo c){
        HashMap<String,String> map = new HashMap<>();
        map.put("CarNumber", c.getCarNumber());
        map.put("Register", c.getRegisterTime());
        DataList.remove(map);

        notifyDataSetChanged();
    }

    public void notifyDataSetChanged(){
        carNumberView.setText("등록된 차 : " + (carInfos.size()));
        simpleAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        carNumberView = view.findViewById(R.id.car_number_view);
        ListView listView = view.findViewById(R.id.car_list_view);

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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((MainActivity) getActivity()).setSearchBar(carInfos.get(position).getCarNumber());
            }
        });

        carNumberView.setText("등록된 차 : " + Integer.toString(carInfos.size()));

        return view;
    }



}
