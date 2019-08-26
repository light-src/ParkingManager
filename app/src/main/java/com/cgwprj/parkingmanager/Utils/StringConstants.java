package com.cgwprj.parkingmanager.Utils;

public enum StringConstants {
    DATE_FORMAT("HH:mm a yy/MM/dd"),
    COLLECTION_PATH_PARKINGLOT("PARKINGLOT");

    private final String constants;

    StringConstants(String constants){
        this.constants = constants;
    }

    public String getConstants(){
        return constants;
    }
}
