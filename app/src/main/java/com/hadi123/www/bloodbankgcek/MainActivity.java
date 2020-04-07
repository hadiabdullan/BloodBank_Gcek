package com.hadi123.www.bloodbankgcek;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

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
                        c= Calendar.getInstance();
                        int day = c.get(Calendar.DAY_OF_MONTH);
                        int month =c.get(Calendar.MONTH);
                        int year=c.get(Calendar.YEAR);
                        dpd=new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                                field.setText(mDay+"/" + (mMonth+1) +"/" +mYear);
                            }
                        },day,month,year);
                        dpd.show();
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

    }
}
