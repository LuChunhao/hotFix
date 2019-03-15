package com.demo.hotfix;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.demo.hotfix.bean.TestBean;
import com.demo.hotfix.library.FixDexUtils;
import com.demo.hotfix.library.utils.Constants;
import com.demo.hotfix.library.utils.FileUtils;

import java.io.File;
import java.io.IOException;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    public void bug(View view) {
        TestBean.show(this);
    }

    public void fix(View view) {

        // 通过接口向服务器下载修复好的dex文件，将文件放到SD卡
        File sourceFile = new File(Environment.getExternalStorageDirectory(), Constants.DEX_NAME);
        // 目标文件：私有目录
        File targetFile = new File(getDir(Constants.DEX_DIR, Context.MODE_PRIVATE).getAbsolutePath()
                + File.separator + Constants.DEX_NAME);

        if (targetFile.exists()) {
            targetFile.delete();
            Log.d(this.getClass().getSimpleName(), "已经存在目标文件，删除!");
        }

        try {
            FileUtils.copyFile(sourceFile, targetFile);
            Log.d(this.getClass().getSimpleName(), "复制完成");
            // 开始修复
            FixDexUtils.loadFixedDex(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
