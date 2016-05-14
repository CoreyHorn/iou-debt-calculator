package com.ioudebtcalculator;

import com.ioudebtcalculator.accountlist.AccountListFragment;
import com.ioudebtcalculator.accountlist.AccountListPresenterImpl;
import com.ioudebtcalculator.newaccount.NewAccountFragment;
import com.ioudebtcalculator.newaccount.NewAccountPresenterImpl;
import com.ioudebtcalculator.newtransaction.NewTransactionComponent;
import com.ioudebtcalculator.newtransaction.NewTransactionModule;
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
    void inject(NewAccountFragment target);
    void inject(NewAccountPresenterImpl target);
    void inject(AccountListPagerAdapter target);
    void inject(AccountListFragment target);
    void inject(AccountListPresenterImpl target);

    NewTransactionComponent plus(NewTransactionModule newTransactionModule);
}
