package pl.hypeapp.wykopolka.plugin;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.crashlytics.android.answers.SearchEvent;
import com.pascalwelsch.compositeandroid.activity.ActivityPlugin;

import io.fabric.sdk.android.Fabric;

public class AnalyticsPlugin extends ActivityPlugin {
    private Answers mAnalytics;
    private ContentViewEvent onBookEvent;
    private SearchEvent onSearchEvent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAnalytics = new Answers();
        Fabric.with(getActivity(), mAnalytics);
    }

    public void onBookEvent(String bookTitle, String bookId) {
        onBookEvent = new ContentViewEvent()
                .putContentId(bookId)
                .putContentType("Book")
                .putContentName(bookTitle);
        Answers.getInstance().logContentView(onBookEvent);
    }

    public void onSearchEvent(String query) {
        onSearchEvent = new SearchEvent()
                .putQuery(query);
        Answers.getInstance().logSearch(onSearchEvent);
    }
}
