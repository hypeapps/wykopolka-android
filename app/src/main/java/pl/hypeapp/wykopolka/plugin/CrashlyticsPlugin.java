package pl.hypeapp.wykopolka.plugin;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.crashlytics.android.Crashlytics;
import com.pascalwelsch.compositeandroid.activity.ActivityPlugin;

import io.fabric.sdk.android.Fabric;

public class CrashlyticsPlugin extends ActivityPlugin {
    private Crashlytics mCrashlytics;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCrashlytics = new Crashlytics();
        Fabric.with(getActivity(), mCrashlytics);
    }
}
