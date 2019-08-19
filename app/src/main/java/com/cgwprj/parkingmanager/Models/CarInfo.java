package com.cgwprj.parkingmanager.Models;

import java.io.Serializable;

public class CarInfo implements Serializable {
    String carNumber;
    String registerTime;

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }


    @Override
    public boolean equals(Object carInfo){
        if( carInfo instanceof CarInfo) {
            CarInfo comp = (CarInfo) carInfo;
            return carNumber.equals(comp.carNumber) && registerTime.equals(comp.registerTime);
        }

        return false;
    }

    @Override
    public int hashCode(){
        int hash = carNumber.hashCode();
        hash += registerTime.hashCode() * 31;

        return hash;
    }
}
