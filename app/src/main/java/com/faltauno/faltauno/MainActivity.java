package com.faltauno.faltauno;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.view.MenuItem;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Info de Usuario";

    private double userLatitude;
    private double userLongitude;

    Gps gps;
    private static final String[] LOCATION_PERMS={
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private static final int INITIAL_REQUEST=1337;
    private static final int LOCATION_REQUEST=INITIAL_REQUEST+1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Visualizacion del menu de la toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();
            String uid = user.getUid();
//            Log.i(TAG, "el nombre es: " + name + " y el email es: " + email + " y el uid es : " + uid);
//            Toast.makeText(getApplicationContext(), user.getDisplayName(), Toast.LENGTH_LONG).show();
//            Toast.makeText(getApplicationContext(), user.getEmail(), Toast.LENGTH_LONG).show();
//            Toast.makeText(getApplicationContext(), user.getUid(), Toast.LENGTH_LONG).show();
        } else {
            andaYLogueate();
        }


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher_toolbar);

        //cneira84 - Se agrega para visualizacion de sliding tabs
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomFragmentPagerAdapter(getSupportFragmentManager(),
                MainActivity.this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        LinearLayout main_linear = (LinearLayout)findViewById(R.id.parent);

        //lgonzalez: agrego peticion de permisos para geoloc
        gps = new Gps(this);
        ActivityCompat.requestPermissions(this, LOCATION_PERMS, LOCATION_REQUEST);
//  esto no se usa en ningun lado!!      LinearLayout main_linear = (LinearLayout)findViewById(R.id.parent);
    }

    private void andaYLogueate() {
        Intent intent = new Intent(this, Login_App.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final Intent settings = new Intent(this, SettingsActivity.class);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

/*  esto no se usa en ningun lado!! --> */     //final Intent settings = new Intent(this, SettingsActivity.class);
        return true;
    }

    //cneira84 - metodo llamado por el menu definido en menu_main.xml
    public boolean action_profile(MenuItem item) {
        FragmentManager fm = getSupportFragmentManager();
        ProfileDialogFragment profileDialogFragment = new ProfileDialogFragment();
        profileDialogFragment.show(fm, "profile");
        return true;
    }

    //cneira84 - metodo llamado por el menu definido en menu_main.xml
    public boolean action_about(MenuItem item){
        FragmentManager fm = getSupportFragmentManager();
        AboutDialogFragment aboutDialogFragment = new AboutDialogFragment();
        aboutDialogFragment.show(fm, "about");
        return true;
    }

    //cneira84 - metodo llamado por el menu definido en menu_main.xml
    public boolean action_settings(MenuItem item) {
        Intent settings_i = new Intent(this, SettingsActivity.class);
        startActivity(settings_i);
        return true;
    }

    public void action_disconnects(MenuItem item){
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        andaYLogueate();
    }

    //cneira84 - metodo llamado por el menu definido en menu_main.xml
    public void action_search(MenuItem item) {
    }
//    public void logout(View view) {
//        FirebaseAuth.getInstance().signOut();
//        LoginManager.getInstance().logOut();
//        andaYLogueate();
//    }

    public void mostrarNuevoPartido() {
        NuevoPartidoFragment fragment = new NuevoPartidoFragment();

        FragmentManager m = getSupportFragmentManager();
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setVisibility(View.GONE);
        m.beginTransaction()
//                .show(fragment)
                .add(R.id.activity_main, fragment)
                .addToBackStack(null)
                .commit();

    }

    public void cerrarNuevoPartido() {

        FragmentManager m = getSupportFragmentManager();
        // Saco el último fragment que cargué para volver al pager viewer
        m.popBackStack();
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setVisibility(View.VISIBLE);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        FragmentManager m = getSupportFragmentManager();
        //Antes de habilitar la barra chequeo estar en el fragment posterior al view pager
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0 && m.getBackStackEntryCount()==1) {

            final TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
            tabLayout.setVisibility(View.VISIBLE);
        }
        return super.onKeyDown(keyCode, event);
    }

    public void mostrarNuevaCancha() {
        NuevaCanchaFragment fragment = new NuevaCanchaFragment();

        FragmentManager m = getSupportFragmentManager();
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setVisibility(View.GONE);
        m.beginTransaction()
                .replace(R.id.activity_main, fragment)
                .addToBackStack(null)
                .commit();

    }

    public void cerrarNuevaCancha() {

        FragmentManager m = getSupportFragmentManager();
        // Saco el último fragment que cargué para volver al pager viewer
        m.popBackStack();
    }

    //este metodo es el callback para cuando el usuario acepto o rechazo dar los permisos
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case LOCATION_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // los permisos fueron dados, busco la location
                    updateLocation(this.getCurrentFocus());

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

        }
    }

    public double getUserLatitude() {
        return userLatitude;
    }

    public void setUserLatitude(double userLatitude) {
        this.userLatitude = userLatitude;
    }

    public double getUserLongitude() {
        return userLongitude;
    }

    public void setUserLongitude(double userLongitude) {
        this.userLongitude = userLongitude;
    }

    void updateLocation(View view){
        //genera la location y guarda latitud y longitud del usuario
        Location location = gps.getLocation();
        setUserLatitude(location.getLatitude());
        setUserLongitude(location.getLongitude());
    }
}

