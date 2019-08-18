package com.cgwprj.parkingmanager;

import com.cgwprj.parkingmanager.Utils.Calculator;
import com.cgwprj.parkingmanager.Utils.IntegerConstants;
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
        String prevTime = "2019-08-18 22:04:46.382";
        try {
            Date prv = new SimpleDateFormat(StringConstants.DATE_FORMAT.getConstants()).parse(prevTime);
            Date cur = new Date();

            int takenTime = Calculator.BetweenMinutes(prv, cur);
            int fee = Calculator.FeeCalculator(takenTime);

            if (takenTime < 10)
                assertEquals(fee, 0);

            else if (takenTime < 30)
                assertEquals(fee, IntegerConstants.FEE_FIRST_BOUNDARY_FEE.getConstants());

            else {
                int overTime = takenTime - IntegerConstants.FEE_FIRST_BOUNDARY_TIME.getConstants();
                int overStep = overTime / 10;
                int expectedFee = IntegerConstants.FEE_FIRST_BOUNDARY_FEE.getConstants() +
                        (IntegerConstants.FEE_PER_TEN_MINUTES.getConstants() * overStep);
                assertEquals(fee,expectedFee);
            }

        }catch (java.text.ParseException e){
            assert true;
        }
    }


}