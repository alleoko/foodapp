package com.example.finaljjkitchen.Server;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.finaljjkitchen.R;

public class MainActivityServer extends AppCompatActivity implements View.OnClickListener {
    Button btnSignIn;
    TextView textSlogan;
    private boolean isSinglePressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_server);

        btnSignIn = findViewById(R.id.btnSignIn);

//        textSlogan = findViewById(R.id.txtSlogan);
//        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/NABILA.TTF");
//        textSlogan.setTypeface(typeface);

        btnSignIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnSignIn)
        {
            Intent intent = new Intent(MainActivityServer.this, com.example.finaljjkitchen.Server.SignInServer.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
