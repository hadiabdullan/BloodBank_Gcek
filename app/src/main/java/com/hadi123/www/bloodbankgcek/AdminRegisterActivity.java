package com.hadi123.www.bloodbankgcek;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AdminRegisterActivity extends AppCompatActivity {
    EditText name,pass,mob,ht,wt,add,yj,emailid1;
    TextView dob,don;
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference reff;
    Spinner genderText;
    Spinner deptText;
    Spinner donText;
    DatePickerDialog dpd,dpd1;
    Calendar c;
    ImageButton mBtn;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);

        name=findViewById(R.id.name);
        pass=findViewById(R.id.passwordcreate);
        dob=findViewById(R.id.dob1);
        mob=findViewById(R.id.mobilenumber);
        ht=findViewById(R.id.height);
        wt=findViewById(R.id.weight);
        don=findViewById(R.id.donation);
        add=findViewById(R.id.address);
        yj=findViewById(R.id.year);
        emailid1=findViewById(R.id.emailuser);
        mBtn=findViewById(R.id.imageButton);



        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        reff= FirebaseDatabase.getInstance().getReference().getRoot();


        genderText=findViewById(R.id.gender);
        String[] GenderArray = {"Select blood","A+","A-","B+","B-","O+","O-","AB+","AB-"};

        ArrayAdapter<String> genderadapter= new ArrayAdapter<String>(this,R.layout.gender_spinner_row,R.id.gender,GenderArray);
        genderText.setAdapter(genderadapter);
        genderText.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View arg1, int position, long id) {
                String gender = String.valueOf(genderText.getSelectedItem());
                if (position==0){
                    genderText.setSelection(1);
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        deptText=findViewById(R.id.dept);
        String[] deptArray={"Select Department","ECE-A","ECE-B","CSE","Civil","Mechanical","EEE"};
        ArrayAdapter<String> deptadapter=new ArrayAdapter<String>(this,R.layout.department_spinner_row,R.id.dept,deptArray);
        deptText.setAdapter(deptadapter);
        deptText.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String dept=String.valueOf(deptText.getSelectedItem());
                if (position==0){
                    deptText.setSelection(1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        donText=findViewById(R.id.donation_select_list);
        String[] donArray={"Never","Add Date.."};
        ArrayAdapter<String> donadapter= new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,donArray);
        donText.setAdapter(donadapter);
        donText.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String donat=String.valueOf(donText.getSelectedItem());
                String donater=donText.getSelectedItem().toString();
                if(position==0){
                    donText.setSelection(0);
                    don.setText(donater);
                }

                if(donater.equals("Add Date..")){
                    Calendar c = Calendar.getInstance();
                    int currentyear = c.get(Calendar.YEAR);
                    int currentMonth = c.get(Calendar.MONTH);
                    int currentDay = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(AdminRegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                            Calendar c = Calendar.getInstance();
                            c.set(Calendar.YEAR, year);
                            c.set(Calendar.MONTH, monthOfYear);
                            c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            String formattedDate = df.format(c.getTime());
                            don.setText(formattedDate);

                            df = new SimpleDateFormat("dd/MM/yyyy");
                            date = df.format(c.getTime());
                        }
                    },currentyear,currentMonth,currentDay);
                    datePickerDialog.show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int currentyear = c.get(Calendar.YEAR);
                int currentMonth = c.get(Calendar.MONTH);
                int currentDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AdminRegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, monthOfYear);
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String formattedDate = df.format(c.getTime());
                        dob.setText(formattedDate);

                        df = new SimpleDateFormat("dd/MM/yyyy");
                        date = df.format(c.getTime());
                    }
                },currentyear,currentMonth,currentDay);
                datePickerDialog.show();
            }
        });


    }

    public void registerUser(View view){
        final String username = name.getText().toString();
        final String password = pass.getText().toString();
        final String dateOfBirth = dob.getText().toString();
        final String mobile = mob.getText().toString();
        final String height = ht.getText().toString();
        final String weight = wt.getText().toString();
        final String donation = don.getText().toString();
        final String address = add.getText().toString();
        final String yearOfJoin = yj.getText().toString();
        final String email = emailid1.getText().toString();

        boolean errorOccured = false;
        if ( !validateMobileNo(mobile)) {
            mob.setError("Invalid mobile number");
            errorOccured = true;
        }
        if (!validatepass(password)) {
            pass.setError("Invalid password");
            errorOccured = true;
        }
        if (!validateemail(email)) {
            emailid1.setError("Invalid email");
            errorOccured = true;
        }
        if (username.isEmpty()) {
            name.setError("Invalid name");
            errorOccured = true;
        }
        if (dateOfBirth.isEmpty()) {
            dob.setError("Invalid Date of birth");
            errorOccured = true;
        }
        if (height.isEmpty()) {
            ht.setError("Invalid height");
            errorOccured = true;
        }
        if (weight.isEmpty()) {
            wt.setError("Invalid weight");
            errorOccured = true;
        }
        if (donation.isEmpty()) {
            don.setError("Invalid donation date");
            errorOccured = true;
        }
        if (address.isEmpty()) {
            add.setError("Invalid donation date");
            errorOccured = true;
        }
        if (yearOfJoin.isEmpty()) {
            yj.setError("Invalid year of join");
            errorOccured = true;
        }
        if (errorOccured) {
            return;
        }

        createuser();
        updateUI();



    }



    private boolean validateMobileNo(String mobileNumber) {
        String mobNumber = mobileNumber;
        String regEx = "^[0-9]*$";
        return mobNumber.length() == 10 && mobNumber.matches(regEx);
    }

    private boolean validateemail(String emailid){
        String emlid=emailid;
        String regem="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String regm1="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+\\.+[a-z]+";
        return emlid.matches(regem) || emlid.matches(regm1);
    }

    private boolean validatepass(String pass){
        String password=pass;
        return password.length() >= 8;
    }


    // }
    private void bloodgroupupdate() {
        String blood = genderText.getSelectedItem().toString();
        String deptmnt=deptText.getSelectedItem().toString();
        reff.child("users").child(auth.getCurrentUser().getUid()).child("Bloodgroup").setValue(blood);
        reff.child("users").child(auth.getCurrentUser().getUid()).child("department").setValue(deptmnt);
    }




    private void updateUI(){
        Intent i = new Intent(getApplicationContext(), AdminActivity.class);
        startActivity(i);
        finish();
        Toast.makeText(getApplicationContext(), "User created Succesfully", Toast.LENGTH_SHORT).show();

    }

    private void createuser(){
        final String username = name.getText().toString();
        final String password = pass.getText().toString();
        final String dateOfBirth = dob.getText().toString();
        final String mobile = mob.getText().toString();
        final String height = ht.getText().toString();
        final String weight = wt.getText().toString();
        final String donation = don.getText().toString();
        final String address = add.getText().toString();
        final String yearOfJoin = yj.getText().toString();
        final String email = emailid1.getText().toString();
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            reff.child("users").child(auth.getCurrentUser().getUid()).child("name").setValue(username);
                            reff.child("users").child(auth.getCurrentUser().getUid()).child("mobile_number").setValue(mobile);
                            reff.child("users").child(auth.getCurrentUser().getUid()).child("Password").setValue(password);
                            reff.child("users").child(auth.getCurrentUser().getUid()).child("DateofBirth").setValue(dateOfBirth);
                            reff.child("users").child(auth.getCurrentUser().getUid()).child("height").setValue(height);
                            reff.child("users").child(auth.getCurrentUser().getUid()).child("weight").setValue(weight);
                            reff.child("users").child(auth.getCurrentUser().getUid()).child("Donation").setValue(donation);
                            reff.child("users").child(auth.getCurrentUser().getUid()).child("Address").setValue(address);
                            reff.child("users").child(auth.getCurrentUser().getUid()).child("yearOfJoin").setValue(yearOfJoin);
                            reff.child("users").child(auth.getCurrentUser().getUid()).child("email").setValue(email);
                            bloodgroupupdate();

                        } else {
                            Toast.makeText(getApplicationContext(), "User Could not be created", Toast.LENGTH_SHORT).show();
                        }
                    }


                });
    }
}
