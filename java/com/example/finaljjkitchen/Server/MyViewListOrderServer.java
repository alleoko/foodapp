package com.example.finaljjkitchen.Server;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.finaljjkitchen.R;

public class MyViewListOrderServer extends RecyclerView.ViewHolder {

    TextView txtPrice, txtOrderNo, txtQuantity;
    Button btnDelete;
    public MyViewListOrderServer(View itemView) {
        super(itemView);

        txtOrderNo=itemView.findViewById(R.id.order_id);
        txtQuantity=itemView.findViewById(R.id.text_quantity);
        txtPrice=itemView.findViewById(R.id.order_phone);
        btnDelete=itemView.findViewById(R.id.btnDelete);
    }
}
