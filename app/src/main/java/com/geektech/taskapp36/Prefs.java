package com.geektech.taskapp36;

import android.content.Context;
import android.content.SharedPreferences;

import java.net.PortUnreachableException;

public class Prefs {

    private SharedPreferences preferences;

    public Prefs(Context context) {
    preferences = context.getSharedPreferences("setting", Context.MODE_PRIVATE);
    }

//сохраняем исформацию
    public  void saveBoardState(){
        preferences.edit().putBoolean("isShown" ,  true).apply();
    }

    //проверка
    public boolean isBoardShown(){
        return preferences.getBoolean("isShown" , false);
    }
}
