package com.faltauno.faltauno;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

/**
 * Created by lgonzalez on 03-Jul-17.
 * manages locationn permissions and interactions with the application
 */

public class LocalLocationManager {
    private static LocalLocationManager INSTANCE = null;
    private Gps gps;
    public double userLongitude;
    public double userLatitude;
    public boolean locationPermissionGranted = false;

    private LocalLocationManager(Gps gps) {
        this.gps = gps;
    }

    public static LocalLocationManager getInstance(Gps gps){
        if (INSTANCE == null) {
            INSTANCE = new LocalLocationManager(gps);
        }
        return INSTANCE;
    }

    public static LocalLocationManager getInstance(){
        return INSTANCE;
    }

    //muestra cartel de solicitud de permiso
    public static void showRequestPermission(Activity activity,
                                      final @NonNull String[] permissions,
                                      final @IntRange(from = 0) int requestCode){

        ActivityCompat.requestPermissions(activity,permissions, requestCode);
    }

    //este metodo es el callback para cuando el usuario acepto o rechazo dar los permisos
    private void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        // If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // los permisos fueron dados, busco la location
            locationPermissionGranted = true;
            updateLocation();

        } else {
            locationPermissionGranted = false;
            // permission denied, boo! Disable the
            // functionality that depends on this permission.
        }
        return;
    }


    void updateLocation(){
        if (locationPermissionGranted) {
            //genera la location y guarda latitud y longitud del usuario
            Location location = gps.getLocation();
            this.userLatitude = location.getLatitude();
            this.userLongitude = location.getLongitude();
        }
    }

    public void revokePermissions() {
        locationPermissionGranted = false;
    }


    public double getUserLatitude() {
        return userLatitude;
    }
    public double getUserLongitude() {
        return userLongitude;
    }
}




