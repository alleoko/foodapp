package com.example.finaljjkitchen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private Button CreateAccountButton, Signin;
    private EditText InputName, InputPhoneNumber, InputPassword, InputAddress, InputEmAdd, InputVerify, newUsers;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        CreateAccountButton = (Button) findViewById(R.id.register_btn);

        InputName = (EditText) findViewById(R.id.register_username_input);
        InputPassword = (EditText) findViewById(R.id.register_password_input);
        InputPhoneNumber = (EditText) findViewById(R.id.register_phone_number_input);
        InputAddress = (EditText) findViewById(R.id.register_address);
        InputVerify = (EditText) findViewById(R.id.register_passwordverify_input);
        InputEmAdd = (EditText) findViewById(R.id.register_email_input);
        Signin = (Button) findViewById(R.id.btn_logIn);

        loadingBar = new ProgressDialog(this);

        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(com.example.finaljjkitchen.RegisterActivity.this, SignIn.class);
                startActivity(intent);
            }
        });
        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount();
            }
        });
    }

    private void CreateAccount() {
        String name = InputName.getText().toString();
        String phone = InputPhoneNumber.getText().toString();
        String password = InputPassword.getText().toString();
        String address = InputAddress.getText().toString();
        String verifyPass = InputVerify.getText().toString();
        String emadd = InputEmAdd.getText().toString();


        if (TextUtils.isEmpty(name)) {
            //  Toast.makeText(this, "Please write your name...", Toast.LENGTH_SHORT).show();
            InputPassword.setError("Please write your name");
        } else if (!phone.matches("[5-9]{1}[0-9]{9}$")) {
        //    Toast.makeText(this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
            InputPhoneNumber.setError("Invalid Phone Number");
        } else if (TextUtils.isEmpty(password)|| password.length()<8) {
            InputPassword.setError("Invalid Password. Must be 8 characters ");
            // Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(verifyPass)) {
            InputPassword.setError("Please write your password");
            //Toast.makeText(this, "Password is not match...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(emadd)) {
            InputEmAdd.setError("Please write your email");
            //   Toast.makeText(this, "Please write your email...", Toast.LENGTH_SHORT).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emadd).matches()) {
            InputEmAdd.setError("Invalid Email Address");
            //       Toast.makeText(this, "Invalid email...", Toast.LENGTH_SHORT).show();
        } else if (!password.equals(verifyPass)) {
            Toast.makeText(this, "Password not match...", Toast.LENGTH_SHORT).show();
            InputVerify.setError("Did not match");
        } else {
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidatephoneNumber(name, phone, password, address, emadd);
        }

    }

    private void ValidatephoneNumber(final String name, final String phone, final String password, final String address, final String emadd) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.child("User").child(phone).exists())) {


                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("phone", phone);
                    userdataMap.put("password", password);
                    userdataMap.put("name", name);
                    userdataMap.put("address", address);
                    userdataMap.put("emadd", emadd);
                    userdataMap.put("isStaff", "false");
                    RootRef.child("User").child(phone).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(com.example.finaljjkitchen.RegisterActivity.this, "Congratulations, your account has been created.", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Intent intent = new Intent(com.example.finaljjkitchen.RegisterActivity.this, SignIn.class);
                                startActivity(intent);
                            } else {
                                loadingBar.dismiss();
                                Toast.makeText(com.example.finaljjkitchen.RegisterActivity.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                } else {
                    Toast.makeText(com.example.finaljjkitchen.RegisterActivity.this, "This " + phone + " already exists.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(com.example.finaljjkitchen.RegisterActivity.this, "Please try again using another phone number.", Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}