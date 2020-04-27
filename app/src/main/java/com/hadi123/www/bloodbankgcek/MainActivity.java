package com.hadi123.www.bloodbankgcek;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference reff;
    FirebaseAuth auth;
    FirebaseUser user;
    TextView name,email,dob,mob,blood,ht,wt,don,add,dept,yr;
    FloatingActionButton editbtn;
    Calendar c;
    DatePickerDialog dpd;
    ProgressDialog progressDialog;
    ImageView logout;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);
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
        database = FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        reff=FirebaseDatabase.getInstance().getReference().getRoot();

        progressDialog=new ProgressDialog(MainActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name1 =dataSnapshot.child("users").child(auth.getCurrentUser().getUid()).child("name").getValue().toString();
                name.setText(name1);
                String mail =dataSnapshot.child("users").child(auth.getCurrentUser().getUid()).child("email").getValue().toString();
                email.setText(mail);
                String dobe =dataSnapshot.child("users").child(auth.getCurrentUser().getUid()).child("DateofBirth").getValue().toString();
                dob.setText(dobe);
                String mobile =dataSnapshot.child("users").child(auth.getCurrentUser().getUid()).child("mobile_number").getValue().toString();
                mob.setText(mobile);
                String bloodg =dataSnapshot.child("users").child(auth.getCurrentUser().getUid()).child("Bloodgroup").getValue().toString();
                blood.setText(bloodg);
                String htg =dataSnapshot.child("users").child(auth.getCurrentUser().getUid()).child("height").getValue().toString();
                ht.setText(htg);
                String wtg =dataSnapshot.child("users").child(auth.getCurrentUser().getUid()).child("weight").getValue().toString();
                wt.setText(wtg);
                String dont =dataSnapshot.child("users").child(auth.getCurrentUser().getUid()).child("Donation").getValue().toString();
                don.setText(dont);
                String addd =dataSnapshot.child("users").child(auth.getCurrentUser().getUid()).child("Address").getValue().toString();
                add.setText(addd);
                String yj =dataSnapshot.child("users").child(auth.getCurrentUser().getUid()).child("yearOfJoin").getValue().toString();
                yr.setText(yj);

                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        editbtn=findViewById(R.id.edit_fab);
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
                        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                new AlertDialog.Builder(MainActivity.this)
                        .setView(dialogView)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String content = field.getText().toString();
                                String content1 = field1.getText().toString();
                                String content2 = field2.getText().toString();
                                reff.child("users").child(auth.getCurrentUser().getUid()).child("weight").setValue(content2);
                                reff.child("users").child(auth.getCurrentUser().getUid()).child("height").setValue(content1);
                                reff.child("users").child(auth.getCurrentUser().getUid()).child("Donation").setValue(content);
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


        logout=findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Warning")
                            .setMessage("Are you sure that you want to logout?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("No", null)
                            .create()
                            .show();

            }
        });

    }
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity();
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 1000);
    }


}
