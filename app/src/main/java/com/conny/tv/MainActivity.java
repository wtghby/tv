package com.conny.tv;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.conny.tv.bean.PersonBean;
import com.conny.tv.material.base.BaseActivity;
import com.conny.tv.material.dao.Dao;
import com.conny.tv.material.dialog.CommonDialog;
import com.conny.tv.test.FrameApi;
import com.conny.tv.test.IpBean;
import com.conny.tv.test.LazyFragment;
import com.conny.tv.test.PullActivity;
import com.conny.library.lazy.LazyViewPager;
import com.conny.library.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    @BindView(R.id.text)
    TextView mText;
    @BindView(R.id.image)
    ImageView mImage;
    @BindView(R.id.pager)
    LazyViewPager mPager;

    private SlidingMenu menu;

    private final String URL = "http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json";
    private final String PIC = "http://inthecheesefactory.com/uploads/source/glidepicasso/cover.jpg";

    @Override
    protected void initLayout() {
        addView(R.layout.activity_main);
        initMenu();
    }

    @Override
    protected void initData() {
        mText.setText("hhhhhhhhhhhhh");
        Glide.with(this)
                .load(PIC)
                .into(mImage);

        LazyFragment f1 = new LazyFragment();
        f1.setId(1);

        LazyFragment f2 = new LazyFragment();
        f2.setId(2);

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

    @OnClick({R.id.click, R.id.query})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.click:
//                req();
//                pull();
//                sliding();
//                showDialog();
//                showProgress(true);
                save();
                break;
            case R.id.query:
                query();
                break;
        }
    }

    private void save() {
        PersonBean person = new PersonBean();
        person.name = "张三";
        person.age = 22;
        person.adderss = "sdsdasa";

        Dao.insert(person);
//        person.save();
    }

    private void query() {
        List<PersonBean> persons = Dao.queryAll();
        if (persons == null)
            return;
        PersonBean person = persons.get(0);
        mText.setText("size = " + persons.size() + "--" + person.name + "---" + person.age + "---" + person.adderss);
    }

    private void showDialog() {
//        LoadingUtils.show(this);
        final CommonDialog dialog = new CommonDialog(this, "dasdsdasssd", "title");
        dialog.setNegativeButton(R.string.yes, new CommonDialog.DialogOnClickListener() {
            @Override
            public void onClick() {
                dialog.dismiss();
            }
        });

        dialog.setPositiveButton(R.string.no, new CommonDialog.DialogOnClickListener() {
            @Override
            public void onClick() {
                dialog.dismiss();
            }
        });
//        dialog.set
        dialog.show();
    }

    private void pull() {
        Intent intent = new Intent(this, PullActivity.class);
        startActivity(intent);
    }

    private void req() {
        FrameApi.mass("json", new Callback<IpBean>() {
            @Override
            public void onResponse(Call<IpBean> call, Response<IpBean> response) {
                IpBean bean = response.body();
                if (bean != null) {
                    mText.setText(bean.country + " " + bean.city);
                }

            }

            @Override
            public void onFailure(Call<IpBean> call, Throwable t) {
                mText.setText("error");
            }
        });
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
