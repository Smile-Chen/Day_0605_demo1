package bway.com.day_0530_lianxi;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import bway.com.day_0530_lianxi.adapter.MyFragmentAdapter;
import bway.com.day_0530_lianxi.fragments.FenLeiFragment;
import bway.com.day_0530_lianxi.fragments.ShouYeFragment;
import bway.com.day_0530_lianxi.fragments.WodeFragment;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewpager;
    private RadioGroup radio;

    private List<Fragment> list=new ArrayList<>();
    private ShouYeFragment f1;
    private FenLeiFragment f2;
    private WodeFragment f3;
    private MyFragmentAdapter mf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
   //初始化页面
   initViews();

    }

    private void initViews() {

        viewpager = findViewById(R.id.main_viewpager);
        radio = findViewById(R.id.main_radio);

        f1 = new ShouYeFragment();
        f2 = new FenLeiFragment();
        f3 = new WodeFragment();

        list.add(f1);
        list.add(f2);
        list.add(f3);


        mf = new MyFragmentAdapter(getSupportFragmentManager(), list);
        viewpager.setAdapter(mf);
        radio.check(R.id.main_shouye);
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch (position){

                    case 0:
                        radio.check(R.id.main_shouye);
                        break;

                    case 1:
                        radio.check(R.id.main_fenlei);
                        break;

                    case 2:
                        radio.check(R.id.main_wode);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){

                    case R.id.main_shouye:
                        viewpager.setCurrentItem(0);
                        break;
                    case R.id.main_fenlei:
                        viewpager.setCurrentItem(1);
                        break;
                    case R.id.main_wode:
                        viewpager.setCurrentItem(2);
                        break;
                }
            }
        });
    }
}
