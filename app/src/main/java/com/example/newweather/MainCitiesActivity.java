package com.example.newweather;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;

import com.google.android.material.navigation.NavigationView;
//import com.squareup.otto.Subscribe;
//import naillibip.firstapp.weather.ConnectionStateMonitor;
//import naillibip.firstapp.weather.EventBus;
//import naillibip.firstapp.weather.event.ChangeThemeBtnClickedEvent;
//import naillibip.firstapp.weather.R;

public class MainCitiesActivity extends BaseActivity  {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setNavigationUI();
        ConnectionStateMonitor connectionStateMonitor = new ConnectionStateMonitor();
        connectionStateMonitor.enable(getApplicationContext());

    }

    private void setNavigationUI() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_cities_list, R.id.nav_settings, R.id.nav_about)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getBus().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getBus().unregister(this);
        super.onStop();
    }

    @Subscribe
    public void onChangeThemeBtnClickedEvent (ChangeThemeBtnClickedEvent event) {
        setDarkTheme(event.isChecked());
        recreate();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100) {
            boolean permissionsGranted = (grantResults.length > 1 && grantResults[1] == PackageManager.PERMISSION_GRANTED)
                    && (grantResults[0] == PackageManager.PERMISSION_GRANTED);
            if (permissionsGranted) recreate();
        }
    }
}
