package com.cgwprj.parkingmanager.Data;

public class UserData {
    private UserData(){}

    private static UserData instance = null;
    private String parkingLot = "TEMP";

    public static UserData getInstance(){
        if(instance != null)
            return instance;
        else
            instance = new UserData();

        return instance;
    }

    public String getParkingLot(){
        return parkingLot;
    }

    public void setParkingLot(String parkingLot) {
        this.parkingLot = parkingLot;
    }

}