
        package com.example.finaljjkitchen.Server;

        import android.annotation.SuppressLint;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.example.finaljjkitchen.ViewOrders;
        import com.firebase.ui.database.FirebaseRecyclerAdapter;
        import com.firebase.ui.database.FirebaseRecyclerOptions;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.jaredrummler.materialspinner.MaterialSpinner;
        import com.example.finaljjkitchen.Common.Common;
        import com.example.finaljjkitchen.Model.Request;
        import com.example.finaljjkitchen.R;
        import com.example.finaljjkitchen.Server.Services.OrderListenService;
        import com.example.finaljjkitchen.Server.ViewHolders.OrderViewHolderServer;

        public class OrderStatusActivityServer extends AppCompatActivity {
        
            RecyclerView recyclerView;
            TextView textViewName;
            TextView textViewStatus;
            TextView textViewPhone;
            TextView textViewAddress;
        
            FirebaseDatabase firebaseDatabase;
            DatabaseReference request;
            FirebaseRecyclerAdapter adapter;
            private MaterialSpinner orderStatus;
        
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_order_status_server);
        
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
        
        
                TextView backBtn = findViewById(R.id.backbtn);
                backBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
        
        
        
        
        
                if(getIntent().getStringExtra("phone") != null)
                {
                    showOrders(getIntent().getStringExtra("phone"));
                }
                else
                {
                    showOrders(Common.currentUser.getPhone());
                }
        
        
                Intent intentServer = new Intent(com.example.finaljjkitchen.Server.OrderStatusActivityServer.this, OrderListenService.class);
                startService(intentServer);
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
        
            @Override
            public boolean onContextItemSelected(MenuItem item) {
                if(item.getTitle().equals(Common.UPDATE))
                {
                    showUpdateDialog(adapter.getRef(item.getOrder()).getKey(), (Request) adapter.getItem(item.getOrder()));
                }
                else if(item.getTitle().equals(Common.DELETE))
                {
                    deleteOrder(adapter.getRef(item.getOrder()).getKey());
                }
                return super.onContextItemSelected(item);
            }
        
        
            //Helper Method
            private void showOrders(String phone)
            {
                FirebaseRecyclerOptions<Request> options = new FirebaseRecyclerOptions.Builder<Request>().setQuery(
                        request.orderByChild("phone"), Request.class).build();
                adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolderServer>(options) {
                    @NonNull
                    @Override
                    public OrderViewHolderServer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item_server, parent, false);
                        return new OrderViewHolderServer(view);
                    }
        
                    @Override
                    protected void onBindViewHolder(@NonNull OrderViewHolderServer holder, int position, @NonNull final Request model) {
                        TextView textViewId = holder.itemView.findViewById(R.id.order_id);
                        textViewId.setText(adapter.getRef(position).getKey());
        
                        TextView textViewPhone = holder.itemView.findViewById(R.id.order_phone);
                        textViewPhone.setText(model.getPhone());
        
                        TextView textViewAddress = holder.itemView.findViewById(R.id.order_address);
                        textViewAddress.setText(model.getAddress());

                        TextView textViewStatus = holder.itemView.findViewById(R.id.order_status);
                        textViewStatus.setText(Common.getStatus(model.getStatus()));

                        TextView textViewName = holder.itemView.findViewById(R.id.order_client);
                        textViewName.setText(model.getName());


                        Button btnDelete = holder.itemView.findViewById(R.id.btnDelete);
                        Button btnSeeOrder = holder.itemView.findViewById(R.id.btnSeeOrder);


                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(com.example.finaljjkitchen.Server.OrderStatusActivityServer.this, TrackingOrderActivity.class);
                                Common.currentRequest = model;
                                startActivity(intent);
                            }
                        });

                        final String orderid = adapter.getRef(position).getKey();
                        btnDelete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(OrderStatusActivityServer.this, "The ORDER NO. " + orderid+ " has been deleted", Toast.LENGTH_SHORT).show();
                                String uID = orderid;
                                deleteOrder(uID);
                            }
                        });



                        btnSeeOrder.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Toast.makeText(OrderStatusActivityServer.this, orderid, Toast.LENGTH_SHORT).show();

                                Intent intentFood = new Intent(OrderStatusActivityServer.this, ViewOrders.class);
                                intentFood.putExtra("orderID",orderid );
                                startActivity(intentFood);


                            }
                        });

                    }
                };
                recyclerView.setAdapter(adapter);
            }
        
        
        
            private void showUpdateDialog(final String key, final Request item) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("Update Order");
                alertDialog.setMessage("Please choose status");
                alertDialog.setIcon(R.drawable.ic_access_time_black_24dp);
        
                @SuppressLint("InflateParams") View view = getLayoutInflater().inflate(R.layout.update_order_dialog, null);
                alertDialog.setView(view);
        
                orderStatus = view.findViewById(R.id.order_status);
        
                orderStatus.setItems("Placed", "Shipping", "Shipped");
                orderStatus.setSelectedIndex(Integer.parseInt(item.getStatus()));
        
                alertDialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        item.setStatus(String.valueOf(orderStatus.getSelectedIndex()));
                        request.child(key).setValue(item);
                    }
                });
        
                alertDialog.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        
                alertDialog.show();
            }
        
        
            private void deleteOrder(String key) {
                request.child(key).removeValue();
            }




        }
