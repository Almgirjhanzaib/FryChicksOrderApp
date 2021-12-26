package com.example.frychicksorderapp;

import android.content.Context;
import android.content.SharedPreferences;

public class AppSession {
    private static AppSession instance;
    public SharedPreferences preferences;

    SharedPreferences.Editor editor;
    Context context;
    String uid,name,phoneNum;

public  void getSession(Context context)
{
    AppSession.instance.context = context;
    preferences = context.getSharedPreferences("Current Session",context.MODE_PRIVATE);
    editor = preferences.edit();
}

    public static  AppSession getInstance() {
        if (instance == null)
        {
            instance = new AppSession();
            return instance;

        }
        else {
            return instance;
        }
    }

    public String getUid() {
        preferences.getString("UID",uid);
        return uid;
    }

    public void setUid(String uid) {
        doEdit();
        editor.putString("UID",uid);
        doCommit();

    } public String getName() {
        preferences.getString("NAME",name);
        return name;
    }

    public void setName(String name) {
        doEdit();
        editor.putString("NAME",name);
        doCommit();

    }
    public String getPhone() {
        preferences.getString("PHONE_NUM",phoneNum);
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        doEdit();
        editor.putString("PHONE_NUM",phoneNum);
        doCommit();

    }

    public void doEdit(){
        if (editor != null)
        {
            editor = preferences.edit();
        }

    }
    public void doCommit(){
        if(editor != null)
        {
            editor.commit();
        }
    }

}
