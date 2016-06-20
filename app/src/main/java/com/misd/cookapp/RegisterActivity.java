package com.misd.cookapp;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.misd.cookapp.helpers.HelperMethods;
import com.misd.cookapp.interfaces.IServer;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.util.Calendar;

import static com.misd.cookapp.helpers.HelperMethods.pasteCalendar;

/**
 * This Activity ables the User to register to the Application.
 * @author Ines Müller
 */
public class RegisterActivity extends AppCompatActivity implements DatePickerFragment.OnDatePickedListener {
    private static final String TAG = "RegisterActivity";
    int unixDateOfBirth;
    User createdUser;
    View rootView;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        setTitle("Registrierung");
        rootView = findViewById(android.R.id.content);
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }


    @Override
    public void onDatePicked(final int year, final int month, final int day) {
        Calendar birthday = pasteCalendar(year, month, day);
        this.unixDateOfBirth = (int) birthday.getTimeInMillis() / 1000;

        Button datePicker = (Button) findViewById(R.id.birthday_picker);
        datePicker.setText(day + "." + month + "." + year);
    }



    /*
     * Validierung
     */
    public boolean isValidRegisterFirstname() {
        EditText textRegisterFirstname  = (EditText) findViewById(R.id.textRegisterFirstname);
        if (textRegisterFirstname.length() == 0) {
            textRegisterFirstname.setError("Feld darf nicht leer sein.");
            return false;
        }
        else {
            textRegisterFirstname.setError(null);
            return true;
        }
    }
    public boolean isValidRegisterLastname() {
        EditText textRegisterLastname  = (EditText) findViewById(R.id.textRegisterLastname);
        if (textRegisterLastname.length() == 0) {
            textRegisterLastname.setError("Feld darf nicht leer sein.");
            return false;
        }
        else {
            textRegisterLastname.setError(null);
            return true;
        }
    }
    public boolean isValidRegisterStreet() {
        EditText textRegisterStreet = (EditText) findViewById(R.id.textRegisterStreet);
        if (textRegisterStreet.length() == 0) {
            textRegisterStreet.setError("Feld darf nicht leer sein.");
            return false;
        }
        else {
            textRegisterStreet.setError(null);
            return true;
        }
    }
    public boolean isValidRegisterPc() {
        EditText textRegisterPostalCode = (EditText) findViewById(R.id.textRegisterPostalCode);
        if (textRegisterPostalCode.length() == 0) {
            textRegisterPostalCode.setError("Feld darf nicht leer sein.");
            return false;
        } else if(!HelperMethods.isNumericInt(textRegisterPostalCode.getText().toString())){
            textRegisterPostalCode.setError("Es dürfen nur Zahlen eingegeben werden.");
            return false;
        }
        else {
            textRegisterPostalCode.setError(null);
            return true;
        }
    }
    public boolean isValidRegisterCity() {
        EditText textRegisterCity = (EditText) findViewById(R.id.textRegisterCity);
        if (textRegisterCity.length() == 0) {
            textRegisterCity.setError("Feld darf nicht leer sein.");
            return false;
        }
        else {
            textRegisterCity.setError(null);
            return true;
        }
    }
    public boolean isValidRegisterTelephonnumber() {
        EditText textRegisterTelephonenumber = (EditText) findViewById(R.id.textRegisterTelephonenumber);
        if (textRegisterTelephonenumber.length() == 0) {
            textRegisterTelephonenumber.setError("Feld darf nicht leer sein.");
            return false;
        }
        else {
            textRegisterTelephonenumber.setError(null);
            return true;
        }
    }
    public boolean isValidRegisterGender() {
        RadioGroup radioGroupGenderUser = (RadioGroup) findViewById(R.id.radioGroupGenderUser);
        RadioButton radioGroupLastButton = (RadioButton) findViewById(R.id.radiobutton_female);
        if (radioGroupGenderUser.getCheckedRadioButtonId() <= 0) {
            // no radioButtons are checked
            return true;
        }
        else {
            // one of the radioButtons is checked
            radioGroupLastButton.setError("Eine Möglichkeit muss ausgewählt werden.");
            return false;
        }
    }
    public boolean isValidRegisterBirthday(){;
        Button btnDate = (Button) findViewById(R.id.birthday_picker);
        if (btnDate.getText().equals("Wähle ein Datum")) {
            btnDate.setError("Bitte wählen Sie ein Datum aus");
            return false;
        }
        btnDate.setError(null);
        return true;
    }

    public void register(View v) {
        EditText textRegisterFirstname  = (EditText) findViewById(R.id.textRegisterFirstname);
        String registerFirstname = textRegisterFirstname.getText().toString();

        EditText textRegisterLastname  = (EditText) findViewById(R.id.textRegisterLastname);
        String registerLastname = textRegisterLastname.getText().toString();

        EditText textRegisterMail = (EditText) findViewById(R.id.textRegisterMail);
        String registerMail = textRegisterMail.getText().toString();

        EditText textRegisterStreet = (EditText) findViewById(R.id.textRegisterStreet);
        String registerStreet = textRegisterStreet.getText().toString();

        EditText textRegisterPostalCode = (EditText) findViewById(R.id.textRegisterPostalCode);
        Integer registerPostalCode = Integer.parseInt(textRegisterPostalCode.getText().toString());

        EditText textRegisterCity = (EditText) findViewById(R.id.textRegisterCity);
        String registerCity = textRegisterCity.getText().toString();

        EditText textRegisterTelephonenumber = (EditText) findViewById(R.id.textRegisterTelephonenumber);
        String registerTelephonenumber = textRegisterTelephonenumber.getText().toString();

        RadioGroup radioGroupGenderUser = (RadioGroup) findViewById(R.id.radioGroupGenderUser);
        int selectedId = radioGroupGenderUser.getCheckedRadioButtonId(); // get selected radio button from radioGroup
        // Check which radiobutton was clicked
        char registerGender = 'x';
        switch (selectedId) {
            case R.id.radiobutton_male:
                registerGender = 'm';
                break;
            case R.id.radiobutton_female:
                registerGender = 'w';
                break;
        }

        User user = new User(registerMail, registerLastname, registerFirstname, registerStreet, registerPostalCode, registerCity, registerGender, unixDateOfBirth, registerTelephonenumber);
        Log.d(TAG, "User created: " + user.toString());
        new LoginTask().execute(user);
    }

    private class LoginTask extends AsyncTask<User, Void, Integer> {
        private boolean taskFailed = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected Integer doInBackground(User... params) {
            int sessionId = 0;
                    IServer server = CookApplication.getCookApplication().getServer();
            try {
                sessionId = server.register(params[0].getMailAddress(),
                        params[0].getLastname(),
                        params[0].getFirstname(),
                        params[0].getStreet(),
                        params[0].getPostalCode(),
                        params[0].getCity(),
                        (int)params[0].getBirthday().getTimeInMillis() / 1000,
                        params[0].getTelephoneNumber(),
                        params[0].getGender());

                CookApplication cookApplication = CookApplication.getCookApplication();
                if(sessionId > 0) {
                    cookApplication.setSessionId(sessionId);
                    cookApplication.setLoggedInUser(params[0]);
                }else {
                    cookApplication.setSessionId(0);
                    cookApplication.setLoggedInUser(null);
                }
            } catch (Exception e) {
                taskFailed = true;
                e.printStackTrace();

            }

            return sessionId;
        }

        @Override
        protected void onPostExecute(Integer sessionId) {
            hideProgressDialog();

            if (!taskFailed && sessionId != 0) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            } else {
                Snackbar.make(rootView,"Die Registrierung konnte wegen eines Fehlers nicht abgeschlossen werden",Snackbar.LENGTH_LONG).show();
            }
        }
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading_registration));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }
}
