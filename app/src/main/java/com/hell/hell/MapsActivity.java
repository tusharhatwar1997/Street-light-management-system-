package com.hell.hell;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

public  class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    // Google Map
    private GoogleMap googleMap;
Marker marker;
    String name;
    // Latitude & Longitude
    private Double Latitude = 0.00;
    private Double Longitude = 0.00;
    ArrayList<HashMap<String, String>> location = new ArrayList<HashMap<String, String>>();
    HashMap<String, String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        map = new HashMap<String, String>();
        map.put("LocationID", "stright1");
        map.put("Latitude", "21.10582645");
        map.put("Longitude", "79.0027508");
        map.put("LocationName", "G H Raisoni College Of Engineering ");
        location.add(map);

        // Location 2
        map = new HashMap<String, String>();
        map.put("LocationID", "stright2");
        map.put("Latitude", "21.10622838");
        map.put("Longitude", "79.00190859");
        map.put("LocationName", "Deo Boys Hostel , Police Nagar ");
        location.add(map);

        // Location 3
        map = new HashMap<String, String>();
        map.put("LocationID", "stright3");
        map.put("Latitude", "21.10581957");
        map.put("Longitude", "79.00102918");
        map.put("LocationName", "Sachin Mess , Police Nagar ");
        location.add(map);

        // *** Display Google Map
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googleMap)).getMapAsync(this);
       }



    @Override
    public void onMapReady(final GoogleMap googleMap) {

        // *** Focus & Zoom
        Latitude = Double.parseDouble(location.get(0).get("Latitude").toString());
        Longitude = Double.parseDouble(location.get(0).get("Longitude").toString());
        LatLng coordinate = new LatLng(Latitude, Longitude);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 17));

        // *** Marker (Loop)
        for (int i = 0; i < location.size(); i++) {
            Latitude = Double.parseDouble(location.get(i).get("Latitude").toString());
            Longitude = Double.parseDouble(location.get(i).get("Longitude").toString());
            name = location.get(i).get("LocationName").toString();
            MarkerOptions marker = new MarkerOptions().position(new LatLng(Latitude, Longitude)).title(name);
            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
            googleMap.addMarker(marker);


        }

googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
    @Override
    public boolean onMarkerClick(Marker marker) {
 String PoleNumber = marker.getTitle();
 String PoleLocation = marker.getId();
        Intent intent = new Intent(getApplicationContext(), MapComplaint.class);
        intent.putExtra("Key_PoleNumber", PoleNumber);
        intent.putExtra("Key_PoleLocation", PoleLocation);
        startActivity(intent);

               return false;
    }
});


}


}