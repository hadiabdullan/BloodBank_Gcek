package com.hadi123.www.bloodbankgcek;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hbb20.CountryCodePicker;

public class LoginActivity extends AppCompatActivity {
    CountryCodePicker ccp;
    FirebaseAuth auth;
    EditText user;
    EditText pass;
    FirebaseUser user1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ccp = (CountryCodePicker) findViewById(R.id.ccp);
         user = findViewById(R.id.username);
         pass = findViewById(R.id.password);
        auth=FirebaseAuth.getInstance();



    }

    public void login(View view){

        final String username = user.getText().toString();
        final String password = pass.getText().toString();

        boolean errorOccured = false;
            if ( username.isEmpty()) {
                user.setError("Invalid Email Id");
                errorOccured = true;
            }
            if (password.isEmpty()) {
                pass.setError("Invalid password");
                errorOccured = true;
            }

        if (errorOccured) {
            return;
        }

        userSignIn();

    }
    public void register(View view){
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    private void userSignIn(){
        final String username = user.getText().toString();
        final String password = pass.getText().toString();
        if(username.equals("admin@gmail.com") && password.equals("admin123")) {
            auth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "User Logged in Succesfully", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getApplicationContext(), "User could not be logged In", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }
        else


            auth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "User Logged in Succesfully", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getApplicationContext(), "User could not be logged In", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

    }



}
