package com.ioudebtcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ioudebtcalculator.newtransaction.NewTransactionActivity;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    FloatingActionButton fabNew;

    private View.OnClickListener newTransactionListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, NewTransactionActivity.class);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.vwpAccountList);
        fabNew = (FloatingActionButton) findViewById(R.id.fabNew);

        if (fabNew != null) {
            fabNew.setOnClickListener(newTransactionListener);
        }

        viewPager.setAdapter(new AccountListPagerAdapter(getSupportFragmentManager()));

        if (savedInstanceState != null) {
            return;
        }

    }
}
