package com.ioudebtcalculator;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

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

    @Provides
    public SQLiteDatabase provideWritableDatabase(SQLiteImpl sqLite) {
        return sqLite.getWritableDatabase();
    }
}
