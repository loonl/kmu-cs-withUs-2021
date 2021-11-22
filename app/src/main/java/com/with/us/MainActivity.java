package com.with.us;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.ExpandableListView;
import android.widget.TextView;
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
import com.google.gson.Gson;
import com.with.us.databinding.ActivityMainBinding;
import com.with.us.models.Test;
import com.with.us.models.UserInfo;
import com.with.us.services.RetrofitService;
import com.with.us.services.auxiliary.RequestHelper;
import com.with.us.utils.FirebaseHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private ActionBar actionBar;
    private ExpandableListView listView;
    FirebaseAuth mAuth;
    FirebaseUser user;
    DrawerLayout drawer;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        user = mAuth.getCurrentUser();

        // Make custom title & centered
        invalidateOptionsMenu();
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);
        actionBar = getSupportActionBar();
        setActionBar(actionBar);

        // Set Drawer Controller
        drawer = binding.drawerLayout;
        navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home).setOpenableLayout(drawer).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Set List Category
        ArrayList<ListCategory> DataList = new ArrayList<ListCategory>();
        listView = binding.lvCategory;
        makeListCategory(DataList, "지역", R.array.region);
        makeListCategory(DataList, "관심사", R.array.interest);
        setListCategory(DataList);

        if (user != null) {
//            FirebaseHelper.setAccessToken(this);
            getUserData();
        }
    }

    private void setListCategory(ArrayList<ListCategory> DataList) {
        ListCategoryAdapter listCategoryAdapter = new ListCategoryAdapter(getApplicationContext(),
                R.layout.item_main_group, R.layout.item_main_child, DataList);
        listView.setAdapter(listCategoryAdapter);
    }

    private void makeListCategory(ArrayList<ListCategory> DataList, String alias, int array) {
        ListCategory temp = new ListCategory(alias);
        Resources res = getResources();
        String[] regions_temp = res.getStringArray(array);
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(regions_temp));

        for (String element : arrayList) {
            temp.getChild().add(element);
        }

        DataList.add(temp);
    }

    private void setActionBar(ActionBar actionBar) {
        if (actionBar == null) {
            return;
        }
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem loginMenu = menu.findItem(R.id.action_login);
        MenuItem myPageMenu = menu.findItem(R.id.action_mypage);

        String logInText = getApplicationContext().getResources().getString(R.string.login_text);
        String logOutText = getApplicationContext().getResources().getString(R.string.logout_text);

        if (user != null) {
            loginMenu.setTitle(logOutText);
            myPageMenu.setVisible(true);
        } else {
            loginMenu.setTitle(logInText);
            myPageMenu.setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    private void getUserData() {
        TextView userName = findViewById(R.id.nav_header_title);

        RequestHelper.getUserAPI().getUserInfo("Bearer " + FirebaseHelper.getAccessToken(this))
                .enqueue(new Callback<UserInfo>() {
                    @Override
                    public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                        if (response.isSuccessful()) {
                            userName.setText(response.body().displayName);
                        }
                    }

                    @Override

                    public void onFailure(Call<UserInfo> call, Throwable t) {
                        Log.e(TAG, t.getMessage());
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void logIn(MenuItem item) {
        String logOutText = getApplicationContext().getResources().getString(R.string.logout_text);

        if (item.getTitle().equals(logOutText)) {
            mAuth.signOut();
            Toast.makeText(this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
            startActivity(getIntent());
            finish();
            overridePendingTransition(0, 0);
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    public void getMyPage(MenuItem item) {
        String myPageText = getApplicationContext().getResources().getString(R.string.mypage_text);

        if (item.getTitle().equals(myPageText)) {
            Intent intent = new Intent(this, UserInfoActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
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