package com.example.andreiiorga.waiterapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.andreiiorga.waiterapp.AsyncTasks.AsynchronousHttpClient;
import com.example.andreiiorga.waiterapp.R;
import com.example.andreiiorga.waiterapp.staticUtils.StaticStrings;
import com.google.firebase.iid.FirebaseInstanceId;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private EditText serverURL;

    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.username);
        mPasswordView = (EditText) findViewById(R.id.password);
        serverURL = (EditText) findViewById(R.id.serverUrl);

        Button btnLogin = (Button) findViewById(R.id.email_sign_in_button);
        btnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mEmailView.getText().toString();
                String password = mPasswordView.getText().toString();
                StaticStrings.SERVER_ADDRESS = serverURL.getText().toString();

                login(username, password);

            }
        });


        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    private void login(String username, final String password) {
        AsynchronousHttpClient.get(StaticStrings.GET_WAITER_BY_USERNAME + username, null, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if(response.getString("password").equals(password)){
                        StaticStrings.WAITER_ID = response.getInt("id") +"";
                        StaticStrings.WAITERS_NAME = response.getString("name");
                        StaticStrings.FIREBASE_TOKEN = FirebaseInstanceId.getInstance().getToken();
                        StaticStrings.TABLE_ZONE = response.getInt("zoneID") + "";
                        sendToken();
                        Intent intent = new Intent(getBaseContext(), TasksActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });

    }

    private void sendToken() {
        RequestParams params = new RequestParams();
        params.put("id", StaticStrings.WAITER_ID);
        params.put("token", StaticStrings.FIREBASE_TOKEN);
        AsynchronousHttpClient.post(StaticStrings.SEND_TOKEN_ROUTE, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                System.out.println("Token SENT!");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("Error sending Token!");
            }
        });
    }
}

