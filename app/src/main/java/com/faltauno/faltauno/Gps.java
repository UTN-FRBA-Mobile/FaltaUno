package com.faltauno.faltauno;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;


public class Gps {

    Context context;
    Location location;
    LocationManager locationManager;

    String provider = LocationManager.GPS_PROVIDER;

    Gps(Context ctx){
        context = ctx;
        locationManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);

        //chequeo de permisos
        if((ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) ||
                (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED)){

            //mostrar error
            return;
        }

        locationManager.requestLocationUpdates(provider, 60 * 10, 50, new LocationListener() {
            public void onStatusChanged(String s,int i,Bundle bundle){}
            public void onProviderEnabled(String s){}
            public void onProviderDisabled(String s){}
            public void onLocationChanged(Location loc) {
                location = loc;
            }
        });
    }


    //obtiene la ubicacion del dispositivo
    public Location getLocation(){

        //si no hay location previa, chequea los permisos y hace la peticion
        if(location == null)

            //si los permisos fueron dados...
            if((ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) ||
                    (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED))
                try {
                     location = locationManager.getLastKnownLocation(provider);
                }
                catch (Exception e){
                    e.printStackTrace();
                }

        return location;

    }
}
