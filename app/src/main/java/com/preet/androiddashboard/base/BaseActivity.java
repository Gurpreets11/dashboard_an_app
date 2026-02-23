package com.preet.androiddashboard.base;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.preet.androiddashboard.R;
import com.preet.androiddashboard.core.LoadingDialog;
import com.preet.androiddashboard.core.storage.PrefManager;
import com.preet.androiddashboard.features.ProfileActivity;
import com.preet.androiddashboard.features.SettingsActivity;
import com.preet.androiddashboard.features.auth.LoginActivity;


import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public abstract class BaseActivity extends AppCompatActivity {

    private LoadingDialog loadingDialog;

    protected DrawerLayout drawerLayout;
    protected NavigationView navigationView;
    protected MaterialToolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        loadingDialog = new LoadingDialog(this);


        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.toolbar_menu);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.open_drawer,
                R.string.close_drawer
        );

        toggle.getDrawerArrowDrawable().setColor(
                getResources().getColor(R.color.hamburger_color)
        );

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        View headerView = navigationView.getHeaderView(0);

        TextView txtUserName = headerView.findViewById(R.id.txtUserName);
        TextView txtUserEmail = headerView.findViewById(R.id.txtUserEmail);

// Get from SharedPref
        String name = "Gurpreet Singh";//PrefManager.getInstance(this).getUserName();
        String email = "gurpreet.singh@infodartmail.com";//PrefManager.getInstance(this).getUserEmail();

        txtUserName.setText(name);
        txtUserEmail.setText(email);

        setupDrawerMenu();

        /*toolbar.setOnMenuItemClickListener(item -> {

            if (item.getItemId() == R.id.action_profile) {

                startActivity(new Intent(this, ProfileActivity.class));
                return true;
            }

            return false;
        });*/
    }

    protected  void setTitle(String title){
        getSupportActionBar().setTitle(title);
    }
    protected void loadContentLayout(@LayoutRes int layoutId) {
        FrameLayout contentFrame = findViewById(R.id.contentFrame);
        LayoutInflater.from(this).inflate(layoutId, contentFrame, true);
    }

    private void setupDrawerMenu() {

        navigationView.setNavigationItemSelectedListener(item -> {

            item.setChecked(true); // Highlight selected

            int id = item.getItemId();

            if (id == R.id.nav_home) {
                getSupportActionBar().setTitle("Dashboard");
            }

            else if (id == R.id.nav_settings) {
                startActivity(new Intent(this, SettingsActivity.class));
            }

            else if (id == R.id.nav_logout) {
                logout();
            }

            drawerLayout.closeDrawers();
            return true;
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        MenuItem profileItem = menu.findItem(R.id.action_profile);
        if (profileItem != null) {
            Drawable drawable = profileItem.getIcon();
            if (drawable != null) {
                drawable.setTint(Color.WHITE);
            }
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_profile) {
            startActivity(new Intent(this, ProfileActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void logout() {

        // Clear SharedPreferences
//        SharedPrefManager.getInstance(this).clear();




            new AlertDialog.Builder(this)
                    .setTitle("Logout")
                    .setMessage("Are you sure you want to logout?")
                    .setPositiveButton("Yes", (dialog, which) -> {

                        PrefManager.getInstance(this).clear();

                        Intent intent = new Intent(this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();



    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showLoading() {
        loadingDialog.show();
    }

    public void hideLoading() {
        loadingDialog.dismiss();
    }
}
