package com.example.finaljjkitchen.forgot;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.finaljjkitchen.Model.Prevalent;
import com.example.finaljjkitchen.Model.UsersForgotModel;
import com.example.finaljjkitchen.R;
import com.example.finaljjkitchen.SignIn;

//import androidx.appcompat.app.AppCompatActivity;
//import androidx.annotation.NonNull;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText inputPhone;
    TextView inputMessage, inputEmail;
    Button submit, submit2;
    String user = "jamesjacobkitchen@gmail.com";
    String password = "1234abcd!@#$";
    String PHONE, EMAIL, MESSAGE, SUBJECT;
    TextView txtSubject;
    private ProgressDialog loadingBar;
    String email_message;
    private String parentDbName = "User";
    String emadd;
    com.example.finaljjkitchen.forgot.GMailSender sender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        loadingBar = new ProgressDialog(this);
        inputPhone = (EditText) findViewById(R.id.subject);
        inputMessage = (TextView) findViewById(R.id.body);
        inputEmail = (TextView) findViewById(R.id.recipient);
        submit = (Button) findViewById(R.id.submit);
        submit2 = (Button) findViewById(R.id.submit2);
        txtSubject = (TextView) findViewById(R.id.txtSub);

        sender = new com.example.finaljjkitchen.forgot.GMailSender(user, password);


        submit.setVisibility(View.GONE);
        submit.setVisibility(View.INVISIBLE);


       ImageView backBtn = findViewById(R.id.backbtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(CartActivity.this, "Cart is empty", Toast.LENGTH_SHORT).show();

              finish();

            }
        });



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                PHONE = inputPhone.getText().toString();
                MESSAGE = inputMessage.getText().toString();
                EMAIL = inputEmail.getText().toString();
                SUBJECT = inputEmail.getText().toString();
                new MyAsyncClass().execute();

                submit.setVisibility(View.INVISIBLE);

            }
        });

        ///////////////////////////////
        submit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PHONE = inputPhone.getText().toString();
                MESSAGE = inputMessage.getText().toString();
                EMAIL = inputEmail.getText().toString();
                SUBJECT = inputEmail.getText().toString();


                if (TextUtils.isEmpty(PHONE)) {
                    Toast.makeText(com.example.finaljjkitchen.forgot.ForgotPasswordActivity.this, PHONE, Toast.LENGTH_LONG).show();

                    //        inputPhone.setError("Please write your password");
                }else {


                    SendMail();
                    inputEmail.setVisibility(View.VISIBLE);
                    inputMessage.setVisibility(View.VISIBLE);
                }
            }
        });

//////////////////////////////////


    }


    class MyAsyncClass extends AsyncTask<Void, Void, Void> {

        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            pDialog = new ProgressDialog(com.example.finaljjkitchen.forgot.ForgotPasswordActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.show();

        }

        @Override

        protected Void doInBackground(Void... mApi) {
            try {

                // Add subject, Body, your mail Id, and receiver mail Id.
                sender.sendMail("JJ Password Recovery", email_message, user, emadd);
                Log.d("send", "done");
            } catch (Exception ex) {
                Log.d("exceptionsending", ex.toString());
            }
            return null;
        }

        @Override

        protected void onPostExecute(Void result) {

            super.onPostExecute(result);
            pDialog.cancel();

            Toast.makeText(com.example.finaljjkitchen.forgot.ForgotPasswordActivity.this, "Mail was sent to your email", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(com.example.finaljjkitchen.forgot.ForgotPasswordActivity.this, SignIn.class);
            startActivity(intent);
        }
    }

    public void SendMail() {


        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String phone = inputPhone.getText().toString();


                if (dataSnapshot.child(parentDbName).child(phone).exists()) {

                    UsersForgotModel usersData = dataSnapshot.child(parentDbName).child(phone).getValue(UsersForgotModel.class);
                    if (usersData.getPhone().equals(phone)) {

///////////////////////////////////////// DITO KA MAGCODE FOR SEND PASSWORD TO EMAIL
                        Prevalent.currentOnlineUser = usersData;
                        emadd = usersData.getEmadd();
                        String password = usersData.getPassword();
                        String tel = usersData.getPhone();

                        inputEmail.setText("registered email. Thank you");
                        inputMessage.setText("Your details will be send to your");
                        email_message =
                                "You requested for password recovery of this phone " +
                                        tel +
                                        " which has a password: " +
                                        password;

                        Toast.makeText(com.example.finaljjkitchen.forgot.ForgotPasswordActivity.this, "email add" + emadd, Toast.LENGTH_SHORT).show();

                        loadingBar.dismiss();
                        submit2.setVisibility(View.GONE);
                        submit2.setVisibility(View.INVISIBLE);

                        submit.setVisibility(View.VISIBLE);


                    } else{

                        Toast.makeText(com.example.finaljjkitchen.forgot.ForgotPasswordActivity.this, "Account with this " + phone + " email do not exists.", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(com.example.finaljjkitchen.forgot.ForgotPasswordActivity.this, "Account with this " + phone + " number do not exists.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

}
