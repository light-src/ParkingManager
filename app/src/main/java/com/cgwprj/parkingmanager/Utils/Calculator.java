package com.cgwprj.parkingmanager.Utils;

import java.util.Date;

public class Calculator {

    public static int BetweenMinutes(Date d1, Date d2){

        if( d1.getTime() > d2.getTime())
            return BetweenMinutes(d2, d1);

        long prv = d1.getTime();
        long nxt = d2.getTime();

        int sec_time = (int) ((nxt - prv)/1000);

        return sec_time / 60;
    }

    public static int FeeCalculator(int takenTime){

        // Over first 10 minutes
        if( takenTime > IntegerConstants.FEE_FREE_TIME.getConstants() ){

            // First 30 minutes fee
            int fee = IntegerConstants.FEE_FIRST_BOUNDARY_FEE.getConstants();

            // Over 30 minutes.
            if ( takenTime > IntegerConstants.FEE_FIRST_BOUNDARY_TIME.getConstants() ){
                int overTime = takenTime - IntegerConstants.FEE_FIRST_BOUNDARY_TIME.getConstants();
                int overStep = overTime / 10;
                fee += ( overStep * IntegerConstants.FEE_PER_TEN_MINUTES.getConstants());
            }

            return fee;
        }

        return 0;
    }
}
