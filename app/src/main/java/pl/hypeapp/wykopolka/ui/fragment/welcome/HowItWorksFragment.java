package pl.hypeapp.wykopolka.ui.fragment.welcome;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.hypeapp.wykopolka.R;
import pl.hypeapp.wykopolka.ui.activity.FaqActivity;

public class HowItWorksFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome_how_it_works, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btn_open_faq)
    public void openFaq() {
        startActivity(new Intent(getActivity(), FaqActivity.class));
    }
}
