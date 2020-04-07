package com.hadi123.www.bloodbankgcek;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainNewActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference reff;
    FirebaseAuth auth;
    FirebaseUser user;
    TextView name,email,dob,mob,blood,ht,wt,don,add,dept,yr;
    FloatingActionButton callbtn;
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
        database = FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        String UserKey=getIntent().getStringExtra("UserKey");
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

        callbtn=findViewById(R.id.call_fab);
        callbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
            
        });

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
