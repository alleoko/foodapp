package com.example.finaljjkitchen;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyViewListOrder extends RecyclerView.ViewHolder {

    TextView txtPrice, txtOrderNo, txtQuantity;
    Button btnDelete;
    public MyViewListOrder(View itemView) {
        super(itemView);

        txtOrderNo=itemView.findViewById(R.id.order_id);
        btnDelete=itemView.findViewById(R.id.btnDelete);

        txtQuantity=itemView.findViewById(R.id.text_quantity);
        txtPrice=itemView.findViewById(R.id.order_phone);
    }
}
