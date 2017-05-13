package com.faltauno.faltauno;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;

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


//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.fragmentContainer, new FragmentProxPartidos(), "FragmentProxPartidos")
//                .commit();
//        tieneDosFragments = findViewById(R.id.contentFrame) != null;


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
        return true;
    }

    public void logout(View view) {
        LoginManager.getInstance().logOut();
        andaYLogueate();
    }
}

