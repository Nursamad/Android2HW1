package kg.geektech.TaskApp36;

import android.content.Context;
import android.content.SharedPreferences;

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



//для сохранение фото
    public void saveImage(String imgUrl){
        preferences.edit().putString("image" , imgUrl).apply();
    }
    public String getImage(){
        return preferences.getString("image" , null);
    }

    public void deleteImage() {
        preferences.edit().remove("image").apply();
    }


    //для сохранение edit text
    public void saveEdt(String edt){
        preferences.edit().putString("edt" , edt).apply();
    }

    public String getEdt(){
        return  preferences.getString("edt" , null);
    }
}
