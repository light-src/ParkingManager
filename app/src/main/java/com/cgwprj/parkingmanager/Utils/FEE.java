package com.cgwprj.parkingmanager.Utils;

public enum FEE {
    FEE_FREE_TIME(10),
    FEE_FIRST_BOUNDARY_TIME(30),
    FEE_FIRST_BOUNDARY_FEE(1000),
    FEE_PER_TEN_MINUTES(400);

    public int constants;

    FEE(int constants){
        this.constants = constants;
    }

    public int getConstants(){
        return constants;
    }
}