package com.conny.tv.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.conny.library.lazy.LazyViewPager;
import com.conny.library.slidingmenu.lib.SlidingMenu;
import com.conny.tv.R;
import com.conny.tv.material.base.BaseActivity;
import com.conny.tv.material.view.HorizontalListView;
import com.conny.tv.test.LazyFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tab_list)
    HorizontalListView mTab;
    @BindView(R.id.pager)
    LazyViewPager mPager;

    private SlidingMenu menu;
    private final String PIC = "http://inthecheesefactory.com/uploads/source/glidepicasso/cover.jpg";

    @Override
    protected void initLayout() {
        addView(R.layout.activity_main);
        initMenu();
    }

    @Override
    protected void initData() {
        setTitleTxt("首页");
        setLeftImage(R.mipmap.ic_person);
        initTab();

        Fragment f1 = new LiveFragment();
        Fragment f2 = new LiveFragment();

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(f1);
        fragments.add(f2);

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
        mPager.setAdapter(adapter);
    }

    class FragmentAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments;

        public FragmentAdapter(FragmentManager fm, List<Fragment> fs) {
            super(fm);
            fragments = fs;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments == null ? null : fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments == null ? 0 : fragments.size();
        }
    }

    private void initTab() {
        TabBean t1 = new TabBean();
        t1.name = "热门";
        TabBean t2 = new TabBean();
        t2.name = "精选";
        TabBean t3 = new TabBean();
        t3.name = "央视";
        TabBean t4 = new TabBean();
        t4.name = "卫视";
        TabBean t5 = new TabBean();
        t5.name = "收藏";
        TabBean t6 = new TabBean();
        t6.name = "直播";
        TabBean t7 = new TabBean();
        t7.name = "电影";

        List<TabBean> tabBeans = new ArrayList<>();
        tabBeans.add(t1);
        tabBeans.add(t2);
        tabBeans.add(t3);
        tabBeans.add(t4);
        tabBeans.add(t5);
        tabBeans.add(t6);
        tabBeans.add(t7);

        final TabAdapter adapter = new TabAdapter(this, tabBeans);
        mTab.setAdapter(adapter);
        mTab.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.selectItem(position);
            }
        });
    }

    @OnClick({R.id.left_view})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_view:
                sliding();
                break;
        }
    }

    private void sliding() {
        if (menu == null)
            return;
        menu.setSlidingEnabled(true);
        menu.toggle();
    }

    private void initMenu() {
        if (menu == null) {
            menu = new SlidingMenu(this);
            menu.setMode(SlidingMenu.LEFT);
            menu.setSlidingEnabled(false);
            menu.setTouchModeBehind(SlidingMenu.TOUCHMODE_FULLSCREEN);
            menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
            menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
            menu.setMenu(R.layout.layout_home_sliding);
            menu.setOffsetFadeDegree(0.25f);
            menu.setFadeDegree(0.35f);
            menu.setShadowWidth(15);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (menu != null && menu.isMenuShowing()) {
                menu.toggle();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}