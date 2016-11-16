package com.feicuiedu.treasure;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.feicuiedu.treasure.commons.ActivityUtils;
import com.feicuiedu.treasure.treasure.home.HomeActivity;
import com.feicuiedu.treasure.user.UserPrefs;
import com.feicuiedu.treasure.user.login.LoginActivity;
import com.feicuiedu.treasure.user.register.RegisterActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 第一个界面
 */
public class MainActivity extends AppCompatActivity {

    private ActivityUtils activityUtils;  //工具类

    public static final String ACTION_ENTER_HOME = "action.enter.home";

    // 广播接收器(当登陆和注册成功后，将发送出广播)
    // 接收到后，关闭当前页面
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {  //接收
            finish();  //结束当前页面
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);  //初始化工具类
        setContentView(R.layout.activity_main);  //加载布局

        //轻量级的存储   判断是否登录过
        SharedPreferences preferences = getSharedPreferences("user_info", MODE_PRIVATE);
        if (preferences != null) {
            //接收信息是否与用户控制台的信息相同（手机令牌）
            if (preferences.getInt("key_tokenid", 0)==UserPrefs.getInstance().getTokenid()) {
                activityUtils.startActivity(HomeActivity.class);  //跳转
                finish();  //结束
            }
        }
        ButterKnife.bind(this);  //绑定控件

        // 注册本地广播接收器---动态注册
        IntentFilter intentFilter = new IntentFilter(ACTION_ENTER_HOME); //初始化过滤器
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, intentFilter); //注册广播接收器
    }
//---------------跳转事件---------------------------------------------------------------------------
    @OnClick({R.id.btn_Login, R.id.btn_Register})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.btn_Login:  //登录按钮
                activityUtils.startActivity(LoginActivity.class);
                break;
            case R.id.btn_Register:  //注册按钮
                activityUtils.startActivity(RegisterActivity.class);
                break;
        }
    }
}