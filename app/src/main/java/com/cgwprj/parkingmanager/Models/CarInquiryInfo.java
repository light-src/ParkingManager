package com.cgwprj.parkingmanager.Models;

import com.cgwprj.parkingmanager.Utils.Calculator;
import com.cgwprj.parkingmanager.Utils.Converter;
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

        Date registerDate = Converter.getDateByString(carInfo.registerTime);
        Date unregisterDate = new Date();

        unregisterTime = Converter.getStringByDate(unregisterDate);

        int takenTimeInteger = Calculator.BetweenMinutes(registerDate, unregisterDate);
        int feeInteger = Calculator.FeeCalculator(takenTimeInteger);
        takenTime = Integer.toString(takenTimeInteger);
        fee = Integer.toString(feeInteger);


    }
}
