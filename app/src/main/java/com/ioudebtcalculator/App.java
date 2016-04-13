package com.ioudebtcalculator;

import android.app.Application;

public class App extends Application {

    private static App instance;

    private AppComponent appComponent;

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

    private static void setInstance(App app) {
        instance = app;
    }
}
