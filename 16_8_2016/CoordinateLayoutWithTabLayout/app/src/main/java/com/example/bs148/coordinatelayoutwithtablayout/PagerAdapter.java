package com.example.bs148.coordinatelayoutwithtablayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by BS148 on 8/16/2016.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    int tabNumbers;

    public PagerAdapter(FragmentManager fm, int tabNumbers) {
        super(fm);
        this.tabNumbers=tabNumbers;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                PhoneTab phoneTab=new PhoneTab();
                return phoneTab;
            case 1:
                ContactTab contactTab=new ContactTab();
                return contactTab;
            case 2:
                MessageTab messageTab=new MessageTab();
                return messageTab;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabNumbers;
    }
}
