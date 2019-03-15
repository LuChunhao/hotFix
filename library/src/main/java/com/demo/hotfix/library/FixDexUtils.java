package com.demo.hotfix.library;

import android.content.Context;

import com.demo.hotfix.library.utils.ArrayUtils;
import com.demo.hotfix.library.utils.Constants;
import com.demo.hotfix.library.utils.FileUtils;
import com.demo.hotfix.library.utils.Reflectutils;

import java.io.File;
import java.util.HashSet;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

public class FixDexUtils {

    // 存放需要修复的dex集合
    private static HashSet<File> loadedDex = new HashSet<>();

    static {
        // 修复之前先清理
        loadedDex.clear();
    }


    public static void loadFixedDex(Context context) {
        if (null == context) return;

        // Dex文件目录
        File fileDir = context.getDir(Constants.DEX_DIR, Context.MODE_PRIVATE);
        // 遍历私有目录中所有文件
        File[] listFiles =  fileDir.listFiles();
        for (File file : listFiles) {
            if (file.getName().endsWith(Constants.DEX_SUFFIX) && !"classes.dex".equals(file.getName())) {
                // 找到修复包dex文件，加入集合
                loadedDex.add(file);
            }
        }

        // 模拟类加载器
        createDexClassLoader(context, fileDir);
    }

    private static void createDexClassLoader(Context context, File fileDir) {
        // 临时的解压目录（dex-->.class）
        String optimizedDir = fileDir.getAbsolutePath() + File.separator + "opt_dex";

        // 创建该目录
        File optFile = new File(optimizedDir);
        if (!optFile.exists()) {
            optFile.mkdirs();
        }

        for (File dex : loadedDex) {
            DexClassLoader classLoader = new DexClassLoader(dex.getAbsolutePath(), optimizedDir, null, context.getClassLoader());
            // 每遍历一个需要修复的dex文件，就需要插队一次
            hotFix(classLoader, context);
        }
    }

    private static void hotFix(DexClassLoader classLoader, Context context) {
        // 获取系统的PathClassLoader
        PathClassLoader pathClassLoader = (PathClassLoader) context.getClassLoader();
        try {
            // 获取自己的dexElements数组
            Object myDexElements = Reflectutils.getDexElements(Reflectutils.getPathList(classLoader));
            // 获取系统的dexElements数组
            Object systemDexElements = Reflectutils.getDexElements(Reflectutils.getPathList(pathClassLoader));
            // 合并成新的dexElements数组， 并设置优先级
            Object dexElements = ArrayUtils.combineArray(myDexElements, systemDexElements);
            // 获取系统的pathList对象
            Object systemPathList = Reflectutils.getPathList(pathClassLoader);
            // 重新赋值系统的pathList属性值  --修改的dexElements数组（新合成的）
            Reflectutils.setField(systemPathList, systemPathList.getClass(), dexElements);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
