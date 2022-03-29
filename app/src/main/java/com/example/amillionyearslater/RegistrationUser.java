package com.example.amillionyearslater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

public class RegistrationUser extends AppCompatActivity {

    private TextView banner;
    private EditText editFNAME, editLNAME, editMNAME, editEMAIL, editPHONE, editPASSWORD, editAGE, editADDRESS, editSCHOOLID;
    private EditText edit_birthdate;
    private ProgressBar progressLoading;
    private Button bDate;
    private DatabaseReference databaseReference;
    private DatePickerDialog dateDialog;
    private FirebaseDatabase database;


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_user);

        mAuth = FirebaseAuth.getInstance();



        //municipality selection
        Spinner spinnerCities = (Spinner) findViewById(R.id.spin_city);
        ArrayAdapter<CharSequence> adapterCities = ArrayAdapter.createFromResource(this, R.array.municipality, android.R.layout.simple_spinner_item);
        adapterCities.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCities.setAdapter(adapterCities);

        //course selection
        Spinner spinnerCourse = (Spinner) findViewById(R.id.spin_course);
        ArrayAdapter<CharSequence> adapterCourse = ArrayAdapter.createFromResource(this, R.array.course, android.R.layout.simple_spinner_item);
        adapterCourse.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCourse.setAdapter(adapterCourse);

        //year level selection
        Spinner spinnerYearLevel = (Spinner) findViewById(R.id.spin_yearLevel);
        ArrayAdapter<CharSequence> adapterYearLevel = ArrayAdapter.createFromResource(this, R.array.yearLevel, android.R.layout.simple_spinner_item);
        adapterYearLevel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYearLevel.setAdapter(adapterYearLevel);

        //vaccine selection
        Spinner spinnerVaccines = (Spinner) findViewById(R.id.spin_vaccine);
        ArrayAdapter<CharSequence> adapterVaccines = ArrayAdapter.createFromResource(this, R.array.vaccines, android.R.layout.simple_spinner_item);
        adapterVaccines.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVaccines.setAdapter(adapterVaccines);


        TextView banner = (TextView) findViewById(R.id.banner);
        banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationUser.this, MainActivity.class));

            }
        });


        //ASSIGN ID to VARIABLES.
        editFNAME = (EditText) findViewById(R.id.et_fName);
        editMNAME = (EditText) findViewById(R.id.et_mName);
        editLNAME = (EditText) findViewById(R.id.et_lName);
        editEMAIL = (EditText) findViewById(R.id.et_email);
        editPHONE = (EditText) findViewById(R.id.et_phone);
        editPASSWORD = (EditText) findViewById(R.id.et_password);
        editAGE = (EditText) findViewById(R.id.et_age);
        editAGE.setEnabled(false);
        editADDRESS = (EditText) findViewById(R.id.et_address);
        editSCHOOLID = (EditText) findViewById(R.id.et_schoolID);
        progressLoading = (ProgressBar) findViewById(R.id.loading);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_gender);
        RadioGroup radioGroup1 = (RadioGroup) findViewById(R.id.radio_dose);
        Button buttonSUBMIT = (Button) findViewById(R.id.btn_submit);

        bDate = (Button) findViewById(R.id.btn_bDate);
        edit_birthdate = (EditText) findViewById(R.id.bday);
        bDate();

        buttonSUBMIT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String firstname = editFNAME.getText().toString().trim();
                final String middlename = editMNAME.getText().toString().trim();
                final String lastname = editLNAME.getText().toString().trim();
                final String u_email = editEMAIL.getText().toString().trim();
                final String phoneNumber = editPHONE.getText().toString().trim();
                final String u_password = editPASSWORD.getText().toString().trim();
                final String age = editAGE.getText().toString().trim();
                final String u_address = editADDRESS.getText().toString().trim();
                final String schoolId = editSCHOOLID.getText().toString().trim();
                int checkedGENDER = radioGroup.getCheckedRadioButtonId();
                int checkedDOSE = radioGroup1.getCheckedRadioButtonId();
                RadioButton selectedGENDER = radioGroup.findViewById(checkedGENDER);
                RadioButton selectedDOSE = radioGroup1.findViewById(checkedDOSE);
                String city = spinnerCities.getSelectedItem().toString();
                String course = spinnerCourse.getSelectedItem().toString();
                String year_level = spinnerYearLevel.getSelectedItem().toString();
                String vaccine = spinnerVaccines.getSelectedItem().toString();
                final String birthdate = edit_birthdate.getText().toString();

                if (TextUtils.isEmpty(firstname)){
                    Toast.makeText(RegistrationUser.this, "Please fill in your firstname!", Toast.LENGTH_LONG).show();
                }else if (TextUtils.isEmpty(middlename)){
                    Toast.makeText(RegistrationUser.this, "Please fill in your middlename!", Toast.LENGTH_LONG).show();
                }else if (TextUtils.isEmpty(lastname)) {
                    Toast.makeText(RegistrationUser.this, "Please fill in your lastname!", Toast.LENGTH_LONG).show();
                }else if (TextUtils.isEmpty(u_email)) {
                    Toast.makeText(RegistrationUser.this, "Please fill in your email!", Toast.LENGTH_LONG).show();
                }else if (TextUtils.isEmpty(phoneNumber)) {
                    Toast.makeText(RegistrationUser.this, "Please fill in your phone number!", Toast.LENGTH_LONG).show();
                }else if (TextUtils.isEmpty(u_password)) {
                    Toast.makeText(RegistrationUser.this, "Please fill in your password!", Toast.LENGTH_LONG).show();
                }else if (TextUtils.isEmpty(age)) {
                    Toast.makeText(RegistrationUser.this, "Please select your birthdate!", Toast.LENGTH_LONG).show();
                }else if (selectedGENDER == null) {
                    Toast.makeText(RegistrationUser.this, "Please select your gender!", Toast.LENGTH_LONG).show();
                }else if (TextUtils.isEmpty(u_address)) {
                    Toast.makeText(RegistrationUser.this, "Please fill in your address!", Toast.LENGTH_LONG).show();
                }else if (city.equals("SELECT CITY")) {
                    Toast.makeText(RegistrationUser.this, "Please select your municipality!", Toast.LENGTH_LONG).show();
                }else if (TextUtils.isEmpty(schoolId)) {
                    Toast.makeText(RegistrationUser.this, "Please fill in your school ID number!", Toast.LENGTH_LONG).show();
                }else if (course.equals("SELECT COURSE")) {
                    Toast.makeText(RegistrationUser.this, "Please select your course!", Toast.LENGTH_LONG).show();
                }else if (year_level.equals("SELECT YEAR")) {
                    Toast.makeText(RegistrationUser.this, "Please select your year level!", Toast.LENGTH_LONG).show();
                }else if (vaccine.equals("SELECT VACCINE")) {
                    Toast.makeText(RegistrationUser.this, "Please select your vaccine!", Toast.LENGTH_LONG).show();
                }else if (selectedDOSE == null) {
                    Toast.makeText(RegistrationUser.this, "Please select your number of dose!", Toast.LENGTH_LONG).show();
                } else {
                    final String dose = selectedDOSE.getText().toString();
                    final String gender = selectedGENDER.getText().toString();
                    registerStudent(firstname, middlename, lastname, u_email, phoneNumber, u_password, age, u_address, schoolId, gender, dose, city, course,
                                year_level, vaccine, birthdate);
                    }
                }


            });



    }

    private void bDate() {
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        bDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dateDialog = new DatePickerDialog(view.getContext(), datePickerListener, year, month, day);
                dateDialog.getDatePicker().setMaxDate(new Date().getTime());
                dateDialog.show();
            }
        });
    }
    private final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, day);
            editAGE.setText(Integer.toString(calculateAge(c.getTimeInMillis())));
            month = month+1;
            String birth = month+"/"+day+"/"+year;
                        /*
                          ANG KANING 'bDate_Button.setHint(birthdate)' DARI MA CHANGE TONG TEXT
                          FROM "BUTTON NGA WORD" TO "ILANG BIRTHDATE"
                        */
            edit_birthdate.setText(birth);
            bDate.setText(birth);

        }
    };
    int calculateAge(long date){
        Calendar dob = Calendar.getInstance();
        dob.setTimeInMillis(date);

        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if(today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)){
            age--;
        }
        return age;
    }


    private void registerStudent(String fName, String mName, String lName, String email, String phone, String password, String age, String address,
                                 String schoolId, String gender, String dose, String city, String course, String year_level, String vaccine,
                                 String birthdate) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser rUser = mAuth.getCurrentUser();
                    assert rUser != null;
                    String userId = rUser.getUid();
                    databaseReference = FirebaseDatabase.getInstance("https://amillionyearslater-7935e-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users").child(userId);
                    HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put("userId",userId);
                    hashMap.put("firstName",fName);
                    hashMap.put("middleName",mName);
                    hashMap.put("lastName",lName);
                    hashMap.put("phoneNumber",phone);
                    hashMap.put("age",age);
                    hashMap.put("address",address);
                    hashMap.put("city",city);
                    hashMap.put("gender",gender);
                    hashMap.put("email",email);
                    hashMap.put("password",password);
                    hashMap.put("course",course);
                    hashMap.put("yearLevel",year_level);
                    hashMap.put("schoolId",schoolId);
                    hashMap.put("vaccine",vaccine);
                    hashMap.put("vaccineDosage",dose);
                    hashMap.put("birthDate", birthdate);
                    hashMap.put("imageURL","default");
                    databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Intent intent = new Intent(RegistrationUser.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }else{
                                Toast.makeText(RegistrationUser.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    progressLoading.setVisibility(View.GONE);
                    Toast.makeText(RegistrationUser.this, Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

