package com.bbubtb111p16y4s1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_app_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbarID);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawerLayoutID);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.open_drawer,R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView nav = findViewById(R.id.navigationID);
        nav.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menuNewOrder){
            Intent inNewOrder = new Intent(MainActivity.this, NewOrderActivity.class);
            startActivity(inNewOrder);
        }else if(id == R.id.menuFavoriteItems){
            Intent inFav = new Intent(MainActivity.this, FavoriteItemsActivity.class);
            startActivity(inFav);
        }else if(id == R.id.menuPopularItems){
            Intent inpop = new Intent(MainActivity.this, PopularItemsActivity.class);
            startActivity(inpop);
        }else if(id == R.id.menuRecentViews){
            Intent inRecent = new Intent(MainActivity.this, RecentViewsActivity.class);
            startActivity(inRecent);
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.mnProducts){
            Intent in = new Intent(MainActivity.this, ProductActivity.class);
            startActivity(in);
        }else if(id == R.id.mnCategories){
            Intent in = new Intent(MainActivity.this, CategoryActivity.class);
            startActivity(in);
        }else if(id == R.id.mnContacts){
            Intent in = new Intent(MainActivity.this, ContactActivity.class);
            startActivity(in);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
    public void ViewContact(View v){
        Intent in = new Intent(MainActivity.this, ContactActivity.class);
        startActivity(in);
    }
}