package com.example.finaljjkitchen.Server;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finaljjkitchen.Common.Common;
import com.example.finaljjkitchen.Model.Catering;
import com.example.finaljjkitchen.R;
import com.example.finaljjkitchen.Server.ViewHolders.CateringViewHolderServer;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CateringStatusActivityServer extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView textViewName;
    TextView textViewStatus;
    TextView textViewPhone, textViewTotal;
    TextView textViewAddress;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference request;
    FirebaseRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);


       TextView backBtn = findViewById(R.id.backbtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




        //init Firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        request = firebaseDatabase.getReference("Book");

        textViewName = findViewById(R.id.order_id);
        textViewStatus = findViewById(R.id.order_status);
        textViewPhone = findViewById(R.id.order_phone);
        textViewAddress = findViewById(R.id.order_address);

        textViewTotal = findViewById(R.id.order_total);

        recyclerView = findViewById(R.id.recycler_order_status);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        if(getIntent().getStringExtra("phone") != null)
        {
            showOrders(getIntent().getStringExtra("phone"));
        }
        else
        {
            showOrders(Common.currentUser.getPhone());
        }



//        Intent intent = new Intent(CateringStatusActivity.this, OrderStatusService.class);
//        startService(intent);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    //Helper Method
    private void showOrders(String phone)
    {
        FirebaseRecyclerOptions<Catering> options = new FirebaseRecyclerOptions.Builder<Catering>().setQuery(
                request.orderByChild("catering_Phone"), Catering.class).build();
        adapter = new FirebaseRecyclerAdapter<Catering, CateringViewHolderServer>(options) {


            @NonNull
            @Override
            public CateringViewHolderServer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.catering_list_item, parent, false);
                return new CateringViewHolderServer(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull CateringViewHolderServer holder, int position, @NonNull Catering model) {

                final TextView textViewId = holder.itemView.findViewById(R.id.order_id);
                textViewId.setText(model.getCatering_ProductName());


                TextView textViewLabel = holder.itemView.findViewById(R.id.order_phone);
                textViewLabel.setText(model.getCatering_Phone());

                TextView textViewPhone = holder.itemView.findViewById(R.id.cater_date);
                textViewPhone.setText(model.getDate());
//
                TextView textViewTotal = holder.itemView.findViewById(R.id.order_total);
                textViewTotal.setText(model.getCatering_Price());
//
                TextView textViewAddress = holder.itemView.findViewById(R.id.order_address);
                textViewAddress.setText(model.getCatering_Address());
//
               Button btnDelete = holder.itemView.findViewById(R.id.btnDelete);
//
//
//                TextView textViewStatus = holder.itemView.findViewById(R.id.order_status);
//                textViewStatus.setText(Common.getStatus(model.getStatus()));

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


//                        Intent intentFood = new Intent(CateringStatusActivityServer.this, ViewOrders.class);
//                        intentFood.putExtra("orderID", request.getKey());
//                        startActivity(intentFood);





                    }
                });


                final String orderid = adapter.getRef(position).getKey();
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CateringStatusActivityServer.this, orderid, Toast.LENGTH_SHORT).show();
                        String uID = orderid;
                        RemoverOrder(uID);
                    }
                });


            }
        };
        recyclerView.setAdapter(adapter);
    }

    private void RemoverOrder(String orderID) {
        request.child(orderID).removeValue();
    }

}//class ends
