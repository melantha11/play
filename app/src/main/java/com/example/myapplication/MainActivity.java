package com.example.myapplication;//包的名字

//引入的库

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


//声明类
public class MainActivity extends AppCompatActivity {
    private String []tabHeaderStrings = {"Books","News","Tencent maps"};
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);                 //调用父类的方法
        setContentView(R.layout.activity_main3);             //设置布局；R->res

        ViewPager2 viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        //创建适配器
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(),getLifecycle());
        viewPager.setAdapter(fragmentAdapter);
        //将TabLayout和ViewPager2进行关联
        new TabLayoutMediator(tabLayout, viewPager,
                (tab,position) -> tab.setText(tabHeaderStrings[position])
        ).attach();
    }

    private class FragmentAdapter extends FragmentStateAdapter {
        private static final int NUM_TABS = 3;
        public FragmentAdapter (FragmentManager fragmentManager,Lifecycle lifecycle){
            super(fragmentManager,lifecycle);
        }

        public Fragment createFragment(int position){
            switch(position){
                case 0:
                    return new ShoppingListFragment();
                case 1:
                    return new WebViewFragment();
                case 2:
                    return new TencentMapsFragment();
                default:
                    return null;
            }
        }
        @Override
        public int getItemCount() {
            return NUM_TABS;
        }
    }
}
