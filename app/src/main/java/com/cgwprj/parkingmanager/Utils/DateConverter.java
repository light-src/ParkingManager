package com.cgwprj.parkingmanager.Utils;

import android.util.Log;

import com.cgwprj.parkingmanager.Models.CarInfo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {

    public static Date getDateByString(String format){

        try {
            return new SimpleDateFormat(StringConstants.DATE_FORMAT.getConstants()).parse(format);
        }
        catch (java.text.ParseException e){
            Log.e("ERROR", e.toString());
        }

        return null;
    }

    public static String getStringByDate(Date date){
        return new SimpleDateFormat(StringConstants.DATE_FORMAT.getConstants()).format(date);
    }



}
