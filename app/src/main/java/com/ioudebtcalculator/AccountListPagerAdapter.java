package com.ioudebtcalculator;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ioudebtcalculator.accountlist.AccountListFragment;
import com.ioudebtcalculator.repository.DataRepository;

import javax.inject.Inject;

public class AccountListPagerAdapter extends FragmentStatePagerAdapter {

    @Inject
    DataRepository dataRepository;

    private static final int POSITION_ALL = 0;
    private static final int POSITION_DEBTS = 1;
    private static final int POSITION_LOANS = 2;

    public AccountListPagerAdapter(FragmentManager fm) {
        super(fm);
        App.getInstance().getAppComponent().inject(this);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case POSITION_ALL:
                return AccountListFragment.newInstance(AccountListFragment.TYPE_DEFAULT);
            case POSITION_DEBTS:
                return AccountListFragment.newInstance(AccountListFragment.TYPE_DEBTS);
            case POSITION_LOANS:
                return AccountListFragment.newInstance(AccountListFragment.TYPE_LOANS);
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
