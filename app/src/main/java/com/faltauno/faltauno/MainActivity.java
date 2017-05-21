package com.faltauno.faltauno;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import static android.R.attr.tag;
import static android.R.id.message;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (AccessToken.getCurrentAccessToken() == null) {
            andaYLogueate();
        }

        //Visualizacion del menu de la toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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
    public void action_profile(MenuItem item) {
        //Busco el id del Main Activity para setearle la transparencia
        LinearLayout main_layout = (LinearLayout)findViewById(R.id.activity_main);
        main_layout.setAlpha(0.4F);

        //Codigo para el popup
        LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.fragment_profile, null);
        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                Toolbar.LayoutParams.MATCH_PARENT,
                Toolbar.LayoutParams.MATCH_PARENT);
        Button buttonOK = (Button)popupView.findViewById(R.id.buttonOK);
        buttonOK.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                popupWindow.dismiss();
                //Busco el id del Main Activity para setearlo nuevamente opaco
                LinearLayout main_layout = (LinearLayout)findViewById(R.id.activity_main);
                main_layout.setAlpha(1);
            }});

        popupWindow.showAsDropDown(buttonOK, 50, -30);
    }

    //cneira84 - metodo llamado por el menu definido en menu_main.xml
    public boolean action_about(MenuItem item){
        //Busco el id del Main Activity para setearle la transparencia
        LinearLayout main_layout = (LinearLayout)findViewById(R.id.activity_main);
        main_layout.setAlpha(0.4F);

        LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        final Dialog dialog = new Dialog(layoutInflater.getContext()); // Context, this, etc.
        dialog.setContentView(R.layout.about);
        dialog.setTitle("Titulo");
        dialog.show();

        //TODO: Modificar para utilizar el default del alert dialog
        //https://developer.android.com/guide/topics/ui/dialogs.html
        Button buttonOK = (Button)dialog.findViewById(R.id.buttonOK);
        buttonOK.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //Busco el id del Main Activity para setearlo nuevamente opaco
                LinearLayout main_layout = (LinearLayout)findViewById(R.id.activity_main);
                main_layout.setAlpha(1);
            }});
        return true;
    }

    //cneira84 - metodo llamado por el menu definido en menu_main.xml
    public boolean action_settings(MenuItem item) {
        Intent settings_i = new Intent(this, SettingsActivity.class);
        startActivity(settings_i);
        return true;
    }

    public void action_disconnects(MenuItem item){
    LoginManager.getInstance().logOut();
    andaYLogueate();
    }

    //cneira84 - metodo llamado por el menu definido en menu_main.xml
    public void action_search(MenuItem item) {
    }
}

