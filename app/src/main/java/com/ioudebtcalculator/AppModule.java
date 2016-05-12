package com.ioudebtcalculator;

import android.app.Application;
import android.content.Context;

import com.ioudebtcalculator.accountlist.AccountListPresenter;
import com.ioudebtcalculator.accountlist.AccountListPresenterImpl;
import com.ioudebtcalculator.newaccount.NewAccountPresenter;
import com.ioudebtcalculator.newaccount.NewAccountPresenterImpl;
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

    //TODO: Scope these two different providers. Scope the raw SQLiteImpl to the repo package.
    @Provides @Singleton
    public SQLiteImpl provideSQLiteImpl(Context context) {
        return new SQLiteImpl(context);
    }

    @Provides @Singleton
    public DataRepository provideDataRepository(Context context) {
        return provideSQLiteImpl(context);
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
