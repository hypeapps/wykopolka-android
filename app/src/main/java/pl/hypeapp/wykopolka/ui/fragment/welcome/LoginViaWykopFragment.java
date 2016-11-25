package pl.hypeapp.wykopolka.ui.fragment.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.hypeapp.wykopolka.R;
import pl.hypeapp.wykopolka.ui.activity.SignInActivity;

public class LoginViaWykopFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome_login_via_wykop, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btn_login_via_wykop)
    public void loginViaWykop() {
        startActivity(new Intent(getActivity(), SignInActivity.class));
    }
}
