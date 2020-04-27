package com.hadi123.www.bloodbankgcek;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainNewActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference reff;
    FirebaseAuth auth;
    FirebaseUser user;
    TextView name,email,dob,mob,blood,ht,wt,don,add,dept,yr;
    FloatingActionButton callbtn,editbtn;
    Calendar c;
    DatePickerDialog dpd;
    String date;
    private static final int REQUEST_CALL=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new2);
        name=findViewById(R.id.username1);
        email=findViewById(R.id.emailuser);
        dob=findViewById(R.id.dob);
        mob=findViewById(R.id.mobilenumber);
        blood=findViewById(R.id.bloodgroup);
        ht=findViewById(R.id.height);
        wt=findViewById(R.id.weight);
        don=findViewById(R.id.donation);
        add=findViewById(R.id.address);
        dept=findViewById(R.id.dept);
        yr=findViewById(R.id.year);
        setDefaultDate();
        database = FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        final String UserKey=getIntent().getStringExtra("UserKey");
        reff=FirebaseDatabase.getInstance().getReference().child("users");
        reff.child(UserKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    String name1 = dataSnapshot.child("name").getValue().toString();
                    name.setText(name1);
                    String mail = dataSnapshot.child("email").getValue().toString();
                    email.setText(mail);
                    String dobe = dataSnapshot.child("DateofBirth").getValue().toString();
                    dob.setText(dobe);
                    String mobile = dataSnapshot.child("mobile_number").getValue().toString();
                    mob.setText(mobile);
                    String bloodg = dataSnapshot.child("Bloodgroup").getValue().toString();
                    blood.setText(bloodg);
                    String htg = dataSnapshot.child("height").getValue().toString();
                    ht.setText(htg);
                    String wtg = dataSnapshot.child("weight").getValue().toString();
                    wt.setText(wtg);
                    String dont = dataSnapshot.child("Donation").getValue().toString();
                    don.setText(dont);
                    String addd = dataSnapshot.child("Address").getValue().toString();
                    add.setText(addd);
                    String yj = dataSnapshot.child("yearOfJoin").getValue().toString();
                    yr.setText(yj);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        editbtn=findViewById(R.id.edit_fab);
        callbtn=findViewById(R.id.call_fab);
        callbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
            
        });


        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogView=getLayoutInflater().inflate(R.layout.edittext_layout,null);
                final TextInputEditText field1 = dialogView.findViewById(R.id.httext_content);
                final TextInputEditText field2 = dialogView.findViewById(R.id.wttext_content);
                final TextView field=dialogView.findViewById(R.id.edittext_layout);
                final ImageButton mBtn=dialogView.findViewById(R.id.imageButton);
                field.setText(don.getText());
                field1.setText(ht.getText());
                field2.setText(wt.getText());

                mBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar c = Calendar.getInstance();
                        int currentyear = c.get(Calendar.YEAR);
                        int currentMonth = c.get(Calendar.MONTH);
                        int currentDay = c.get(Calendar.DAY_OF_MONTH);
                        DatePickerDialog datePickerDialog = new DatePickerDialog(MainNewActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                                Calendar c = Calendar.getInstance();
                                c.set(Calendar.YEAR, year);
                                c.set(Calendar.MONTH, monthOfYear);
                                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                String formattedDate = df.format(c.getTime());
                                field.setText(formattedDate);

                                df = new SimpleDateFormat("dd/MM/yyyy");
                                date = df.format(c.getTime());
                            }
                        },currentyear,currentMonth,currentDay);
                        datePickerDialog.show();
                    }
                });
                final TextView finalTextView = don;
                final TextView finalHtView=ht;
                final TextView finalWtView=wt;
                new AlertDialog.Builder(MainNewActivity.this)
                        .setView(dialogView)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String content = field.getText().toString();
                                String content1 = field1.getText().toString();
                                String content2 = field2.getText().toString();
                                reff.child(UserKey).child("weight").setValue(content2);
                                reff.child(UserKey).child("height").setValue(content1);
                                reff.child(UserKey).child("Donation").setValue(content);
                                finalTextView.setText(content);
                                finalHtView.setText(content1);
                                finalWtView.setText(content2);
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .setCancelable(false)
                        .create()
                        .show();
            }
        });

    }
    private void setDefaultDate() {
        Date c = Calendar.getInstance().getTime();
        //System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(c);


        df = new SimpleDateFormat("dd/MM/yyyy");
        date = df.format(c);
    }


    private void makePhoneCall() {
        String number=mob.getText().toString();
        if(ContextCompat.checkSelfPermission(MainNewActivity.this,
                Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainNewActivity.this,
                    new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
        }else{
            String dial="tel:" + number;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_CALL){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                makePhoneCall();
            }else{
                Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
