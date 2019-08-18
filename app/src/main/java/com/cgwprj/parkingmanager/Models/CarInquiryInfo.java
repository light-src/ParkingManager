package com.cgwprj.parkingmanager.Models;

import android.util.Log;

import com.cgwprj.parkingmanager.Utils.Calculator;
import com.cgwprj.parkingmanager.Utils.StringConstants;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CarInquiryInfo {
    CarInfo carInfo;
    String unregisterTime;
    String fee;
    String takenTime;

    public CarInquiryInfo(CarInfo carInfo){
        this.carInfo = carInfo;

        try {
            Date registerDate = new SimpleDateFormat(StringConstants.DATE_FORMAT.getConstants()).parse(carInfo.registerTime);
            Date unregisterDate = new Date();

            unregisterTime = new SimpleDateFormat(StringConstants.DATE_FORMAT.getConstants()).format(unregisterDate);

            int takenTimeInteger = Calculator.BetweenMinutes(registerDate, unregisterDate);
            int feeInteger = Calculator.FeeCalculator(takenTimeInteger);
            takenTime = Integer.toString(takenTimeInteger);
            fee = Integer.toString(feeInteger);
        }
        catch(java.text.ParseException e){
            Log.e("ERROR", e.toString());
        }

    }
}
