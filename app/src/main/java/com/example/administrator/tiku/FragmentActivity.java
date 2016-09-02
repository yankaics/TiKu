package com.example.administrator.tiku;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import adapter.MyAdapter;
import fragment.Fragment_1;
import fragment.Fragment_2;
import fragment.Fragment_3;

@ContentView(R.layout.activity_fragment)
public class FragmentActivity extends AppCompatActivity {
    @ViewInject(R.id.vp_01)
    private ViewPager viewPager;

    @ViewInject(R.id.iv_dian_1)
    private ImageView dian_1;

    @ViewInject(R.id.iv_dian_2)
    private ImageView dian_2;

    @ViewInject(R.id.iv_dian_3)
    private ImageView dian_3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);



        List<Fragment> list = new ArrayList<Fragment>();
        list.add(new Fragment_1());
        list.add(new Fragment_2());
        list.add(new Fragment_3());

        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager(),list));



        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                dian_1.setImageResource(R.mipmap.page_indicator_focused);
                dian_2.setImageResource(R.mipmap.page_indicator_focused);
                dian_3.setImageResource(R.mipmap.page_indicator_focused);
                switch (position){
                    case 0:{
                        dian_1.setImageResource(R.mipmap.page_indicator_unfocused);

                    }
                    break;
                    case 1:{
                        dian_2.setImageResource(R.mipmap.page_indicator_unfocused);
                    }
                    break;
                    case 2:{
                        dian_3.setImageResource(R.mipmap.page_indicator_unfocused);
                    }
                    break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}
