package com.faltauno.faltauno;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.BundleCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by jonathan on 13/05/17.
 */

public class Login_App extends AppCompatActivity implements View.OnClickListener {

    private LoginButton loginButton;
    private TextView textView;
    private CallbackManager callbackManager;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText etPass;
    private EditText etEmail;
    private final String TAG = "FB_SIGNIN";


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


        setContentView(R.layout.login_app);
        loginButton = (LoginButton) findViewById(R.id.fb_login_btn);
        findViewById(R.id.btnSignIn).setOnClickListener(this);
        findViewById(R.id.btnCreate).setOnClickListener(this);

        etEmail = (EditText)findViewById(R.id.etEmailAddr);
        etPass = (EditText)findViewById(R.id.etPassword);



        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user != null) {
                        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("usuarios/"+user.getUid());
                        ref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                // cuando se loguea obtengo los datos del usuario

                                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                                if (usuario == null) {
                                    usuario = new Usuario("","");
                                    ref.setValue(usuario);
                                }
                                SharedPreferences pref = getApplicationContext().getSharedPreferences("USUARIO_DATOS", MODE_PRIVATE);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("nombre",usuario.getNombre());
                                editor.putString("usuario",usuario.getUsuario());
                                editor.commit();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                System.out.println("The read failed: " + databaseError.getCode());
                            }
                        });

                        irAlMain();

                    } else {
                        Log.d(TAG, "Currently Signed Out");
                    }

            }
        };

        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
//                irAlMain();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), R.string.cancel_login, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(),R.string.mensaje_error,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleFacebookAccessToken(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), R.string.firebase_error_login, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }
    private void irAlMain() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignIn:
                signUserIn();
                break;

            case R.id.btnCreate:
                createUserAccount();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    private void signUserIn() {
        if (!checkFormFields())  return;
        String email = etEmail.getText().toString();
        String password = etPass.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Login_App.this, "Usuario logueado", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Login_App.this, "Logueo fallido", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(Login_App.this, "Invalid password.", Toast.LENGTH_SHORT).show();
                        }
                        else if (e instanceof FirebaseAuthInvalidUserException) {
                            Toast.makeText(Login_App.this, "No account with this email.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(Login_App.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private boolean checkFormFields() {
        String email, password;

        email = etEmail.getText().toString();
        password = etPass.getText().toString();

        if (email.isEmpty()) {
            etEmail.setError("Email Required");
            return false;
        }
        if (password.isEmpty()){
            etPass.setError("Password Required");
            return false;
        }

        return true;
    }
    private void createUserAccount() {
        if (!checkFormFields())
            return;

        String email = etEmail.getText().toString();
        String password = etPass.getText().toString();


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Login_App.this, "User created", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Login_App.this, "Account creation failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, e.toString());
                        if (e instanceof FirebaseAuthUserCollisionException) {
                            Toast.makeText(Login_App.this, "This email address is already in use.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(Login_App.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
