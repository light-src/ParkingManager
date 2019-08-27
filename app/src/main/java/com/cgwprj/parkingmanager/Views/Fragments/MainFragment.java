package com.cgwprj.parkingmanager.Views.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.cgwprj.parkingmanager.Models.CarInfo;
import com.cgwprj.parkingmanager.R;
import com.cgwprj.parkingmanager.Views.Acitivity.MainActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("All")
public class MainFragment extends android.support.v4.app.Fragment{

    List<CarInfo> carInfos;
    SimpleAdapter simpleAdapter;
    int carInfosLength;
    List<HashMap<String,String>> DataList = new ArrayList<>();

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

        return view;
    }



}
