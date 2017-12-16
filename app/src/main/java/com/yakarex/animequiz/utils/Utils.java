package com.yakarex.animequiz.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.provider.ContactsContract;
import android.text.format.DateUtils;

import com.yakarex.animequiz.R;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;

import io.paperdb.Paper;

/**
 * Created by aromero on 22/10/15.
 */
public class Utils {

    public static AlertDialog getAlertDialog(Context context, int message
            , int positiveText, DialogInterface.OnClickListener positiveListener
            , int negativeText, DialogInterface.OnClickListener negativeListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage(message);
        builder.setPositiveButton(positiveText, positiveListener);
        builder.setNegativeButton(negativeText, negativeListener);

        return builder.create();
    }

    public static String getTimeStamp(){

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");

        return format.format(date);
    }

    public static boolean hasDailyRandom() {

        if(Paper.book().read(FinalStringsUtils.DAILY_RANDOM)!= null){

            String lastRandomTimestamp= Paper.book().read(FinalStringsUtils.DAILY_RANDOM);

            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
            Date lastRandomDate;
            Date nowDate=  new Date();

            int days= 0;

            try {
                lastRandomDate = dateFormat.parse(lastRandomTimestamp);
                days = Days.daysBetween(new DateTime(lastRandomDate), new DateTime(nowDate)).getDays();

            } catch (ParseException e) {
                e.printStackTrace();
                return true;
            }
            
            return days >= 1;
        }
        else {
            return true;
        }
    }
}
