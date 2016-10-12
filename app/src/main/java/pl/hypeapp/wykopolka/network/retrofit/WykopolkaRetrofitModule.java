package pl.hypeapp.wykopolka.network.retrofit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Module
public class WykopolkaRetrofitModule {
    private static final String API_URL = "http://192.168.1.1/";

    @Provides
    @Singleton
    Retrofit providesWykopolkaRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }
}
