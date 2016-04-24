package com.ioudebtcalculator;

import android.app.Application;
import android.content.Context;

import com.ioudebtcalculator.accountlist.AccountListPresenter;
import com.ioudebtcalculator.accountlist.AccountListPresenterImpl;
import com.ioudebtcalculator.newaccount.NewAccountPresenter;
import com.ioudebtcalculator.newaccount.NewAccountPresenterImpl;
import com.ioudebtcalculator.newtransaction.NewTransactionPresenter;
import com.ioudebtcalculator.newtransaction.NewTransactionPresenterImpl;
import com.ioudebtcalculator.repository.DataRepository;
import com.ioudebtcalculator.repository.sqlite.SQLiteImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides @Singleton
    public Context provideContext() {
        return application;
    }

    @Provides @Singleton
    public SQLiteImpl provideSQLiteImpl(Context context) {
        return new SQLiteImpl(context);
    }

    @Provides @Singleton
    public DataRepository provideDataRepository(Context context) {
        return provideSQLiteImpl(context);
    }

    @Provides
    public NewTransactionPresenter provideNewTransactionPresenter() {
        return new NewTransactionPresenterImpl();
    }

    @Provides
    public NewAccountPresenter provideNewAccountPresenter() {
        return new NewAccountPresenterImpl();
    }

    @Provides
    public AccountListPresenter provideAccountListPresenter() {
        return new AccountListPresenterImpl();
    }
}
