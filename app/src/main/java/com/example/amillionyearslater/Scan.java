package com.example.amillionyearslater;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Scan extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);


        EditText passcode = findViewById(R.id.et_code);


        Button code_submit = findViewById(R.id.btn_code_submit);
        code_submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final String password = "010702";
                final String code = passcode.getText().toString();
                Toast.makeText(Scan.this, "THIS IS FOR ADMINS ONLY!", Toast.LENGTH_LONG).show();
                if (code.equals(password)){
                    startActivity(new Intent(Scan.this, QRCodeScanner.class));
                    Toast.makeText(Scan.this, "YOU HAVE ENTERED AS ADMIN", Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    Toast.makeText(Scan.this, "Incorrect code! Try again!", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}