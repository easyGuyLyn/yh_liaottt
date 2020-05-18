package com.dawoo.coretool.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.dawoo.coretool.util.packageref.Utils;

/**
 * SharedPreferences 工具
 * Create by Fei on 16-12-08.
 */
public class SPTool {
    /**
     * 保存在手机里面的文件名
     */
    private static final String SHARE_NAME = "share_data";
    /**
     * 保存在手机里面的游戏
     */
    private static final String GAME_LIST = "game_list_data";

    /**
     * 保存数据
     */
    public static void put(String key, Object value) {
        SharedPreferences sp = Utils.getContext().getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        }

        editor.commit();
    }

    /**
     * 读取数据
     */
    public static Object get(String key, Object defValue) {
        SharedPreferences sp = Utils.getContext().getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);

        if (defValue instanceof String) {
            return sp.getString(key, (String) defValue);
        } else if (defValue instanceof Integer) {
            return sp.getInt(key, (Integer) defValue);
        } else if (defValue instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defValue);
        } else if (defValue instanceof Float) {
            return sp.getFloat(key, (Float) defValue);
        } else if (defValue instanceof Long) {
            return sp.getLong(key, (Long) defValue);
        }

        return null;
    }

    /**
     * 删除数据
     */
    public static void remove(String key) {
        SharedPreferences sp = Utils.getContext().getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (key != null) {
            editor.remove(key);
        }
        editor.commit();
    }

    /**
     * 清空数据
     */
    public static void clear() {
        SharedPreferences sp = Utils.getContext().getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();

        SharedPreferences sp1 = Utils.getContext().getSharedPreferences(GAME_LIST, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1 = sp1.edit();
        editor1.clear();
        editor1.commit();

    }


    /**
     * 保存游戏数据
     */
    public static void saveGameList(String key, String value) {
        SharedPreferences sp = Utils.getContext().getSharedPreferences(GAME_LIST, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, (String) value);
        editor.commit();
    }

    /**
     * 读取游戏数据
     */
    public static String readGameList(String key, String defValue) {
        SharedPreferences sp = Utils.getContext().getSharedPreferences(GAME_LIST, Context.MODE_PRIVATE);
        return sp.getString(key,  defValue);
    }
}