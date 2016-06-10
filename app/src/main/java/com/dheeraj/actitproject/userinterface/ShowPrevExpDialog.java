package com.dheeraj.actitproject.userinterface;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import com.dheeraj.actitproject.interfaces.PrevExpDialogListener;

import java.util.Calendar;

/**
 * Created by DHEERAJ on 2/14/2016.
 */
public class ShowPrevExpDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    PrevExpDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //setting the current date as the default date of the date Picker
        final Calendar c=Calendar.getInstance();
        int year=c.get(Calendar.YEAR);
        int month=c.get(Calendar.MONTH);
        int day=c.get(Calendar.DAY_OF_MONTH);


      return new DatePickerDialog(getActivity(),this,year,month,day) ;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            listener= (PrevExpDialogListener) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString()+" must implement the interface PrevExpDialogListener");
        }
    }


    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        if (monthOfYear<9) {
            listener.onPrevDateSelected(this, dayOfMonth + "-0" + (monthOfYear + 1) + "-" + year);
        }
        else
            listener.onPrevDateSelected(this, dayOfMonth +"-"+ (monthOfYear + 1) + "-" + year);


    }
}
