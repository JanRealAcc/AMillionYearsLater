package com.example.amillionyearslater;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.TRANSPARENT;
import static android.graphics.Color.WHITE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.Locale;

public class StartActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        TextView name = findViewById(R.id.name_here);
        TextView contact = findViewById(R.id.contact_here);
        TextView address = findViewById(R.id.address_here);
        TextView city = findViewById(R.id.city_here);
        TextView gender = findViewById(R.id.gender_here);
        TextView age = findViewById(R.id.age_here);
        TextView birthdate = findViewById(R.id.bday_here);
        TextView studId = findViewById(R.id.sid_here);
        TextView course = findViewById(R.id.course_here);
        TextView year = findViewById(R.id.year_here);
        TextView vaccine = findViewById(R.id.vaccine_here);
        TextView dose = findViewById(R.id.dose_here);
        TextView welcome = findViewById(R.id.welcomeText);
        TextView qrCODE_code = findViewById(R.id.qrcode_code);
        ImageView qrCODE_image = findViewById(R.id.user_QRCODE);



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://amillionyearslater-7935e-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users").child(userId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String userId = snapshot.child("userId").getValue(String.class);
                String firstNameU = snapshot.child("firstName").getValue(String.class);
                String lastNameU = snapshot.child("lastName").getValue(String.class);
                String phoneNumberU = snapshot.child("phoneNumber").getValue(String.class);
                String ageU = snapshot.child("age").getValue(String.class);
                String addressU = snapshot.child("address").getValue(String.class);
                String cityU = snapshot.child("city").getValue(String.class);
                String genderU = snapshot.child("gender").getValue(String.class);
                String emailU = snapshot.child("email").getValue(String.class);
                String courseU = snapshot.child("course").getValue(String.class);
                String schoolIdU = snapshot.child("schoolId").getValue(String.class);
                String vaccineU = snapshot.child("vaccine").getValue(String.class);
                String vaccineDosageU = snapshot.child("vaccineDosage").getValue(String.class);
                String birthDateU = snapshot.child("birthDate").getValue(String.class);
                String yearLevelU = snapshot.child("yearLevel").getValue(String.class);


                String fullName = firstNameU + " " + lastNameU;
                String fullContact = phoneNumberU + " | " + emailU;
                String welcomeU = "Welcome " + firstNameU + "!";
                name.setText(fullName);
                contact.setText(fullContact);
                address.setText(addressU);
                city.setText(cityU);
                gender.setText(genderU);
                age.setText(ageU);
                birthdate.setText(birthDateU);
                studId.setText(schoolIdU);
                course.setText(courseU);
                year.setText(yearLevelU);
                vaccine.setText(vaccineU);
                dose.setText(vaccineDosageU);
                welcome.setText(welcomeU);
                qrCODE_code.setText(userId);


                String fullQrCode = userId + schoolIdU + firstNameU.toUpperCase() + lastNameU.toUpperCase();
                Bitmap qrCode = createBitmap(fullQrCode);
                qrCODE_image.setImageBitmap(qrCode);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    private Bitmap createBitmap(String userId) {
        BitMatrix result = null;
        try {
            result = new MultiFormatWriter().encode(userId, BarcodeFormat.QR_CODE, 1000,1000,null);
        } catch (WriterException e){
            e.printStackTrace();
            return null;
        }
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int x=0; x<height; x++){
            int offset= x * width;
            for (int k=0; k<width; k++){
                pixels[offset + k] = result.get(k, x) ? BLACK : TRANSPARENT;
            }
        }
        Bitmap myBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        myBitmap.setPixels(pixels, 0, width,0,0, width, height);
        return myBitmap;
    }
}



