package com.misd.cookapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.misd.cookapp.exceptions.LoginFailedException;
import com.misd.cookapp.exceptions.ServerCommunicationException;
import com.misd.cookapp.helpers.HelperMethods;

import org.ksoap2.SoapFault;

import java.io.InputStream;
import java.math.BigInteger;

import javax.security.auth.login.LoginException;

import static com.misd.cookapp.helpers.HelperMethods.pasteCalendar;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    private static final String SERVER_CLIENT_ID = "server_client_id";
    private static final String SESSION_ID_KEY = "session_id_key";

    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInAccount acct;
    private TextView mStatusTextView;
    private ProgressDialog mProgressDialog;
    private String iExtra;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        // Views
        mStatusTextView = (TextView) findViewById(R.id.status);

        //Get IntentExtra flag
        i = getIntent();
        iExtra = i.getStringExtra(MainActivity.START_LOGOUT);

        // Button listeners
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);

        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                //.requestScopes(new Scope(Scopes.PROFILE))
                .requestEmail()
                //.requestIdToken(SERVER_CLIENT_ID) Wird für die kommunikation mit dem Server gebraucht. Erfordert das hizufügen weiterer rechte
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
// options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addConnectionCallbacks(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        // [START customize_button]
        // Customize sign-in button. The sign-in button can be displayed in
        // multiple sizes and color schemes. It can also be contextually
        // rendered based on the requested scopes. For example. a red button may
        // be displayed when Google+ scopes are requested, but a white button
        // may be displayed when only basic profile is requested. Try adding the
        // Scopes.PLUS_LOGIN scope to the GoogleSignInOptions to see the
        // difference.
        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setScopes(gso.getScopeArray());
        // [END customize_button]

        Intent i = getIntent();
        String iExtra = i.getStringExtra(MainActivity.START_LOGOUT);
        if (iExtra != null && iExtra.equals(MainActivity.START_LOGOUT)) {
            Log.d(TAG, "GoogleApiClient.connect() is called...");
            mGoogleApiClient.connect();
        }

    }

    @Override
    public void onStart() {
        Log.d(TAG, "LoginActivity.onStart() is called...");
        super.onStart();
        Log.d(TAG, "GoogleApiClient.connect() is called...");
        mGoogleApiClient.connect();



            OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
            if (opr.isDone()) {
                Log.d(TAG, "SilentSignIn was successful.");
                // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
                // and the GoogleSignInResult will be available instantly.
                GoogleSignInResult result = opr.get();
                if (iExtra == null || !iExtra.equals(MainActivity.START_LOGOUT)) {
                    handleSignInResult(result);
                }

            } else {
                // If the user has not previously signed in on this device or the sign-in has expired,
                // this asynchronous branch will attempt to sign in the user silently.  Cross-device
                // single sign-on will occur in this branch.
                showProgressDialog();
                opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                    @Override
                    public void onResult(GoogleSignInResult googleSignInResult) {
                        hideProgressDialog();
                        handleSignInResult(googleSignInResult);
                    }
                });
            }

    }

    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onConnected(Bundle arg0) {
        Log.d(TAG, "GoogleApiClient is connected.");
        if (iExtra != null && iExtra.equals(MainActivity.START_LOGOUT)) {
            signOut();
        }
    }

    @Override
    public void onConnectionSuspended(final int i) {
        Log.d(TAG, "GoogleApiClient is suspended.");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.getStatus().getStatusMessage() != null) {
            Log.d(TAG, result.getStatus().getStatusMessage().toString());
        }
        Log.d(TAG, result.getStatus().toString());

        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            acct = result.getSignInAccount();
            HelperMethods.setPreferenceObject(this, HelperMethods.PREFS_USER_DATA, "GoogleSignInAccount", acct);
            loginAtServer();
        } else {
            updateUi(false);
        }
    }

    private void loginAtServer() {
        new LoginTask().execute();
    }

    private void updateUi(final boolean b) {
        if(b) {

            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        } else {
            Log.w(TAG, "Der Anmeldevorgang war nicht erfolgreich.");
            //Hier einfügen was passieren soll, wenn der user nicht eingeloogt wurde
            View parentLayout = findViewById(R.id.login_layout);
            Snackbar snackbar = Snackbar
                    .make(parentLayout, "Sie konnten leider nicht angemeldet werden. Versuchen Sie es erneut!", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    // [START signOut]
    public void signOut() {
        Log.d(TAG, "User sign out started....");
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        // Hier ienfügen was passieren soll, wenn der uSer sich ausgeloggt hat
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END signOut]

    // [START revokeAccess]
    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        // Hier einfügen was passieren soll nach demm der Zugriff entzogen wurde
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END revokeAccess]

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.sign_out_button:
                signOut();
                break;
        }
    }


    private class LoginTask extends AsyncTask<Void, Void, Void> {

        public LoginTask() {

        }

        @Override
        protected void onPreExecute() {
            Log.d(TAG, "Start login at server...");
            showProgressDialog();
        }


        @Override
        protected Void doInBackground(Void... params) {
            try {
                int sessionId = Server.login(new BigInteger(acct.getId()));
                Log.d(TAG,"Server login successful");
                HelperMethods.setPreferenceInt(getApplicationContext(),HelperMethods.PREFS_SESSION_DATA, SESSION_ID_KEY,sessionId);
                updateUi(true);
            } catch (LoginFailedException ex) {
                Log.d(TAG, ex.getMessage());
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            } catch (ServerCommunicationException sce) {
                Log.e(TAG,sce.getMessage());
                updateUi(false);
            } catch (SoapFault sf) {
                Log.e(TAG,"SoapAction failed: " + sf.getMessage());
                sf.printStackTrace();
                updateUi(false);
            } catch (Exception e) {
                //Log.e(TAG, e.getMessage());
                e.printStackTrace();
                updateUi(false);
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void params) {
            hideProgressDialog();
        }
    }
}
