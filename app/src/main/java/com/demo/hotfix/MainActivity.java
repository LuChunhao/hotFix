package com.demo.hotfix;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] params = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if (checkSelfPermission(params[0]) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(params, 100);
            }
        }
    }

    public void jump(View view) {
        startActivity(new Intent(this, SecondActivity.class));
    }


}
