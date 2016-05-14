package com.ioudebtcalculator;

import android.app.Application;

import com.ioudebtcalculator.newtransaction.NewTransactionComponent;
import com.ioudebtcalculator.newtransaction.NewTransactionModule;

public class App extends Application {

    private static App instance;

    private AppComponent appComponent;
    private NewTransactionComponent newTransactionComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        setInstance(this);
        appComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .build();
    }

    public static App getInstance() {
        return instance;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public NewTransactionComponent getNewTransactionComponent() {
        return newTransactionComponent = appComponent.plus(new NewTransactionModule());
    }

    public void releaseNewTransactionComponent() {
        newTransactionComponent = null;
    }

    private static void setInstance(App app) {
        instance = app;
    }
}
