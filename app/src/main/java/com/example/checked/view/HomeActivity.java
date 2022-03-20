package com.example.checked.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.checked.R;
import com.example.checked.infra.CheckListPersistence;
import com.example.checked.utils.ViewDialog;
import com.example.checked.view.fragment.ChecklistsFragment;
import com.example.checked.view.fragment.DefaultFragment;
import com.example.checked.view.fragment.FragmentBase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends FragmentActivity implements NavigationView.OnNavigationItemSelectedListener {
    private int page = 0;
    private long idChecklist;
    private DrawerLayout drawerLayout;
    private ImageView menu;
    private FloatingActionButton floatingActionButton;
    public static FragmentBase fragmentBase;
    private TextView titlePage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        titlePage = (TextView) findViewById(R.id.title_page);

        menu = (ImageView) findViewById(R.id.menu);
        floatingActionButton = (FloatingActionButton)findViewById(R.id.floating);
        idChecklist = (new CheckListPersistence(getBaseContext())).selectDefault().getId();

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        goToListItem();

        floatingActionButton.setOnClickListener((view)->{fragmentBase.addItem();});
    }

    private void goToListItem(){
        fragmentBase = DefaultFragment.newInstance(idChecklist,titlePage);

        getSupportFragmentManager().
                beginTransaction().
                add(R.id.container, fragmentBase, "default_fragment").
                commit();

    }

    public void updateContent(FragmentBase fragmentBase){

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.container, fragmentBase).
                commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                fragmentBase = DefaultFragment.newInstance(idChecklist, titlePage);
                updateContent(fragmentBase);
                page = 0;
                drawerLayout.closeDrawer(Gravity.LEFT);
                return true;
            case R.id.nav_checklists:
                fragmentBase = ChecklistsFragment.newInstance(getSupportFragmentManager(), titlePage);
                updateContent(fragmentBase);
                page = 1;
                drawerLayout.closeDrawer(Gravity.LEFT);
                return true;
            default:
                drawerLayout.closeDrawer(Gravity.LEFT);
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}