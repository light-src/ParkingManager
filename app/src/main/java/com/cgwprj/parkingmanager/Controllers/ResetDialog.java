package com.cgwprj.parkingmanager.Controllers;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cgwprj.parkingmanager.Data.UserData;
import com.cgwprj.parkingmanager.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class ResetDialog extends Dialog {

    private Button mPositiveButton;
    private Button mNegativeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_reset);

        //셋팅
        mPositiveButton=(Button)findViewById(R.id.pbutton);
        mNegativeButton=(Button)findViewById(R.id.nbutton);

        Random random = new Random();


        final TextView mResetNumber = findViewById(R.id.reset_number);
        mResetNumber.setText(Integer.toString(random.nextInt(9999) % 10000));

        final EditText mResetNumberChecker = findViewById(R.id.reset_check);

        //클릭 리스너 셋팅 (클릭버튼이 동작하도록 만들어줌.)
        mPositiveButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (mResetNumber.getText().toString().equals(mResetNumberChecker.getText().toString())) {
                            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference()
                                    .child(UserData.getInstance().getParkingLot());

                            myRef.setValue("");
                        }

                        dismiss();
                    }
                });

        mNegativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    //생성자 생성
    public ResetDialog(@NonNull Context context) {
        super(context);
    }
}