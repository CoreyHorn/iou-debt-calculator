package com.ioudebtcalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.ioudebtcalculator.models.Account;
import com.ioudebtcalculator.models.Transaction;
import com.ioudebtcalculator.repository.DataRepositoryListener;
import com.ioudebtcalculator.repository.sqlite.QueryAccountsTask;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Set<Currency> currencySet = Currency.getAvailableCurrencies();
//        for (Currency currency : currencySet) {
//            Log.d("stuff" , currency.getCurrencyCode());
//        }

    }
}
