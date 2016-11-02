package pl.hypeapp.wykopolka.view;

import net.grandcentrix.thirtyinch.TiView;

import pl.hypeapp.wykopolka.model.User;
import retrofit2.Response;

public interface SignInView extends TiView {

    void saveAndLoginUser(Response<User> responseUser, String accountKey);

    void showSuccessLoginInfo();
}
