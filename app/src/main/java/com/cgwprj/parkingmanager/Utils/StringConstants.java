package com.cgwprj.parkingmanager.Utils;

public enum StringConstants {
    DATE_FORMAT("yyyy-MM-dd HH:mm:ss.S");

    private final String constants;

    StringConstants(String constants){
        this.constants = constants;
    }

    public String getConstants(){
        return constants;
    }


}
