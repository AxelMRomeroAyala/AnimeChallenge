package com.yakarex.animequiz.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.yakarex.animequiz.R;

/**
 * Created by aromero on 22/10/15.
 */
public class Utils {

    public static AlertDialog getAlertDialog(Context context,int message
            ,int positiveText,DialogInterface.OnClickListener positiveListener
            ,int negativeText,DialogInterface.OnClickListener negativeListener){

        return new AlertDialog.Builder(context, R.style.Base_Theme_AppCompat_Dialog)
                .setMessage(message)
                .setPositiveButton(positiveText,positiveListener)
                .setNegativeButton(negativeText,negativeListener).create();
    }
}
