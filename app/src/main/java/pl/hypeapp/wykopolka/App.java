package pl.hypeapp.wykopolka;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.squareup.leakcanary.LeakCanary;

import pl.hypeapp.wykopolka.network.retrofit.WykopolkaRetrofitModule;

public class App extends Application {
    public static final String WYKOPOLKA_IMG_HOST = "http://77.253.148.2/wykopolka/public/";
    public static final String WYKOPOLKA_API_URL = "http://77.253.148.2/";
    public static final String WYKOP_API_URL = "http://a.wykop.pl/";
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        Log.d("leakd", "onCreate: leakCanary");

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .wykopolkaRetrofitModule(new WykopolkaRetrofitModule())
                .build();
    }

    public AppComponent getAuthComponent(){
        return appComponent;
    }

    public static void saveToPreferences(Context context, String preferenceName, String preferenceValue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }

    public static void saveToPreferences(Context context, String preferenceName, boolean preferenceValue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(preferenceName, preferenceValue);
        editor.apply();
    }

    public static void saveToPreferences(Context context, String preferenceName, Integer preferenceValue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(preferenceName, preferenceValue);
        editor.apply();
    }

    public static String readFromPreferences(Context context, String preferenceName, String defaultValue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return sharedPreferences.getString(preferenceName, defaultValue);
    }

    public static boolean readFromPreferences(Context context, String preferenceName, boolean defaultValue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return sharedPreferences.getBoolean(preferenceName, defaultValue);
    }

    public static Integer readIntegerFromPreferences(Context context, String preferenceName, Integer defaultValue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return sharedPreferences.getInt(preferenceName, defaultValue);
    }

    public static void clearPreferences(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
