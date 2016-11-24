package pl.hypeapp.wykopolka.network.retrofit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Module
public class WykopolkaRetrofitModule {
    private static final String WYKOPOLKA_API_URL = "http://77.253.145.248/";
    private static final String WYKOP_API_URL = "http://a.wykop.pl/";

    @Provides
    @Singleton
    @Named("wykopolkaApi")
    Retrofit providesWykopolkaApiRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(WYKOPOLKA_API_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    @Named("wykopApi")
    Retrofit providesWykopApiRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(WYKOP_API_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }
}
