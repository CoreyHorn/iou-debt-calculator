package com.ioudebtcalculator;

import com.ioudebtcalculator.repository.sqlite.InsertAccountsTask;
import com.ioudebtcalculator.repository.sqlite.InsertTransactionsTask;
import com.ioudebtcalculator.repository.sqlite.QueryAccountsTask;
import com.ioudebtcalculator.repository.sqlite.QueryTransactionsTask;
import com.ioudebtcalculator.repository.sqlite.UpdateAccountsTask;
import com.ioudebtcalculator.repository.sqlite.UpdateTransactionsTask;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = { AppModule.class })
public interface AppComponent {
    void inject(QueryAccountsTask target);
    void inject(QueryTransactionsTask target);
    void inject(InsertAccountsTask target);
    void inject(InsertTransactionsTask target);
    void inject(UpdateAccountsTask target);
    void inject(UpdateTransactionsTask target);
}
