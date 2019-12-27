package com.cgwprj.parkingmanager.Models;

import com.cgwprj.parkingmanager.Utils.Calculator;
import com.cgwprj.parkingmanager.Utils.DateConverter;
import java.util.Date;

public class CarInquiryInfo implements Comparable{
    private String carNumber;
    private String enrollTime;
    private String unregisterTime;
    private String fee;
    private String takenTime;

    public CarInquiryInfo(){}

    public CarInquiryInfo(CarInfo carInfo){
        carNumber = carInfo.getCarNumber();
        enrollTime = carInfo.getRegisterTime();

        Date registerDate = DateConverter.getDateByString(carInfo.getRegisterTime());
        Date unregisterDate = new Date();

        unregisterTime = DateConverter.getStringByDate(unregisterDate);

        int takenTimeInteger = Calculator.BetweenMinutes(registerDate, unregisterDate);
        int feeInteger = Calculator.FeeCalculate(takenTimeInteger);
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
            CarInquiryInfo compInfo = (CarInquiryInfo) other;

            Date origin = DateConverter.getDateByString(this.unregisterTime);
            Date comp = DateConverter.getDateByString(compInfo.unregisterTime);

            return origin.compareTo(comp) * -1;
        }

        return 0;
    }
}
