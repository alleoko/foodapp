package com.example.finaljjkitchen;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.widget.SearchView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class Mappie extends FragmentActivity implements OnMapReadyCallback
{
    GoogleMap map; SupportMapFragment mapFragment; SearchView searchView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mappie);

searchView = findViewById(R.id.sv_location);
mapFragment= (SupportMapFragment) getSupportFragmentManager()
        .findFragmentById(R.id.google_map);
searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
    @Override
    public boolean onQueryTextSubmit(String query) {
        String location = searchView.getQuery().toString();
        List<Address> addressList = null;
        if(location !=null || !location.equals("")){
            Geocoder geocoder = new Geocoder(Mappie.this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            map.addMarker(new MarkerOptions().position(latLng).title(location));
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 0));
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
});
mapFragment.getMapAsync(this);






































    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;



























    }
}
