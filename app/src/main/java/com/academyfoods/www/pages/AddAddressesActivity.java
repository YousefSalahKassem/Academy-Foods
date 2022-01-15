package com.academyfoods.www.pages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.academyfoods.www.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AddAddressesActivity extends AppCompatActivity implements LocationListener {

EditText type,streetName,maps,floorNumber,notes;
CardView add,map;
DatabaseReference reference;
LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_addresses);

        getSupportActionBar().hide();

        if (ContextCompat.checkSelfPermission(AddAddressesActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddAddressesActivity.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        }

        calling();

        AddAddress();


    }

    private void calling() {
        add=findViewById(R.id.addAddress);
        type=findViewById(R.id.buildingType);
        streetName=findViewById(R.id.StreetName);
        maps=findViewById(R.id.maps);
        map=findViewById(R.id.map);
        floorNumber=findViewById(R.id.FloorNumber);
        notes=findViewById(R.id.notes);
    }

    private void AddAddress() {

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference=FirebaseDatabase.getInstance().getReference("addresses").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                Map<String,Object> map=new HashMap<>();
                map.put("address",type.getText().toString()+", "+streetName.getText().toString()+", "+floorNumber.getText().toString()+", "+notes.getText().toString()+", "+maps.getText().toString());
                reference.setValue(map);
            }
        });
    }
    private void getLocation() {

        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED )
                return;
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, AddAddressesActivity.this);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(this, ""+location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_SHORT).show();
        try {
            Geocoder geocoder = new Geocoder(AddAddressesActivity.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            String enwan = addresses.get(0).getAddressLine(0);
            System.out.println(enwan);
            maps.setText(enwan);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}