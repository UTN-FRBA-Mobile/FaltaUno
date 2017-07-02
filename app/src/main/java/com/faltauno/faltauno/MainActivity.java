package com.faltauno.faltauno;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import static android.R.attr.fragment;
import static android.R.attr.tag;
import static android.R.id.message;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Info de Usuario";

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
        NuevoPartido fragment = new NuevoPartido();

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
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            final TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
            tabLayout.setVisibility(View.VISIBLE);
        }
        return super.onKeyDown(keyCode, event);
    }
}

