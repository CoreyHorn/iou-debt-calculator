package com.ioudebtcalculator.newtransaction;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ioudebtcalculator.R;

public class NewTransactionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newtransaction_activity);
        if (savedInstanceState == null) {

            Integer accountId = null;

            Bundle extras = getIntent().getExtras();

            if (extras != null) {
                accountId = extras.getInt(NewTransactionFragment.KEY_ACCOUNT_ID);
            }

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.llyRoot, NewTransactionFragment.newInstance(accountId))
                    .commit();
        }
    }
}
