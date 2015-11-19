package com.mayer.lucas.journeytracker;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;



/**
 * Created by lulz on 17/11/2015.
 */
public class GpsManager implements LocationListener {
    private  LocationManager locationManager;
    public GpsManager(Context context) {
        locationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        String provider = locationManager.getBestProvider(criteria, true);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            //return null;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1,
                0, this);
        locationManager.getLastKnownLocation(provider);
    }

    private int checkSelfPermission(String accessFineLocation) {
        return 0;
    }

    @Override
    public void onLocationChanged(Location location) {

        Float speed = location.getSpeed();
        speed = (float) (speed * 3.6);
        MainActivity.UpdateSpeed(speed);
        if (GraphView.ArrayListSpeed.size() < 100) {
            GraphView.ArrayListSpeed.add(speed);
            MainActivity.GV.invalidate();

        } else {

            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
            locationManager.removeUpdates(this);
            MainActivity.t.interrupt();
            MainActivity.GV.CleartArrayListSpeed();

        }
        Log.e("Location", "speed: " + speed);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.e("Information:", "onStatusChanged: ");
    }

    public void StopTracking() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        locationManager.removeUpdates(this);
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.e("Information:", "onProviderEnabled: ");

    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.e("Information:", "onProviderDisabled: ");
    }

}
