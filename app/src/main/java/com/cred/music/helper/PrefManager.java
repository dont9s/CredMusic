package com.cred.music.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import javax.inject.Inject;

public class PrefManager {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private Gson gson;


    @Inject
    public PrefManager(Context context, Gson gson) {

        this.gson = gson;
        sharedPreferences = context.getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    public void saveFloat(String key, float value) {
        if (editor != null)
            editor.putFloat(key, value)
                    .commit();
    }

    public float getFloat(String key, float value) {
        if (sharedPreferences != null)
            return sharedPreferences.getFloat(key, value);

        return value;
    }


}
