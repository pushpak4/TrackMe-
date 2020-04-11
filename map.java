package com.example.trackme.tm.trackme;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import mapparts.directionfinder;
import mapparts.directionfinderlistener;
import mapparts.Route;

public class map extends FragmentActivity implements OnMapReadyCallback, directionfinderlistener {

    private GoogleMap mMap;
    private Button b1;
    private EditText e1;
    private EditText e2;
    private List<Marker> om = new ArrayList<>();
    private List<Marker> dm = new ArrayList<>();
    private List<Polyline> po = new ArrayList<>();
    private ProgressDialog progressDialog;

    LocationManager lc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        EditText od= findViewById(R.id.e1);
        EditText dd= findViewById(R.id.e2);
        Button bu= findViewById(R.id.b1);
        final db data= new db(this);
        bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest();
            }
        });

        lc = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if(lc.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            lc.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    double lat = location.getLatitude();
                    double lon = location.getLongitude();
                    LatLng ll = new LatLng(lat,lon);
                    String lats = Double.toString(location.getLatitude());
                    String lons = Double.toString(location.getLongitude());

                    SQLiteDatabase database = data.getWritableDatabase();
                    ContentValues val= new ContentValues();
                    val.put("lati",lats);
                    val.put("longit",lons);
                    database.insert("Cordinates", null, val);
                    Geocoder gc = new Geocoder(getApplicationContext());
                    try {
                        List<Address> ad = gc.getFromLocation(lat, lon, 1);
                        String st = ad.get(0).getLocality()+",";
                        st += ad.get(0).getCountryName();
                        mMap.addMarker(new MarkerOptions().position(ll).title(st));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ll, 18));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        }
         else if(lc.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            lc.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                    double lat = location.getLatitude();
                    double lon = location.getLongitude();
                    LatLng ll = new LatLng(lat,lon);
                    Geocoder gc = new Geocoder(getApplicationContext());

                    try {
                        List<Address> ad = gc.getFromLocation(lat, lon, 1);
                        String st = ad.get(0).getLocality()+",";
                        st += ad.get(0).getCountryName();
                        mMap.addMarker(new MarkerOptions().position(ll).title(st));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ll, 18));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String lats = Double.toString(location.getLatitude());
                    String lons = Double.toString(location.getLongitude());

                    SQLiteDatabase database = data.getWritableDatabase();
                    ContentValues val= new ContentValues();
                    val.put("lati",lats);
                    val.put("longit",lons);
                    database.insert("Cordinates", null, val);
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        }
    }

    private void sendRequest() {
        String origin = e1.getText().toString();
        String destination = e2.getText().toString();
        if (origin.isEmpty()) {
            Toast.makeText(this, "Please enter origin address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (destination.isEmpty()) {
            Toast.makeText(this, "Please enter destination address!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            new directionfinder(this, origin, destination).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    }

    @Override
    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(this, "Please wait.",
                "Finding direction..!", true);

        if (om != null) {
            for (Marker marker : om) {
                marker.remove();
            }
        }

        if (dm != null) {
            for (Marker marker : dm) {
                marker.remove();
            }
        }

        if (po != null) {
            for (Polyline polyline:po ) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        progressDialog.dismiss();
        po = new ArrayList<>();
        om = new ArrayList<>();
        dm = new ArrayList<>();

        for (Route route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16));
            ((TextView) findViewById(R.id.t1)).setText(route.duration.text);
            ((TextView) findViewById(R.id.t2)).setText(route.distance.text);

            om.add(mMap.addMarker(new MarkerOptions()
                    .title(route.startAddress)
                    .position(route.startLocation)));
            dm.add(mMap.addMarker(new MarkerOptions()
                    .title(route.endAddress)
                    .position(route.endLocation)));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            po.add(mMap.addPolyline(polylineOptions));
        }
    }
}
