package com.misd.cookapp;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * This Dialog provides an UI to pic a date.
 * @author Michael Landreh
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    OnTimePickedListener mCallback;

    /**
     * An interface containing onTimePicked() method signature.
     * Container Activity must implement this interface.
     */
    public interface OnTimePickedListener {
        public void onTimePicked(int hour, int minute);
    }

    /* (non-Javadoc)
     * @see android.app.DialogFragment#onAttach(android.app.Activity)
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (OnTimePickedListener)activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnTimePickedListener.");
        }
    }

    /* (non-Javadoc)
     * @see android.app.DialogFragment#onCreateDialog(android.os.Bundle)
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of DatePickerDialog and return it
        return new TimePickerDialog(getActivity(),this,hour,minute, DateFormat.is24HourFormat(getActivity()));
    }

    /* (non-Javadoc)
     * @see android.app.TimePickerDialog.OnTimeSetListener#onTimeSet(android.widget.TimePicker, int, int)
     */
    public void onTimeSet(TimePicker view, int hour, int minute) {
        if(mCallback != null)
        {
            mCallback.onTimePicked(hour, minute);
        }
    }
}