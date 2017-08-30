package com.hamom.bbtest.di.modules;

import com.hamom.bbtest.data.network.ApiService;
import com.hamom.bbtest.utils.AppConfig;
import dagger.Module;
import dagger.Provides;
import java.util.concurrent.TimeUnit;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hamom on 30.08.17.
 */

@Module
public class NetworkModule {

  @Provides
  @Singleton
  ApiService provideRestService(Retrofit retrofit) {
    return retrofit.create(ApiService.class);
  }

  @Singleton
  @Provides
  Retrofit provideRetrofit(OkHttpClient client) {
    return createRetrofit(client);
  }

  @Singleton
  @Provides
  OkHttpClient provideOkHttpClient() {
    return createClient();
  }

  private Retrofit createRetrofit(OkHttpClient client) {
    return new Retrofit.Builder().baseUrl(AppConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build();
  }

  private OkHttpClient createClient() {
    return new OkHttpClient.Builder().addInterceptor(
        new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY))
        .connectTimeout(AppConfig.MAX_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
        .readTimeout(AppConfig.MAX_READ_TIMEOUT, TimeUnit.MILLISECONDS)
        .writeTimeout(AppConfig.MAX_WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
        .build();
  }
}
