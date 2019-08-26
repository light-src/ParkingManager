package com.cgwprj.parkingmanager.Models;

import com.cgwprj.parkingmanager.Utils.Calculator;
import com.cgwprj.parkingmanager.Utils.Converter;
import java.util.Date;

public class CarInquiryInfo implements Comparable{
    String carNumber;
    String enrollTime;
    String unregisterTime;
    String fee;
    String takenTime;

    public CarInquiryInfo(){}

    public CarInquiryInfo(CarInfo carInfo){
        carNumber = carInfo.getCarNumber();
        enrollTime = carInfo.getRegisterTime();

        Date registerDate = Converter.getDateByString(carInfo.registerTime);
        Date unregisterDate = new Date();

        unregisterTime = Converter.getStringByDate(unregisterDate);

        int takenTimeInteger = Calculator.BetweenMinutes(registerDate, unregisterDate);
        int feeInteger = Calculator.FeeCalculator(takenTimeInteger);
        takenTime = Integer.toString(takenTimeInteger);
        fee = Integer.toString(feeInteger);
    }

    public String getCarNumber() {
        return carNumber;
    }

    public String getEnrollTime() {
        return enrollTime;
    }

    public String getUnregisterTime() {
        return unregisterTime;
    }

    public String getFee() {
        return fee;
    }

    public String getTakenTime() {
        return takenTime;
    }

    @Override
    public int compareTo(Object other) {

        if (other instanceof CarInquiryInfo){
            CarInquiryInfo comp = (CarInquiryInfo) other;

            return unregisterTime.compareTo(comp.getUnregisterTime()) * -1;
        }

        return 0;
    }
}
