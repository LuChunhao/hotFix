package com.demo.hotfix.library.utils;

import java.lang.reflect.Field;

public class Reflectutils {


    /**
     * 通过反射获取某对象，并设置私有访问
     * @param object    该属性类对象
     * @param clazz     该属性所属类
     * @param field     属性名
     * @return          该属性队形
     */
    private static Object getField(Object object, Class<?> clazz, String field) throws NoSuchFieldException, IllegalAccessException {
        Field localField = clazz.getDeclaredField(field);
        localField.setAccessible(true);
        return localField.get(object);
    }

    /**
     * 给某属性赋值，并设置私有可访问
     * @param object    该属性类对象
     * @param clazz     该属性所属类
     * @param value     值
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static void setField(Object object, Class<?> clazz, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field localField = clazz.getDeclaredField("dexElements");
        localField.setAccessible(true);
        localField.set(object, value);

    }

    /**
     * 通过反射获取BaseDexDlassLoader中的pathLst对象
     * @param baseDexClassLoader
     * @return
     * @throws ClassNotFoundException
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static Object getPathList(Object baseDexClassLoader) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        return getField(baseDexClassLoader, Class.forName("dalvik.system.BaseDexClassLoader"), "pathList");
    }

    /**
     * 通过反射获取BaseDexDlassLoader中的pathLst对象， 再获取dexElements对象
     * @param paramObject
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static Object getDexElements(Object paramObject) throws NoSuchFieldException, IllegalAccessException {
        return getField(paramObject, paramObject.getClass(), "dexElements");
    }
}
