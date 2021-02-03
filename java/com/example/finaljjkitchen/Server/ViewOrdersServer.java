package com.example.finaljjkitchen.Server;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.finaljjkitchen.Model.Order;
import com.example.finaljjkitchen.Server.MyViewListOrderServer;
import com.example.finaljjkitchen.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ViewOrdersServer extends AppCompatActivity {
    //   private RecyclerView productsList;
    //   RecyclerView.LayoutManager layoutManager;
    DatabaseReference cartListRef, ref;
    private String orderID = "";
    RecyclerView recyclerView;
    TextView textViewName;
    TextView textViewStatus;
    TextView textViewPhone;
    TextView textViewAddress, backBtn;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference request;
    // FirebaseRecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);


        backBtn = findViewById(R.id.backbtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        firebaseDatabase = FirebaseDatabase.getInstance();
        request = firebaseDatabase.getReference("Request");
        orderID = getIntent().getStringExtra("orderID");


        textViewName = findViewById(R.id.order_id);
        textViewStatus = findViewById(R.id.order_status);
        textViewPhone = findViewById(R.id.order_phone);
        textViewAddress = findViewById(R.id.order_address);

        recyclerView = findViewById(R.id.recycler_order_status);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


//        cartListRef = FirebaseDatabase.getInstance().getReference().child("Request").child(orderID).child(numberOrder).child("Products");

        cartListRef = FirebaseDatabase.getInstance().getReference().child("Request").child(orderID).child("orders");

        // String haha = Common.currentRequest.;

//        showOrders("1");


        ///////////////////////////////

        //Helper Method
//    private void showOrdersafasdfass(String order) {
//        FirebaseRecyclerOptions<Request> options = new FirebaseRecyclerOptions.Builder<Request>().setQuery(
//                cartListRef.orderByChild("orders").equalTo(order), Request.class).build();
//        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolderServer>(options) {
//            @NonNull
//            @Override
//            public OrderViewHolderServer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_orders, parent, false);
//                return new OrderViewHolderServer(view);
//            }
//
//            @Override
//            protected void onBindViewHolder(@NonNull OrderViewHolderServer holder, int position, @NonNull final Request model) {
//                TextView textViewId = holder.itemView.findViewById(R.id.order_id);
//                textViewId.setText(adapter.getRef(position).getKey());
//
//           //     Toast.makeText(ViewOrders.this, " jagsdas " + cartListRef.getKey(), Toast.LENGTH_SHORT).show();
//
//
//
////                TextView textViewPhone = holder.itemView.findViewById(R.id.order_phone);
////                textViewPhone.setText(model.getPhone());
////
////                TextView textViewAddress = holder.itemView.findViewById(R.id.order_address);
////                textViewAddress.setText(model.getAddress());
////
////                TextView textViewStatus = holder.itemView.findViewById(R.id.order_status);
////                textViewStatus.setText(Common.getStatus(model.getStatus()));
////
////                holder.itemView.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View view) {
////                        Intent intent = new Intent(ViewOrders.this, TrackingOrderActivity.class);
////                        Common.currentRequest = model;
////                        startActivity(intent);
////                    }
////                });
//            }
//        };
//        recyclerView.setAdapter(adapter);
//    }


//    private void showOrders(String phone)
//    {
//        FirebaseRecyclerOptions<Request> options = new FirebaseRecyclerOptions.Builder<Request>().setQuery(
//                request.orderByChild("phone").equalTo(phone), Request.class).build();
//        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(options) {
//            @NonNull
//            @Override
//            public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
//                return new OrderViewHolder(view);
//            }
//
//            @Override
//            protected void onBindViewHolder(@NonNull OrderViewHolder holder, int position, @NonNull Request model) {
//                final TextView textViewId = holder.itemView.findViewById(R.id.order_id);
//                textViewId.setText(adapter.getRef(position).getKey());
//
//                TextView textViewPhone = holder.itemView.findViewById(R.id.order_phone);
//                textViewPhone.setText(model.getPhone());
//
//                TextView textViewAddress = holder.itemView.findViewById(R.id.order_address);
//                textViewAddress.setText(model.getAddress());
//
//                TextView textViewStatus = holder.itemView.findViewById(R.id.order_status);
//                textViewStatus.setText(Common.getStatus(model.getStatus()));
//
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//
////                        Intent intentFood = new Intent(ViewOrders.this, ViewOrders.class);
////                        intentFood.putExtra("orderID", textViewId.getText());
////                        startActivity(intentFood);
//
//
//
//
//
//                    }
//                });
//            }
//        };
//        recyclerView.setAdapter(adapter);
//    }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Order> options;
        FirebaseRecyclerAdapter<Order, MyViewListOrderServer> adapter;


        ref = FirebaseDatabase.getInstance().getReference().child("Request").child(orderID).child("orders");

        options = new FirebaseRecyclerOptions.Builder<Order>()
                .setQuery(
                        ref
                      //  request.orderByChild("phone").equalTo(phone)

                        , Order.class).build();






        adapter = new FirebaseRecyclerAdapter<Order, MyViewListOrderServer>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewListOrderServer holder, @SuppressLint("RecyclerView") final int position, @NonNull Order order) {
                holder.txtOrderNo.setText(order.getProductName());


//gasd

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Toast.makeText(ViewOrders.this, getRef(position).getKey(), Toast.LENGTH_SHORT).show();
//                        String uID = getRef(position).getKey();
//                        RemoverOrder(uID);

                        CharSequence choices[] =new CharSequence[]{
                                "Yes",
                                "No"

                        };


                        AlertDialog.Builder builder = new AlertDialog.Builder(ViewOrdersServer.this);
                        builder.setTitle("Delete the item?");
                        builder.setItems(choices, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (i == 0) {
                                    String uID = getRef(position).getKey();
                                    RemoverOrder(uID);

                                } else {
                                    finish();
                                }

                            }
                        });
                        builder.show();


                    }});




            }
////////////////


            //////////////
            @NonNull
            @Override
            public MyViewListOrderServer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_orders, parent, false);

                return new MyViewListOrderServer(v);


            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }



    private void RemoverOrder(String orderID) {
        ref.child(orderID).removeValue();
    }


}