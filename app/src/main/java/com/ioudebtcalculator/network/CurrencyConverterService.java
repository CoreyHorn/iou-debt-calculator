package com.ioudebtcalculator.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CurrencyConverterService {

    String BASE_URL = "http://api.fixer.io/";

    @GET("latest")
    Call<CurrencyResult> getCurrencyResult(@Query("base") String base,
                                           @Query("symbols") String symbols);

}
