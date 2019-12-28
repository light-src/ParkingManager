package com.cgwprj.parkingmanager.Controllers.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.cgwprj.parkingmanager.Data.UserData;
import com.cgwprj.parkingmanager.Models.CarInfo;
import com.cgwprj.parkingmanager.Models.CarInquiryInfo;
import com.cgwprj.parkingmanager.R;
import com.cgwprj.parkingmanager.Utils.StringConstants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LookupFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "CARNUMBER";
    FirebaseFirestore db;
    List<CarInquiryInfo> carInquiryInfos;
    SimpleAdapter simpleAdapter;
    List<HashMap<String,String>> DataList = new ArrayList<>();

    private String carNumber;

    public LookupFragment() {
        // Required empty public constructor
    }

    public static LookupFragment newInstance(String param1) {
        LookupFragment fragment = new LookupFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            carNumber = getArguments().getString(ARG_PARAM1);
            carInquiryInfos = new ArrayList<>();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_lookup, container, false);

        db = FirebaseFirestore.getInstance();
        db.collection(StringConstants.COLLECTION_PATH_PARKINGLOT.getConstants())
                .document(UserData.getInstance().getParkingLot())
                .collection(Integer.toHexString(carNumber.hashCode()))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                CarInquiryInfo carInquiryInfo =  document.toObject(CarInquiryInfo.class);
                                carInquiryInfos.add(carInquiryInfo);
                            }

                            Collections.sort(carInquiryInfos);

                            for(CarInquiryInfo carInquiryInfo : carInquiryInfos) {
                                HashMap<String,String> map = new HashMap<>();
                                map.put("CarNumber", carInquiryInfo.getCarNumber());
                                map.put("Register", "출차 : " + carInquiryInfo.getUnregisterTime());
                                DataList.add(map);
                            }

                            simpleAdapter.notifyDataSetChanged();

                            TextView textView = view.findViewById(R.id.lookup_text);
                            if (carInquiryInfos.size() > 0){
                                textView.setText("");
                            }
                        } else {
                            Log.w("ERROR", "Error getting documents.", task.getException());
                        }
                    }
                });

        ListView listView = view.findViewById(R.id.lookup_list);

        simpleAdapter = new SimpleAdapter(getContext(),DataList,android.R.layout.simple_list_item_2,new String[]{"CarNumber","Register"},new int[]{android.R.id.text1,android.R.id.text2});

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                final CarInquiryInfo carInquiryInfo = carInquiryInfos.get(position);

                String carInfoString =
                        "차 번호 : " + carInquiryInfo.getCarNumber() + "\n" +
                                "입차 시간 : " + carInquiryInfo.getEnrollTime() + "\n" +
                                "출차 시간 : " + carInquiryInfo.getUnregisterTime() + "\n" +
                                "주차 시간 : " + carInquiryInfo.getTakenTime() + "\n" +
                                "주차 요금 : " + carInquiryInfo.getFee();

                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(carInfoString)
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("Revert", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                final CarInfo carInfo = new CarInfo();

                                carInfo.setCarNumber(carInquiryInfo.getCarNumber());
                                carInfo.setRegisterTime(carInquiryInfo.getEnrollTime());

                                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference()
                                        .child(UserData.getInstance().getParkingLot())
                                        .child(Integer.toString(carInfo.hashCode()));
                                myRef.setValue(carInfo);

                                Toast.makeText(view.getContext(), carInfo.getCarNumber() + " 입차 하였습니다.", Toast.LENGTH_SHORT).show();

                                db.collection(StringConstants.COLLECTION_PATH_PARKINGLOT.getConstants())
                                        .document(UserData.getInstance().getParkingLot())
                                        .collection(Integer.toHexString(carNumber.hashCode()))
                                        .document(Integer.toString(carInquiryInfo.hashCode()))
                                        .delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(view.getContext(), carInfo.getCarNumber() + " 삭제 성공 하였습니다.", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(view.getContext(), carInfo.getCarNumber() + " 삭제 실패 하였습니다.", Toast.LENGTH_SHORT).show();
                                            }
                                        });


                            }
                        });
                builder.show();
            }
        });

        listView.setAdapter(simpleAdapter);


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
