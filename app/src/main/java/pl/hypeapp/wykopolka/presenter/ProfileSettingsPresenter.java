package pl.hypeapp.wykopolka.presenter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.grandcentrix.thirtyinch.TiPresenter;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterSubscriptionHandler;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterUtils;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import pl.hypeapp.wykopolka.model.User;
import pl.hypeapp.wykopolka.network.api.WykopolkaApi;
import pl.hypeapp.wykopolka.network.retrofit.DaggerRetrofitComponent;
import pl.hypeapp.wykopolka.network.retrofit.RetrofitComponent;
import pl.hypeapp.wykopolka.util.HashUtil;
import pl.hypeapp.wykopolka.view.ProfileSettingsView;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ProfileSettingsPresenter extends TiPresenter<ProfileSettingsView> {
    private static final int NOTIFICATION_DISABLED = 0;
    private static final int NOTIFICATION_MAIL_ONLY = 1;
    private static final int NOTIFICATION_PM_ONLY = 2;
    private static final int NOTIFICATION_ALL_ENABLED = 3;
    private static final int MAIL_CONFIRMED = 1;
    private static final int PROFILE_FULLNAME = 0;
    private static final int PROFILE_ADDRESS = 1;
    private static final int PROFILE_POSTAL_CODE = 2;
    private static final int PROFILE_CITY = 3;
    private User mUser;
    private RxTiPresenterSubscriptionHandler rxHelper = new RxTiPresenterSubscriptionHandler(this);
    private String mAccountKey;
    private WykopolkaApi mWykopolkaApi;
    @Inject
    @Named("wykopolkaApi")
    Retrofit mRetrofit;
    RetrofitComponent mRetrofitComponent;

    public ProfileSettingsPresenter(String accountKey) {
        this.mAccountKey = accountKey;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        mRetrofitComponent = DaggerRetrofitComponent.builder().build();
        mRetrofitComponent.inject(this);
        mWykopolkaApi = mRetrofit.create(WykopolkaApi.class);
    }

    @Override
    protected void onWakeUp() {
        super.onWakeUp();
        loadUserSettings(mAccountKey);
    }

    public void submitSettings() {
        saveUserSettings(mAccountKey);
    }

    public void retryLoadProfileSettings() {
        getView().hideError();
        getView().showLoading();
        loadUserSettings(mAccountKey);
    }

    private void loadUserSettings(String accountKey) {
        String apiSign = HashUtil.generateApiSign(accountKey);
        rxHelper.manageSubscription(
                mWykopolkaApi.getUser(accountKey, apiSign)
                        .compose(RxTiPresenterUtils.<User>deliverLatestToView(this))
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<User>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                handleError();
                            }

                            @Override
                            public void onNext(User user) {
                                mUser = user;
                                setUserSettingsToView(user);
                            }
                        })
        );
    }

    private void handleError() {
        getView().hideLoading();
        getView().hideSettingsCard();
        getView().showError();
    }


    private void setUserSettingsToView(User userSettings) {
        getView().hideError();
        getView().hideLoading();
        getView().setProfileAddress(userSettings);
        getView().setProfileEmail(userSettings);
        setProfileNotifications(userSettings);
        getView().showSettingsCard();
    }

    private void setProfileNotifications(User userSettings) {
        int notificationType = userSettings.getNotifyType();
        int isMailConfirmed = userSettings.getIsMailConfirmed();
        switch (notificationType) {
            case NOTIFICATION_DISABLED:
                getView().setEmailNotificationsOff();
                getView().setPmNotificationsOff();
                break;
            case NOTIFICATION_MAIL_ONLY:
                getView().setPmNotificationsOff();
                getView().setEmailNotificationsOn();
                break;
            case NOTIFICATION_PM_ONLY:
                getView().setPmNotificationsOn();
                getView().setEmailNotificationsOff();
                break;
            case NOTIFICATION_ALL_ENABLED:
                getView().setPmNotificationsOn();
                getView().setEmailNotificationsOn();
                break;
            default:
                getView().setEmailNotificationsOff();
                getView().setPmNotificationsOn();
        }
        if (isMailConfirmed == MAIL_CONFIRMED) {
            getView().setEmailNotificationsEnabled();
        } else {
            getView().setEmailNotificationsDisabled();
        }
    }

    private void saveUserSettings(String accountKey) {
        if (isAddressFilled() && isAddressEmailFilled()) {
            if (isValidEmail(getView().getProfileEmail())) {
                User editedUser = editUser();
                String userJson = createUserJson(editedUser);
                if (userJson != null) {
                    String apiSign = HashUtil.generateApiSign(accountKey, userJson);

                    rxHelper.manageSubscription(
                            mWykopolkaApi.updateUser(accountKey, userJson, apiSign)
                                    .compose(RxTiPresenterUtils.<User>deliverLatestToView(this))
                                    .subscribeOn(Schedulers.newThread())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Subscriber<User>() {
                                        @Override
                                        public void onCompleted() {

                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            getView().showMessageSaveSettingsFail();
                                        }

                                        @Override
                                        public void onNext(User user) {
                                            mUser = user;
                                            setUserSettingsToView(user);
                                            getView().showMessageSaveSettingsSuccess();
                                        }
                                    })
                    );
                }
            }
        }
    }

    private boolean isAddressFilled() {
        List<String> address = getView().getProfileAddress();
        for (String addressItem : address) {
            if (addressItem.trim().isEmpty()) {
                getView().showMessageAddressIncorrect();
                return false;
            }
        }
        return true;
    }

    private boolean isAddressEmailFilled() {
        String email = getView().getProfileEmail();
        if (!mUser.getMail().isEmpty()) {
            if (email.isEmpty()) {
                getView().showMessageAddressEmailIncorrect();
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    private boolean isValidEmail(CharSequence target) {
        if (!TextUtils.isEmpty(target)) {
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()) {
                return true;
            } else {
                getView().showMessageAddressEmailIncorrect();
                return false;
            }
        } else {
            return true;
        }
    }

    private User editUser() {
        User user = mUser;
        user.setFullName(getView().getProfileAddress().get(PROFILE_FULLNAME));
        user.setAdress(getView().getProfileAddress().get(PROFILE_ADDRESS));
        user.setPostal(getView().getProfileAddress().get(PROFILE_POSTAL_CODE));
        user.setCity(getView().getProfileAddress().get(PROFILE_CITY));
        user.setMail(getView().getProfileEmail());
        user.setNotifyType(getNotificationType());
        return user;
    }

    private Integer getNotificationType() {
        if (mUser.getIsMailConfirmed() == MAIL_CONFIRMED) {
            if (getView().getCheckedEmail() && getView().getCheckedPm()) {
                return NOTIFICATION_ALL_ENABLED;
            } else if (getView().getCheckedEmail() && !getView().getCheckedPm()) {
                return NOTIFICATION_MAIL_ONLY;
            } else if (!getView().getCheckedEmail() && getView().getCheckedPm()) {
                return NOTIFICATION_PM_ONLY;
            } else if (!getView().getCheckedEmail() && !getView().getCheckedEmail()) {
                return NOTIFICATION_DISABLED;
            }
        } else {
            if (getView().getCheckedPm()) {
                return NOTIFICATION_PM_ONLY;
            } else {
                return NOTIFICATION_DISABLED;
            }
        }
        return null;
    }

    @Nullable
    private String createUserJson(User user) {
        String json = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            json = objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            getView().showMessageSaveSettingsFail();
        }
        return json;
    }

}