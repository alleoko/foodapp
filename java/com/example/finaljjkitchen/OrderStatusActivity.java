package com.example.finaljjkitchen;

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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.finaljjkitchen.Common.Common;
import com.example.finaljjkitchen.Model.Request;
import com.example.finaljjkitchen.Services.OrderStatusService;
import com.example.finaljjkitchen.ViewHolders.OrderViewHolder;

public class OrderStatusActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView textViewName;
    TextView textViewStatus;
    TextView textViewPhone;
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
        request = firebaseDatabase.getReference("Request");

        textViewName = findViewById(R.id.order_id);
        textViewStatus = findViewById(R.id.order_status);
        textViewPhone = findViewById(R.id.order_phone);
        textViewAddress = findViewById(R.id.order_address);

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



        Intent intent = new Intent(com.example.finaljjkitchen.OrderStatusActivity.this, OrderStatusService.class);
        startService(intent);
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
        FirebaseRecyclerOptions<Request> options =


                new FirebaseRecyclerOptions.Builder<Request>()
                        .setQuery(
                request.orderByChild("phone").equalTo(phone), Request.class).build();





        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(options) {
            @NonNull
            @Override
            public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
                return new OrderViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull OrderViewHolder holder, int position, @NonNull Request model) {
                final TextView textViewId = holder.itemView.findViewById(R.id.order_id);
                textViewId.setText(adapter.getRef(position).getKey());


                TextView textViewLabel = holder.itemView.findViewById(R.id.order_phone);
                textViewLabel.setText(model.getPhone());

                TextView textViewPhone = holder.itemView.findViewById(R.id.order_phone);
                textViewPhone.setText(model.getPhone());

                TextView textViewAddress = holder.itemView.findViewById(R.id.order_address);
                textViewAddress.setText(model.getAddress());

                Button btnDelete = holder.itemView.findViewById(R.id.btnDelete);


                TextView textViewStatus = holder.itemView.findViewById(R.id.order_status);
                textViewStatus.setText(Common.getStatus(model.getStatus()));

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        Intent intentFood = new Intent(com.example.finaljjkitchen.OrderStatusActivity.this, com.example.finaljjkitchen.ViewOrders.class);
                        intentFood.putExtra("orderID", textViewId.getText());
                        startActivity(intentFood);





                    }
                });


                final String orderid = adapter.getRef(position).getKey();
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(com.example.finaljjkitchen.OrderStatusActivity.this, orderid, Toast.LENGTH_SHORT).show();
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
