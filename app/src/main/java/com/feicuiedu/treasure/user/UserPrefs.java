package com.feicuiedu.treasure.user;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 用户仓库,使用前必须先对其进行初始化 init(),否则就报错啦！！！
 */
public class UserPrefs {

    private final SharedPreferences preferences;  //轻量级存储

    private static UserPrefs userPrefs;  //用户控制台

    public static void init(Context context){
        userPrefs = new UserPrefs(context); //初始化用户控制台
    }

    private static final String PREFS_NAME = "user_info"; //用户信息

    private static final String KEY_TOKENID = "key_tokenid";  //令牌id
    private static final String KEY_PHOTO = "key_photo";  //相册

    private UserPrefs(Context context) {
        //获得一个轻量级存储
        preferences = context.getApplicationContext().
                getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }
    //获得实例
    public static UserPrefs getInstance() {
        return userPrefs;
    }
    //设置令牌id
    public void setTokenid(int tokenid) {
        preferences.edit().putInt(KEY_TOKENID, tokenid).commit();
    }
    //设置照片
    public void setPhoto(String photoUrl) {
        preferences.edit().putString(KEY_PHOTO, photoUrl).apply();
    }
    //获取令牌id
    public int getTokenid() {
        return preferences.getInt(KEY_TOKENID, -1);
    }
    //获取照片
    public String getPhoto() {
        return preferences.getString(KEY_PHOTO, null);
    }
    //清空用户信息
    public void clearUser(){
        preferences.edit().clear().commit(); //清除
    }
}
