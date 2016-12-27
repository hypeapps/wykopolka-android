package pl.hypeapp.wykopolka.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.pascalwelsch.compositeandroid.activity.CompositeActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.hypeapp.wykopolka.R;

public class FaqActivity extends CompositeActivity {
    private boolean isIntentFromWelcome;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        ButterKnife.bind(this);
        isIntentFromWelcome = getIntent().getBooleanExtra("from_welcome", false);
    }

    @Override
    @OnClick({R.id.btn_back, R.id.btn_back_bottom, R.id.faq_text_back})
    public void onBackPressed() {
        if (isIntentFromWelcome) {
            super.onBackPressed();
        } else {
            startActivity(new Intent(this, DashboardActivity.class));
        }
        finish();
    }

}
