package com.demo.hotfix.bean;

import android.content.Context;
import android.widget.Toast;

public class TestBean {

    public static void show(Context context) {
        int a = 10;
        int b = 0;

        Toast.makeText(context, "show-->" + (a / b), Toast.LENGTH_SHORT).show();
    }
}
