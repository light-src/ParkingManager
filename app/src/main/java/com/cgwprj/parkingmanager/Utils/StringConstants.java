package com.cgwprj.parkingmanager.Utils;

public enum StringConstants {
    DATE_FORMAT("HH:mm:ss yy/MM/dd");

    private final String constants;

    StringConstants(String constants){
        this.constants = constants;
    }

    public String getConstants(){
        return constants;
    }
}
