package com.example.amillionyearslater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
        TextView welcome = findViewById(R.id.textView);
        ImageView qrCODE = findViewById(R.id.user_QRCODE);


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

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
}



