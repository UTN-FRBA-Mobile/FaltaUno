package com.faltauno.faltauno;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.BundleCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

/**
 * Created by jonathan on 13/05/17.
 */

public class FaltaUnoApp extends AppCompatActivity {

    private LoginButton loginButton;
    private TextView textView;
    private CallbackManager callbackManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.login_app);
        loginButton = (LoginButton) findViewById(R.id.fb_login_btn);
        textView = (TextView) findViewById(R.id.login_status);

        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                irAlMain();

                textView.setText("Login sucess \n" +
                loginResult.getAccessToken().getUserId() +
                "\n" + loginResult.getAccessToken().getToken()
                );

            }

            @Override
            public void onCancel() {

                textView.setText("Login cancelled");

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }
    private void irAlMain() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);


    }
}
