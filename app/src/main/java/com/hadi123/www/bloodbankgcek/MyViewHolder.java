package com.hadi123.www.bloodbankgcek;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class MyViewHolder extends RecyclerView.ViewHolder {

    TextView nametext_single;
    TextView bloodtext_sinle;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        nametext_single=itemView.findViewById(R.id.nametext_single1);
        bloodtext_sinle=itemView.findViewById(R.id.bloodtext_single1);
    }
}
