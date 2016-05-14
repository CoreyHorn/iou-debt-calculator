package com.ioudebtcalculator.newtransaction;

import com.ioudebtcalculator.network.CurrencyConverterService;
import com.ioudebtcalculator.repository.DataRepository;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NewTransactionModule {

    @Provides
    public Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(CurrencyConverterService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    public CurrencyConverterService provideCurrencyConverterService(Retrofit retrofit) {
        return retrofit.create(CurrencyConverterService.class);
    }

    @Provides
    public NewTransactionPresenter provideNewTransactionPresenter(
            DataRepository dataRepository,
            CurrencyConverterService currencyConverterService) {
        return new NewTransactionPresenterImpl(dataRepository, currencyConverterService);
    }
}
