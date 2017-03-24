package com.rihab.notificationact.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rihab.notificationact.R;
import com.rihab.notificationact.utils.CheckNetwork;
import com.rihab.notificationact.utils.Constants;
import com.rihab.notificationact.utils.SessionManager;
import com.rihab.notificationact.utils.Constants;
import com.rihab.notificationact.utils.ValidateUserInfo;

import static android.Manifest.permission.READ_CONTACTS;


/**
 * Created by Rihab on 20/03/2017.
 */


public class LoginActivity extends AppCompatActivity implements
        View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {
    public String token;
    private BroadcastReceiver mRegistrationBroadcastReceiver;


    SessionManager manager;

    public static final String LOGIN_URL = Constants.BASE_URL+"login.php";
    private static final String REGISTER_URL = Constants.BASE_URL+"inscription.php";


    public static final String KEY_USERNAME="username";
    public static final String KEY_PASSWORD="password";
    public static final String KEY_TOKEN = "token";
    private CreateUserTaskGoogle mCreateTaskGoogle = null;


    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    /* Request code used to invoke sign in user interactions. */
    private static final int RC_SIGN_IN = 0;
    /* Client used to interact with Google APIs. */

    /* Is there a ConnectionResult resolution in progress? */
    private boolean mIsResolving = false;
    /* Should we automatically resolve ConnectionResults when possible? */
    private boolean mShouldResolve = false;


    //private SignInButton mPlusSignInButton;
    private ImageView mPlusSignInButton;
    private Button mEmailSignInButton;

    private TextView txt_create, txt_forgot;
    //private LoginButton facebookLoginButton;
    private ImageView facebookLoginButton;

    ProgressDialog ringProgressDialog;
    private ProgressDialog pDialog;


    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        manager = new SessionManager();


        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);





        initInstances();
    }

    private void initInstances() {
        // Set up the login form.
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.txt_email);
        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.txt_password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        txt_create = (TextView) findViewById(R.id.txt_create);
        txt_create.setOnClickListener(this);

        txt_forgot = (TextView) findViewById(R.id.txt_forgot);
        txt_forgot.setOnClickListener(this);


        mEmailSignInButton.setOnClickListener(this);

        //Google+ Login
        //mPlusSignInButton = (SignInButton) findViewById(R.id.g_sign_in_button);

        //mPlusSignInButton.setSize(SignInButton.SIZE_WIDE);





        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.






        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }











    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    private void attemptLogin() {
        if (mAuthTask != null) {

            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        final String name = mEmailView.getText().toString();
        final String password = mPasswordView.getText().toString();

        final String username = mEmailView.getText().toString().trim();
        final String passworde = mPasswordView.getText().toString().trim();




        boolean cancel = false;
        View focusView = null;

        ValidateUserInfo validateUserInfo = new ValidateUserInfo();

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(name)) {
            mEmailView.setError("Veuillez saisir votre nom d'utilisateur");
            focusView = mEmailView;
            cancel = true;
        }else if (TextUtils.isEmpty(password)) {
            mPasswordView.setError("Veuillez saisir votre mot de passe");
            focusView = mPasswordView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {


            pDialog = new ProgressDialog(LoginActivity.this);
            // Showing progress dialog before making http request
            pDialog.setMessage(getResources().getString(R.string.tv_chargement));
            pDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            hidePDialog();
                            JSONObject root = null;
                            try {
                                root = new JSONObject(response);
                                String test = root.getString("code");


                                if(test.equals("200")){

                                    manager.setPreferences(LoginActivity.this, "id", root.getJSONObject("user").getString("idusermobile"));
                                    manager.setPreferences(LoginActivity.this, "nom", root.getJSONObject("user").getString("username"));
                                    manager.setPreferences(LoginActivity.this, "password", root.getJSONObject("user").getString("password"));
                                    manager.setPreferences(LoginActivity.this, "email", root.getJSONObject("user").getString("email"));
                                    manager.setPreferences(LoginActivity.this, "telephone", root.getJSONObject("user").getString("tel"));
                                    manager.setPreferences(LoginActivity.this, "type", "native");
                                    manager.setPreferences(LoginActivity.this, "status", "1");

                                    mEmailView.setText("");
                                    mPasswordView.setText("");


                                    Intent main = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(main);

                                }else Toast.makeText(LoginActivity.this,"Vérifiez vos cordonnées",Toast.LENGTH_LONG).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {


                    Toast.makeText(LoginActivity.this,"Erreur de connexion",Toast.LENGTH_LONG ).show();
                    hidePDialog();

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> map = new HashMap<String,String>();
                    map.put(KEY_USERNAME,username);
                    map.put(KEY_PASSWORD,passworde);
                    map.put(KEY_TOKEN,token);

                    return map;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);

        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }


    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        String email = mEmailView.getText().toString();

        switch (v.getId()) {

            case R.id.email_sign_in_button:
                attemptLogin();
                break;
            case R.id.txt_create:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                intent.putExtra(Constants.TAG_EMAIL, email);
                startActivity(intent);
                finish();
                break;
            case R.id.txt_forgot:
                Intent intentForgot = new Intent(LoginActivity.this, ForgotPassActivity.class);
                intentForgot.putExtra(Constants.TAG_EMAIL, email);
                startActivity(intentForgot);
                //finish();
                break;
        }
    }







    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        super.onActivityResult(requestCode, responseCode, intent);
        //Facebook login

    }
    // [END onActivityResult]

    // [START handleSignInResult]








    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }













}

