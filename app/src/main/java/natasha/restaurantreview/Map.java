package natasha.restaurantreview;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.location.*;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.maps.GeoPoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class Map extends Activity {
    Location myLocation = null;
    LatLng pos;
    boolean mStopHandler = false;
    Handler mHandler = new Handler();
    static final LatLng HAMBURG = new LatLng(53.558, 9.927);
    static final LatLng KIEL = new LatLng(53.551, 9.993);
    private GoogleMap map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Intent intent = getIntent();
     String address =    intent.getStringExtra("Address");
     String dir =    intent.getStringExtra("Directions");
        Log.w("Map", address);
        Log.w("Map", dir);
        Geocoder geoCoder = new Geocoder(Map.this, Locale.getDefault());
        double latitude = 0;
        double longtitude = 0;
        try {
            List<Address> addresses =
                    geoCoder.getFromLocationName(address, 1);
            if (addresses.size() > 0) {
                latitude = addresses.get(0).getLatitude();
                longtitude =
                        addresses.get(0).getLongitude();
            }

        } catch (IOException e) { // TODO Auto-generated catch block
            e.printStackTrace();
        }


        pos = new LatLng(latitude, longtitude);


        Log.w("Map", pos.toString());

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapfrag))
                .getMap();
    if(dir.contains("1")) {



        map.setMyLocationEnabled(true);


        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // do your stuff - don't create a new runnable here!
                if (!mStopHandler && map.getMyLocation() == null) {
                    mHandler.postDelayed(this, 1000);
                    Log.w("Map", "Not found yet");
                } else {

                    Log.w("Map", "Found");

                    myLocation = map.getMyLocation();
                    LatLng pos2 = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                    PolylineOptions rectOptions = new PolylineOptions()
                            .add(pos)
                            .add(pos2);
                    rectOptions.color(Color.RED);


                    Polyline polyline = map.addPolyline(rectOptions);

                }
            }
        };

        mHandler.post(runnable);

        Marker hamburg = map.addMarker(new MarkerOptions().position(pos)
                .title(address));

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 15));

        // Zoom in, animating the camera.
        map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

    }else{
        Marker hamburg = map.addMarker(new MarkerOptions().position(pos)
                .title(address));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 20));

        // Zoom in, animating the camera.
        map.animateCamera(CameraUpdateFactory.zoomTo(20), 2000, null);
    }
            }










       /* Marker kiel = map.addMarker(new MarkerOptions()
                .position(KIEL)
                .title("Kiel")
                .snippet("Kiel is cool")
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.ic_launcher)));*/

        // Move the camera instantly to hamburg with a zoom of 15.





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
