package com.misd.cookapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.misd.cookapp.exceptions.LoginFailedException;
import com.misd.cookapp.interfaces.IServer;

/*
 * @author Ines Müller
 */
public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        setTitle("Anmeldung");
    }

    public void login(View v) {
        EditText mailAdress = (EditText) findViewById(R.id.login_mail);
        String stringMail = mailAdress.getText().toString();
        LoginTask loginTask = new LoginTask();
        loginTask.execute(stringMail);
    }

    public void navigateToRegisterActivity(View v) {
        Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(i);
    }

    private class LoginTask extends AsyncTask<String, Void, Integer> {
        @Override
        protected void onPreExecute() {
            Log.i(TAG, "Start login at server...");
        }

        @Override
        protected void onPostExecute(Integer sessionId) {
            Log.i(TAG, "Login at server finished...");

            if (sessionId.equals(0)) {
                // TODO TOAST zeigen
            }else {
                Intent iMainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(iMainActivity);
            }
        }

        @Override
        protected Integer doInBackground(String... params) {
            int sessionId = 0;
            try {
                Log.i(TAG,"Server login successful");
                CookApplication cookApplication = (CookApplication) getApplication();
                IServer server = cookApplication.getServer();

                sessionId = server.login(params[0]);

                if(sessionId > 0) {
                    User user = server.getUserInfo(params[0]);
                    cookApplication.setSessionId(sessionId);
                    cookApplication.setLoggedInUser(user);
                }else {
                    cookApplication.setSessionId(0);
                    cookApplication.setLoggedInUser(null);
                }
            } catch (LoginFailedException e) {
                Log.e(TAG, e.getMessage());

            }  catch (Exception e) {
                Log.e(TAG, e.getMessage());
                e.printStackTrace();
            }

            return sessionId;
        }
    }
}
