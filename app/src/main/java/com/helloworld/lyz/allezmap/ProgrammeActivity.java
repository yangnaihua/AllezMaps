package com.helloworld.lyz.allezmap;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;

/**
 * Created at 2017/1/11 20:24
 *
 * @Version 1.0
 * @Author paul (yangnaihua.2008at163.com)
 * @desc: ProgrammeActivity   程序主页面
 */

public class ProgrammeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

    private ImageView mImageView;
    private Dialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programme);

        init();

    }

    public void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.program_activity_toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.program_activity_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //取消navigation的bar滑动效果
        disableNavigationViewScrollbars(navigationView);


        navigationView.setNavigationItemSelectedListener(this);

        mImageView = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.ivAvatar);
        // mImageView.setOnClickListener(this);

        /**
         * 头像监听
         */
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProgrammeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        ButterKnife.bind(this);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.program_activity_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.programme, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        String string = null;
        switch (id) {
            case R.id.nav_me:
                string = "我";
                break;
            case R.id.nav_about:
                string = "关于";
                break;
            case R.id.nav_langage:
                string = "语言";

                langage_set();
                break;
            case R.id.nav_manage:
                string = "通知";
                break;
            case R.id.nav_message:
                string = "私信";
                break;
            case R.id.nav_night:
                string = "夜间模式";
                break;
            case R.id.nav_exit:
                string = "通知";
                break;
            case R.id.nav_setting:
                string = "设置";
                break;
            case R.id.nav_suggestion:
                string = "意见反馈";
                break;
            case R.id.nav_theme:
                string = "主题风格";
//                zhuti(string);
                break;
        }
        if (!TextUtils.isEmpty(string)) {
            Toast.makeText(ProgrammeActivity.this, "你点击了" + string, Toast.LENGTH_LONG).show();


        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.program_activity_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    public  void langage_set(){
        Toast.makeText(ProgrammeActivity.this, "语言dialog", Toast.LENGTH_LONG).show();
        if (mDialog == null) {
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.dialog_select_lanuage, null);

            TextView english = (TextView) layout.findViewById(R.id.dialog_select_english);
            TextView chinese = (TextView) layout.findViewById(R.id.dialog_select_chinese);
            TextView french = (TextView) layout.findViewById(R.id.dialog_select_french);

            mDialog = new Dialog(ProgrammeActivity.this, R.style.Custom_Dialog_Theme);
            mDialog.setCanceledOnTouchOutside(false);


            english.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ProgrammeActivity.this, "你点击了english", Toast.LENGTH_LONG).show();
                    switchLanguage("en");
                    draw();
                }
            });
            chinese.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ProgrammeActivity.this, "你点击了中文", Toast.LENGTH_LONG).show();
                    switchLanguage("zh");

                    draw();
                }
            });
            french.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ProgrammeActivity.this, "你点击了法语", Toast.LENGTH_LONG).show();
                    switchLanguage("fr");
                    draw();
                }
            });

            mDialog.setContentView(layout);
        }
        mDialog.show();


    }

    //当选择语言之后，重新绘制activity
    public void draw(){
        //更新语言后，destroy当前页面，重新绘制
        finish();
        Intent intent = new Intent(ProgrammeActivity.this, ProgrammeActivity.class);
        startActivity(intent);
    }


//    public void zhuti(String string) {
//        Toast.makeText(ProgrammeActivity.this, "你点击了--方法" + string, Toast.LENGTH_LONG).show();
//
//    }

    //首次启动时  检测网络状态
    @Override
    protected void onStart() {
        NetWorkStatus();
        super.onStart();
    }
    // 判断网络连接 方法
    public void NetWorkStatus() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        connectivityManager.getActiveNetworkInfo();

        boolean netSataus = true;
        if (connectivityManager.getActiveNetworkInfo() != null) {
            netSataus = connectivityManager.getActiveNetworkInfo().isAvailable();
        } else {
            AlertDialog.Builder aler_builder = new AlertDialog.Builder(this)
                    .setIcon(R.mipmap.ic_launcher).setTitle(R.string.login_network_title)
                    .setMessage(R.string.login_network_message);
            aler_builder.setPositiveButton(R.string.login_network_setting, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    /* Intent mIntent = new Intent("/"); */
                    /**
                     * 判断手机系统的版本！如果API大于10 就是3.0+
                     * 因为3.0以上的版本的设置和3.0以下的设置不一样，调用的方法不同
                     */

                    Intent intent = null;
                    /**
                     * 判断手机系统的版本！如果API大于10 就是3.0+
                     * 因为3.0以上的版本的设置和3.0以下的设置不一样，调用的方法不同
                     */
                    if (android.os.Build.VERSION.SDK_INT > 10) {
                        intent = new Intent(
                                android.provider.Settings.ACTION_WIFI_SETTINGS);
                    } else {
                        intent = new Intent();
                        ComponentName component = new ComponentName(
                                "com.android.settings",
                                "com.android.settings.WirelessSettings");
                        intent.setComponent(component);
                        intent.setAction("android.intent.action.VIEW");
                    }
                    startActivity(intent);
                }
            }).setNeutralButton(R.string.login_network_cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    dialog.cancel();
                }
            }).show();
        }
    }

    //取消navigation的bar滑动效果
    private void disableNavigationViewScrollbars(NavigationView navigationView) {
        if (navigationView != null) {
            NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
            if (navigationMenuView != null) {
                navigationMenuView.setVerticalScrollBarEnabled(false);
            }
        }
    }
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.Button03:
//                Intent intent = new Intent(mainActivity.this, fristActivity.class);
//                intent.putExtra("data", "mainActivity");
//                startActivity(intent);
//                break;
//            case R.id.Button02:
//                Intent intent = new Intent(mainActivity.this, loginActivity.class);
//                intent.putExtra("data", "mainActivity");
//                startActivity(intent);
//                break;
//            default:
//                break;
//        }
//        if(v.getId()==R.id.ivAvatar){
//            Intent intent = new Intent(this,Login2Activity.class);
//            startActivity(intent);
//        }
    }



//    @OnClick(R.id.snackbar)
//    void goToSnackbar(){
//        startActivity(new Intent(this,SnackBarActivity.class));
//    }
}
