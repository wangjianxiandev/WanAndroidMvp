package com.wjx.android.wanandroidmvp.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.wjx.android.wanandroidmvp.R;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.wjx.android.wanandroidmvp.base.utils.Constant;
import com.wjx.android.wanandroidmvp.ui.fragment.HomeFragment;
import com.wjx.android.wanandroidmvp.ui.fragment.MeFragment;
import com.wjx.android.wanandroidmvp.ui.fragment.ParentSquareFragment;
import com.wjx.android.wanandroidmvp.ui.fragment.ProjectFragment;

import com.wjx.android.wanandroidmvp.ui.fragment.SquareFragment;
import com.wjx.android.wanandroidmvp.ui.fragment.WeChatFragment;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class MainActivity extends AppCompatActivity {

    private static final int INDEX_HOMEPAGE = 0;
    private static final int INDEX_PROJECT = 1;
    private static final int INDEX_SQUARE = 2;
    private static final int INDEX_WE_CHAT = 3;
    private static final int INDEX_ME = 4;

    private SparseArray<Fragment> mFragmentSparseArray = new SparseArray<>();

    private int mLastIndex = -1;

    private long mExitTime;

    Unbinder mBinder;

    @BindView(R.id.navigation_bottom)
    BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBinder = ButterKnife.bind(this);
        initBottomNavigation();
        switchFragment(INDEX_HOMEPAGE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (@NonNull MenuItem item){
        if (item.getItemId() == R.id.top_search) {
            Intent intent = new Intent(MainActivity.this, SearchWordActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }



    private void initBottomNavigation() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.menu_home:
                                switchFragment(INDEX_HOMEPAGE);
                                return true;
                            case R.id.menu_project:
                                switchFragment(INDEX_PROJECT);
                                return true;
                            case R.id.menu_square:
                                switchFragment(INDEX_SQUARE);
                                return true;
                            case R.id.menu_wechat:
                                switchFragment(INDEX_WE_CHAT);
                                return true;
                            case R.id.menu_me:
                                switchFragment(INDEX_ME);
                                return true;
                            default:
                                return false;
                        }
                    }
                });
        showBadgeView(4, 6);
    }


    private void switchFragment(int index) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (mLastIndex != -1) {
            fragmentTransaction.hide(getFragment(mLastIndex));
        }
        mLastIndex = index;
        Fragment fragment = getFragment(index);
        if (fragment != null & !fragment.isAdded()) {
            fragmentTransaction.add(R.id.container, fragment);
        }
        fragmentTransaction.show(fragment).commitAllowingStateLoss();
        getSupportFragmentManager().executePendingTransactions();
    }

    private Fragment getFragment(int index) {
        Fragment fragment = mFragmentSparseArray.get(index);
        if (fragment == null) {
            switch (index) {
                case INDEX_HOMEPAGE:
                    fragment = new HomeFragment();
                    break;
                case INDEX_PROJECT:
                    fragment = new ProjectFragment();
                    break;
                case INDEX_SQUARE:
                    fragment = new ParentSquareFragment();
                    break;
                case INDEX_WE_CHAT:
                    fragment = new WeChatFragment();
                    break;
                case INDEX_ME:
                    fragment = new MeFragment();
                    break;
                default:
                    break;
            }
            mFragmentSparseArray.put(index, fragment);
        }
        return fragment;
    }

    @Override
    public void onBackPressed() {
        long curTime = System.currentTimeMillis();
        if (curTime - mExitTime > Constant.EXIT_TIME) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mExitTime = curTime;
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 设置显示的未读数字
     *
     * @param index      当前bottom index
     * @param showNumber 显示的未读数值
     */
    private void showBadgeView(int index, final int showNumber) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) mBottomNavigationView.getChildAt(0);
        if (index < menuView.getChildCount()) {
            View view = menuView.getChildAt(index);
            View icon = view.findViewById(com.google.android.material.R.id.icon);
            int iconWidth = icon.getWidth();
            int tabWidth = view.getWidth() / 2;
            int spaceWidth = tabWidth - iconWidth;
            final QBadgeView qBadgeView = new QBadgeView(this);

            qBadgeView.bindTarget(view).setGravityOffset(spaceWidth + 50, 13, false).setBadgeNumber(showNumber);
            qBadgeView.setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
                @Override
                public void onDragStateChanged(int dragState, Badge badge, View targetView) {
                    qBadgeView.clearAnimation();
                }
            });

        }
    }
}
