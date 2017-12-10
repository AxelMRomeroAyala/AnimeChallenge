package com.yakarex.animequiz.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.yakarex.animequiz.R;

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

    public static boolean hasDailyRandom(){

        return false;
    }
}
