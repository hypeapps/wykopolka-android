package pl.hypeapp.wykopolka.base;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.grandcentrix.thirtyinch.TiPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import pl.hypeapp.wykopolka.model.Book;
import pl.hypeapp.wykopolka.util.ImageUtil;
import rx.Observable;

public class BaseUploadBookPresenter<V extends BaseUploadBookView> extends TiPresenter<V> {
    protected Bitmap mCoverPhoto;
    protected boolean isLoading = false;

    @Override
    protected void onWakeUp() {
        super.onWakeUp();
        if (mCoverPhoto != null) {
            getView().setCoverBitmap(mCoverPhoto);
        }
        if (isLoading) {
            startLoading();
        } else {
            stopLoading();
        }
    }

    public void takingPhotoSuccess(Bitmap photo) {
        this.mCoverPhoto = photo;
    }

    protected boolean isInputsCompatible() {
        boolean compatible = false;
        List<String> inputs = new ArrayList<>();
        inputs.add(getView().getBookTitle());
        inputs.add(getView().getAuthor());
        inputs.add(getView().getIsbn());
        inputs.add(getView().getGenre());
        inputs.add(getView().getDescription());

        for (int i = 0; i < inputs.size(); i++) {
            if (isInputEmpty(inputs.get(i), i)) {
                compatible = false;
                break;
            } else {
                compatible = true;
            }
        }
        return compatible;
    }

    protected boolean isInputEmpty(String input, int inputIndex) {
        if (input.length() == 0 || input.trim().isEmpty()) {
            showErrorMessage(inputIndex);
            return true;
        } else {
            return false;
        }
    }

    protected void showErrorMessage(int messageIndex) {
        getView().showMessageInputEmpty(messageIndex);
    }

    protected Observable<String> decodeCoverAsync() {
        return Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return ImageUtil.encodeToBase64(mCoverPhoto, Bitmap.CompressFormat.PNG, 100);
            }
        });
    }

    protected Book createBook(String coverEncoded) {
        return new Book.BookBuilder()
                .setTitle(getView().getBookTitle().trim())
                .setAuthor(getView().getAuthor().trim())
                .setIsbn(getView().getIsbn().trim())
                .setGenre(getView().getGenre().trim())
                .setDesc(getView().getDescription().trim())
                .setRating(getView().getRating().trim())
                .setQuality(getView().getQuality().trim())
                .setCover(coverEncoded)
                .build();
    }

    @Nullable
    protected String createBookJson(Book book) {
        String json = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            json = objectMapper.writeValueAsString(book);
        } catch (JsonProcessingException e) {
            getView().showUploadError();
        }
        return json;
    }

    protected void startLoading() {
        getView().showLoading();
        isLoading = true;
    }

    protected void stopLoading() {
        getView().hideLoading();
        isLoading = false;
    }

}