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
import com.conny.tv.api.callback.ApiCallback;
import com.conny.tv.bean.ResultBean;
import com.conny.tv.material.base.BaseActivity;
import com.conny.tv.material.view.HorizontalListView;
import com.conny.tv.test.LazyFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tab_list)
    HorizontalListView mTab;
    @BindView(R.id.pager)
    LazyViewPager mPager;

    private TabAdapter mAdapter;
    private List<TabBean> mTabs;

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
//
//        Fragment f1 = new LiveFragment();
//        Fragment f2 = new LiveFragment();
//
//        List<Fragment> fragments = new ArrayList<>();
//        fragments.add(f1);
//        fragments.add(f2);

    }

    private void initTab() {
        showProgress(false);
        LiveApi.listTab(new ApiCallback<TabBean>() {

            @Override
            public void onResponse(Call<ResultBean<TabBean>> call, Response<ResultBean<TabBean>> response) {
                closeProgress();
                if (response != null) {
                    ResultBean<TabBean> result = response.body();
                    if (result != null && result.errorCode == 0) {
                        mTabs = result.rows;
                        mAdapter = new TabAdapter(MainActivity.this, mTabs);
                        mTab.setAdapter(mAdapter);
                        mTab.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                mAdapter.selectItem(position);
                                mPager.setCurrentItem(position, false);
                            }
                        });
                        initFragments();
                    }
                }

            }

            @Override
            public void onFailure(Call<ResultBean<TabBean>> call, Throwable t) {
                closeProgress();
            }
        });


    }

    private void initFragments() {
        if (mTab != null && mTabs.size() > 0) {
            List<Fragment> fragments = new ArrayList<>();
            for (TabBean tab : mTabs) {
                LiveFragment fragment = new LiveFragment();
                fragment.setType(tab.type);
                fragments.add(fragment);
            }

            FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
            mPager.setAdapter(adapter);
            mPager.setOnPageChangeListener(new LazyViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if (mAdapter != null) {
                        mAdapter.selectItem(position);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
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
}
