package org.barros.checked.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import org.barros.checked.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(()->{goToHome();}, 5000);
    }

    private void goToHome(){
        finish();

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}