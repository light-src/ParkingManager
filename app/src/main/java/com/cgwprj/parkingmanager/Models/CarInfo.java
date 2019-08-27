package com.cgwprj.parkingmanager.Models;

import java.io.Serializable;

public class CarInfo implements Serializable, Comparable {
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

    @Override
    public int compareTo(Object o){
        if (o instanceof CarInfo){
            CarInfo other = (CarInfo) o;

            return carNumber.compareTo(other.getCarNumber());
        }

        return 0;
    }
}
