package com.ioudebtcalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ioudebtcalculator.newaccount.NewAccountFragment;
import com.ioudebtcalculator.newtransaction.NewTransactionFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            return;
        }

//        NewTransactionFragment newTransactionFragment = NewTransactionFragment.newInstance(null);
//        getSupportFragmentManager()
//                .beginTransaction()
//                .add(R.id.fragmentContainer, newTransactionFragment)
//                .commit();

        NewAccountFragment newAccountFragment = NewAccountFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentContainer, newAccountFragment)
                .commit();

    }
}
