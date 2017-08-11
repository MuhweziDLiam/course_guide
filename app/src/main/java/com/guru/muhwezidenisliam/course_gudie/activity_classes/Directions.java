package com.guru.muhwezidenisliam.course_gudie.activity_classes;


/**
 * Created by Muhwezi Denis Liam on 9/28/2016.
 */

        import android.Manifest;
        import android.app.Notification;
        import android.app.PendingIntent;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.graphics.Bitmap;
        import android.graphics.Color;
        import android.graphics.drawable.BitmapDrawable;
        import android.graphics.drawable.Drawable;
        import android.location.Location;
        import android.location.LocationListener;
        import android.location.LocationManager;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.os.Handler;
        import android.os.Message;
        import android.provider.Settings;
        import android.support.design.widget.Snackbar;
        import android.support.v4.app.ActivityCompat;
        import android.support.v4.app.NotificationCompat;
        import android.support.v4.app.NotificationManagerCompat;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.RatingBar;
        import android.widget.TextView;

        import com.akexorcist.googledirection.DirectionCallback;
        import com.akexorcist.googledirection.GoogleDirection;
        import com.akexorcist.googledirection.constant.AvoidType;
        import com.akexorcist.googledirection.constant.TransportMode;
        import com.akexorcist.googledirection.model.Direction;
        import com.akexorcist.googledirection.model.Route;
        import com.akexorcist.googledirection.model.Step;
        import com.akexorcist.googledirection.util.DirectionConverter;
        import com.google.android.gms.maps.CameraUpdate;
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
        import com.guru.muhwezidenisliam.course_gudie.R;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.BufferedReader;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;

        import im.delight.android.location.SimpleLocation;


public class Directions extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final float zoom = 18;

    private SimpleLocation simpleLocation;

    double current_longitude;

    Double faculty_latitude;
    Double faculty_longitude;

    double current_latitude;



    private String[] colors = {"#7fff7272", "#7f31c7c5", "#7fff8a00"};

    Bundle bd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_fragment);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        bd = getIntent().getExtras();

        String latitude = bd.getString("latitude");
        String longitude = bd.getString("longitude");

        faculty_latitude = Double.parseDouble(latitude);
        faculty_longitude = Double.parseDouble(longitude);

        mapFragment.getMapAsync(this);

        // alternative A
        /*location.calculateDistance(startLatitude, startLongitude, endLatitude, endLongitude);

        // alternative B
        location.calculateDistance(startPoint, endPoint);*/

        //location.setBlurRadius(10000);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setHomeButtonEnabled(true);

        simpleLocation = new SimpleLocation(this);

        // if we can't access the location yet
        if (!simpleLocation.hasLocationEnabled()) {
            // ask the user to enable location access
            SimpleLocation.openSettings(this);
        }


       /*current_latitude = location.getLatitude();
       current_longitude = location.getLongitude();*/



    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        // googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        // googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        // googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        // googleMap.setMapType(GoogleMap.MAP_TYPE_NONE);

        mMap.setMyLocationEnabled(true);

        mMap.setTrafficEnabled(true);



        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

            @Override
            public void onMyLocationChange(Location location) {
                // TODO Auto-generated method stub

                GoogleDirection.withServerKey("AIzaSyC8Z0GyBAmpWVfrrxULbJ1y1dAThXeTIG0")
                        .from(new LatLng(location.getLatitude(),location.getLongitude()))
                        .to(new LatLng(faculty_latitude,faculty_longitude))
                        //.transportMode(TransportMode.DRIVING)
                        .avoid(AvoidType.FERRIES)
                        .avoid(AvoidType.TOLLS)
                        //.transportMode(TransportMode.WALKING)
                        .execute(new DirectionCallback() {

                            @Override
                            public void onDirectionSuccess(Direction direction, String rawBody) {
                                if(direction.isOK()) {

                                    mMap.clear();

                                    MarkerOptions marker = new MarkerOptions().position(new LatLng(faculty_latitude,faculty_longitude)).title("Faculty location");
                                    mMap.addMarker(marker);

                                    //Alternate paths
                                   /* for (int i = 0; i < direction.getRouteList().size(); i++) {
                                        Route route = direction.getRouteList().get(i);
                                        String color = colors[i % colors.length];
                                        ArrayList<LatLng> directionPositionList = route.getLegList().get(0).getDirectionPoint();
                                        mMap.addPolyline(DirectionConverter.createPolyline(Directions.this, directionPositionList, 5, Color.parseColor(color)));
                                    }*/

                                    //driving
                                    ArrayList<LatLng> directionPositionList = direction.getRouteList().get(0).getLegList().get(0).getDirectionPoint();

                                    mMap.addPolyline(DirectionConverter.createPolyline(Directions.this, directionPositionList, 5, Color.RED));

                                    Log.d("direction_success","Mapped direction");
                                } else {
                                    Log.d("direction_!success","Mapped not direction");
                                    // Do something
                                }
                            }

                            @Override
                            public void onDirectionFailure(Throwable t) {
                                // Do something
                                Log.d("direction_failure","API key ?");
                            }
                        });




                Double distance = simpleLocation.calculateDistance(location.getLatitude(),location.getLongitude(),faculty_latitude,faculty_longitude);

                current_latitude = location.getLatitude();
                current_longitude = location.getLongitude();
                Log.d("lat=>=long",location.getLatitude()+" =>= "+location.getLongitude());
                Log.d("distance","===>==="+distance+" Meters");

            }
        });

        // Showing / hiding your current location
      //  LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


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


       /* locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 5000, 0,
                new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {

                      //  mMap.clear();

                        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(),location.getLongitude()));
                        CameraUpdate zoom = CameraUpdateFactory.zoomTo(18);

                        mMap.moveCamera(center);
                        mMap.animateCamera(zoom);


                        MarkerOptions marker = new MarkerOptions().position(new LatLng(location.getLatitude(),location.getLongitude())).title("Current location");
                       // mMap.addMarker(marker);

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
*/

        /*locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                3000, 0, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(),location.getLongitude()));
                        CameraUpdate zoom = CameraUpdateFactory.zoomTo(18);

                        mMap.moveCamera(center);
                        mMap.animateCamera(zoom);

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
                });*/



        // Enable / Disable zooming controls
        mMap.getUiSettings().setZoomControlsEnabled(false);

        // Enable / Disable my location button
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        // Enable / Disable Compass icon
        mMap.getUiSettings().setCompassEnabled(true);

        // Enable / Disable Rotate gesture
        mMap.getUiSettings().setRotateGesturesEnabled(true);


        // Enable / Disable zooming functionality
        mMap.getUiSettings().setZoomGesturesEnabled(true);

        mMap.setTrafficEnabled(true);




        // Add a marker in Sydney and move the camera
        LatLng uganda = new LatLng(faculty_latitude, faculty_longitude);

        CameraUpdate cam = CameraUpdateFactory.newLatLngZoom(uganda, zoom);
        mMap.moveCamera(cam);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // make the device update its location
        simpleLocation.beginUpdates();

        // ...
    }

    @Override
    protected void onPause() {
        // stop location updates (saves battery)
        simpleLocation.endUpdates();

        // ...

        super.onPause();
    }
}

