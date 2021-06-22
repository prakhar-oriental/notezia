package com.example.notezia;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class fragmentmanager extends FragmentPagerAdapter
{
    private int tabno;

    public fragmentmanager(@NonNull FragmentManager fm, int behavior, int tabno) {
        super(fm, behavior);
        this.tabno = tabno;
    }

    @NonNull
    @Override
    public Fragment getItem(int position)
    {
        switch (position)
        {
            case 0:return new Fragment1();
            case 1:return new Fragment2();
            default:return  null;
        }
    }

    @Override
    public int getCount() {
        return tabno;
    }
}
