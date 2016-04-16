package com.ioudebtcalculator;

import android.app.Application;
import android.content.Context;

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
}
