package com.example.checked.view;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.checked.R;
import com.example.checked.infra.CheckListPersistence;
import com.example.checked.utils.ViewDialog;
import com.example.checked.view.fragment.DefaultFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeActivity extends FragmentActivity {
    private int page = 0;
    private long idChecklist;
    private DrawerLayout drawerLayout;
    private ImageView menu;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        menu = (ImageView) findViewById(R.id.menu);
        floatingActionButton = (FloatingActionButton)findViewById(R.id.floating);

        idChecklist = (new CheckListPersistence(getBaseContext())).selectDefault().getId();

        menu.setOnClickListener((view)->{drawerLayout.openDrawer(Gravity.LEFT);});
        floatingActionButton.setOnClickListener((view)->{(new ViewDialog()).showDialog(this,idChecklist);});

        goToListItem();
    }

    private void goToListItem(){
        DefaultFragment fragment = DefaultFragment.newInstance();

        getSupportFragmentManager().
                beginTransaction().
                add(R.id.container, fragment, "default_fragment").
                commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                goToListItem();
                page = 0;
                return true;
            case R.id.nav_checklists:
                goToListItem();
                page = 1;
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}