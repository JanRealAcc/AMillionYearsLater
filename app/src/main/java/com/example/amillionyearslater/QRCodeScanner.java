package com.example.amillionyearslater;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class QRCodeScanner extends AppCompatActivity {
    //Initialize variable
    private TextView user_id;
    private EditText value;
    private String text_value, text_time, name_time;
    private SimpleDateFormat simpleDateFormat, nameDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_scanner);

        //Assign variable
        Button btScan = findViewById(R.id.bt_scan);
        Button btLog = findViewById(R.id.bt_logbook);
        user_id = findViewById(R.id.id_here);
        value = findViewById(R.id.value);

        btLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QRCodeScanner.this, LogBookList.class));
            }
        });


        btScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Initialize intent integrator
                IntentIntegrator intentIntegrator= new IntentIntegrator
                        (
                                QRCodeScanner.this
                        );
                //Set prompt text
                intentIntegrator.setPrompt("For flash use volume up key");
                //Set beep
                intentIntegrator.setBeepEnabled(true);
                //Locked orientation
                intentIntegrator.setOrientationLocked(true);
                //Set capture activity
                intentIntegrator.setCaptureActivity(Capture.class);
                //Initiate scan
                intentIntegrator.initiateScan();
            }
        });
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Initialize intent result
        IntentResult intentResult = IntentIntegrator.parseActivityResult(
                requestCode,resultCode,data
        );
        //Check condition
        if(intentResult.getContents() !=null) {
            //When result content is not null
            //Initialize alert dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    QRCodeScanner.this
            );
            //Set title
            builder.setTitle("Result");
            //Set message
            builder.setMessage(intentResult.getContents());

            if(intentResult.getContents() != null){
                Intent intent = new Intent(QRCodeScanner.this, Status.class);
                text_value = intentResult.getContents();
                Calendar dateCurrent = Calendar.getInstance();
                simpleDateFormat = new SimpleDateFormat("E, MMM dd yyyy hh:mm:ssa");
                text_time = simpleDateFormat.format(dateCurrent.getTime());
                intent.putExtra("Time_scan", text_time);
                intent.putExtra("QR_scan", text_value);
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(getApplicationContext(),"ERROR!", Toast.LENGTH_SHORT).show();
            }

            //Show alert dialog
            builder.show();
        }else {
            //When result content is null
            //Display toast
            Toast.makeText(getApplicationContext()
                    ,"OOPS... You did not scan anything", Toast.LENGTH_SHORT).show();
        }


    }
}