package com.cgwprj.parkingmanager;

import com.cgwprj.parkingmanager.Utils.Calculator;
import com.cgwprj.parkingmanager.Utils.StringConstants;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class CalculatorTest {
    @Test
    public void TestCalculator() {

        try {

            String prevTime = "21:09 오후 19/08/27";
            String curTime = "21:18 오후 19/08/27";

            Date prv = new SimpleDateFormat(StringConstants.DATE_FORMAT.getConstants()).parse(prevTime);
            Date cur = new SimpleDateFormat(StringConstants.DATE_FORMAT.getConstants()).parse(curTime);
            int takenTime = Calculator.BetweenMinutes(prv, cur);
            int fee = Calculator.FeeCalculator(takenTime);

            assertEquals(fee, 0);


            prevTime = "21:09 오후 19/08/27";
            curTime = "21:19 오후 19/08/27";

            prv = new SimpleDateFormat(StringConstants.DATE_FORMAT.getConstants()).parse(prevTime);
            cur = new SimpleDateFormat(StringConstants.DATE_FORMAT.getConstants()).parse(curTime);
            takenTime = Calculator.BetweenMinutes(prv, cur);
            fee = Calculator.FeeCalculator(takenTime);

            assertEquals(fee, 0);


            prevTime = "21:09 오후 19/08/27";
            curTime = "21:20 오후 19/08/27";

            prv = new SimpleDateFormat(StringConstants.DATE_FORMAT.getConstants()).parse(prevTime);
            cur = new SimpleDateFormat(StringConstants.DATE_FORMAT.getConstants()).parse(curTime);
            takenTime = Calculator.BetweenMinutes(prv, cur);
            fee = Calculator.FeeCalculator(takenTime);

            assertEquals(fee, 1000);


            prevTime = "21:09 오후 19/08/27";
            curTime = "21:39 오후 19/08/27";

            prv = new SimpleDateFormat(StringConstants.DATE_FORMAT.getConstants()).parse(prevTime);
            cur = new SimpleDateFormat(StringConstants.DATE_FORMAT.getConstants()).parse(curTime);
            takenTime = Calculator.BetweenMinutes(prv, cur);
            fee = Calculator.FeeCalculator(takenTime);

            assertEquals(fee, 1000);


            prevTime = "21:09 오후 19/08/27";
            curTime = "21:40 오후 19/08/27";

            prv = new SimpleDateFormat(StringConstants.DATE_FORMAT.getConstants()).parse(prevTime);
            cur = new SimpleDateFormat(StringConstants.DATE_FORMAT.getConstants()).parse(curTime);
            takenTime = Calculator.BetweenMinutes(prv, cur);
            fee = Calculator.FeeCalculator(takenTime);

            assertEquals(fee, 1400);

            prevTime = "21:09 오후 19/08/27";
            curTime = "21:49 오후 19/08/27";

            prv = new SimpleDateFormat(StringConstants.DATE_FORMAT.getConstants()).parse(prevTime);
            cur = new SimpleDateFormat(StringConstants.DATE_FORMAT.getConstants()).parse(curTime);
            takenTime = Calculator.BetweenMinutes(prv, cur);
            fee = Calculator.FeeCalculator(takenTime);

            assertEquals(fee, 1400);


            prevTime = "21:09 오후 19/08/27";
            curTime = "21:50 오후 19/08/27";

            prv = new SimpleDateFormat(StringConstants.DATE_FORMAT.getConstants()).parse(prevTime);
            cur = new SimpleDateFormat(StringConstants.DATE_FORMAT.getConstants()).parse(curTime);
            takenTime = Calculator.BetweenMinutes(prv, cur);
            fee = Calculator.FeeCalculator(takenTime);

            assertEquals(fee, 1800);

        }catch (java.text.ParseException e){
            assert true;
        }
    }


}