package com.with.us;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBar;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.with.us.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private ActionBar actionBar;
    private ExpandableListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        invalidateOptionsMenu();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);
        // make custom title to locate to center
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home)
                .setOpenableLayout(drawer).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // ExpandableListView
        ArrayList<ListCategory> DataList = new ArrayList<ListCategory>();
        listView = binding.lvCategory;
        // listView = findViewById(R.id.lv_category);
        // R.array.region <= 이렇게 사용하면 된다

        // insert children of group "지역" from arrays.xml
        ListCategory temp = new ListCategory("지역");
        Resources res = getResources();
        String[] regions_temp = res.getStringArray(R.array.region);
        ArrayList<String> regions = new ArrayList<>(Arrays.asList(regions_temp));
        for (String region : regions) {
            temp.getChild().add(region);
        } // insert child
        DataList.add(temp);

        // insert children of group "관심사" from arrays.xml
        temp = new ListCategory("관심사");
        String[] interests_temp = res.getStringArray(R.array.interest);
        ArrayList<String> interests = new ArrayList<>(Arrays.asList(interests_temp));
        for (String interest : interests) {
            temp.getChild().add(interest);
        } // insert child
        DataList.add(temp);


        ListCategoryAdapter listCategoryAdapter = new ListCategoryAdapter(getApplicationContext(),
                R.layout.item_main_group, R.layout.item_main_child, DataList);
        listView.setAdapter(listCategoryAdapter);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_login);
        String logInText = getApplicationContext().getResources().getString(R.string.login_text);
        String logOutText = getApplicationContext().getResources().getString(R.string.logout_text);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            item.setTitle(logOutText);
        } else {
            item.setTitle(logInText);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void logIn(MenuItem item) {
        String logOutText = getApplicationContext().getResources().getString(R.string.logout_text);

        if (item.getTitle().equals(logOutText)) {
            mAuth.signOut();
        }
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        // child onClick listener
        // 여기에서 Text 서버로 넘기면 됩니다.
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(getApplicationContext(), "CHILD CLICKED!", Toast.LENGTH_SHORT).show(); // debug
                return false;
            }
        });
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}