package pl.hypeapp.wykopolka.base;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.grandcentrix.thirtyinch.TiActivity;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.hypeapp.wykopolka.App;
import pl.hypeapp.wykopolka.R;
import pl.hypeapp.wykopolka.model.Book;
import pl.hypeapp.wykopolka.provider.FileContentProvider;
import pl.hypeapp.wykopolka.ui.activity.BookActivity;
import pl.hypeapp.wykopolka.util.BuildUtil;
import pl.hypeapp.wykopolka.util.ImageUtil;

public class BaseUploadBookActivity<P extends BaseUploadBookPresenter<V>, V extends BaseUploadBookView>
        extends TiActivity<BaseUploadBookPresenter<V>, V> implements BaseUploadBookView {
    private static final int CAMERA_RESULT = 1;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 722;
    @BindView(R.id.iv_book_cover) ImageView mCoverImageView;
    @BindView(R.id.et_book_title) EditText mBookTitleEditText;
    @BindView(R.id.et_book_author) EditText mBookAuthorEditText;
    @BindView(R.id.et_book_isbn) EditText mBookIsbnEditText;
    @BindView(R.id.et_book_genre) EditText mBookGenreEditText;
    @BindView(R.id.et_book_description) EditText mBookDescriptionEditText;
    @BindView(R.id.seek_bar_rating) SeekBar mSeekBarRating;
    @BindView(R.id.tv_rating) TextView mRatingTextView;
    @BindView(R.id.seek_bar_quality) SeekBar mSeekBarQuality;
    @BindView(R.id.tv_quality) TextView mQualityTextView;
    @BindView(R.id.scroll_book_layout) View parentLayout;
    @BindView(R.id.loading_layout) View loadingLayout;

    @NonNull
    @Override
    public BaseUploadBookPresenter<V> providePresenter() {
        return new BaseUploadBookPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_upload_book);
        ButterKnife.bind(this);
        initSeekBars();
    }

    private void initSeekBars() {
        mSeekBarQuality.setProgress(4);
        mSeekBarRating.setProgress(4);
        mSeekBarRating.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mRatingTextView.setText(String.valueOf(i + 1));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mSeekBarQuality.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mQualityTextView.setText(String.valueOf(i + 1));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    @OnClick(R.id.btn_take_photo)
    public void takeCoverPhoto() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
        } else {
            dispatchTakePictureIntent();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            }
        }
    }

    private void dispatchTakePictureIntent() {
        PackageManager pm = getPackageManager();
        if (pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            i.putExtra(MediaStore.EXTRA_OUTPUT, FileContentProvider.CONTENT_URI);
            startActivityForResult(i, CAMERA_RESULT);
        } else {
            showError(getString(R.string.error_camera_unavailable));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_RESULT && resultCode == RESULT_OK) {
            File out = new File(getFilesDir(), "newImage.jpg");
            if (!out.exists()) {
                showError(getString(R.string.error_camer_while_taking_photo));
                return;
            }
            Bitmap scaledPhoto = ImageUtil.decodeWithScaling(out.getAbsolutePath());
            getPresenter().takingPhotoSuccess(scaledPhoto);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void setCover(String coverUrl) {
        if (mCoverImageView != null)
            Glide.with(this).load(App.WYKOPOLKA_IMG_HOST + coverUrl).into(mCoverImageView);
    }

    @Override
    public void setCoverBitmap(Bitmap photo) {
        if (mCoverImageView != null) mCoverImageView.setImageBitmap(photo);
    }

    @Override
    public void setTitle(String title) {
        if (mBookTitleEditText != null) mBookTitleEditText.setText(title);
    }

    @Override
    public void setAuthor(String author) {
        if (mBookAuthorEditText != null) mBookAuthorEditText.setText(author);
    }

    @Override
    public void setIsbn(String isbn) {
        if (mBookIsbnEditText != null) mBookIsbnEditText.setText(isbn);
    }

    @Override
    public void setGenre(String genre) {
        if (mBookGenreEditText != null) mBookGenreEditText.setText(genre);
    }

    @Override
    public void setDescription(String description) {
        if (mBookDescriptionEditText != null) mBookDescriptionEditText.setText(description);
    }

    @Override
    public void setRating(int rating) {
        if (mRatingTextView != null && mSeekBarRating != null) {
            mRatingTextView.setText(String.valueOf(rating));
            mSeekBarRating.setProgress(rating - 1);
        }
    }

    @Override
    public void setQuality(int quality) {
        if (mQualityTextView != null && mSeekBarQuality != null) {
            mQualityTextView.setText(String.valueOf(quality));
            mSeekBarQuality.setProgress(quality - 1);
        }
    }

    @Override
    public String getBookTitle() {
        return mBookTitleEditText.getText().toString();
    }

    @Override
    public String getAuthor() {
        return mBookAuthorEditText.getText().toString();
    }

    @Override
    public String getDescription() {
        return mBookDescriptionEditText.getText().toString();
    }

    @Override
    public String getIsbn() {
        return mBookIsbnEditText.getText().toString();
    }

    @Override
    public String getGenre() {
        return mBookGenreEditText.getText().toString();
    }

    @Override
    public int getRating() {
        return Integer.parseInt(mRatingTextView.getText().toString());
    }

    @Override
    public int getQuality() {
        return Integer.parseInt(mQualityTextView.getText().toString());
    }

    @Override
    public void showMessageInputEmpty(int messageIndex) {
        String message;
        switch (messageIndex) {
            case 0:
                message = getString(R.string.error_edit_text_title_empty);
                break;
            case 1:
                message = getString(R.string.error_edit_text_author_empty);
                break;
            case 2:
                message = getString(R.string.error_edit_text_isbn_empty);
                break;
            case 3:
                message = getString(R.string.error_edit_text_genre_empty);
                break;
            case 4:
                message = getString(R.string.error_edit_text_desc_empty);
                break;
            default:
                message = getString(R.string.error_message_default);
        }
        Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showLoading() {
        if (loadingLayout != null) {
            loadingLayout.setVisibility(View.VISIBLE);
        }
        if (parentLayout != null) {
            parentLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void hideLoading() {
        if (loadingLayout != null) {
            loadingLayout.setVisibility(View.GONE);
        }
        if (parentLayout != null) {
            parentLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void uploadingBookSuccessful(Book book) {
        startBookActivity(book);
    }

    public void startBookActivity(Book book) {
        Intent intentBookActivity = new Intent(this, BookActivity.class);
        intentBookActivity.putExtra("book", book);

        if (BuildUtil.isMinApi21()) {
            String transitionName = getString(R.string.transition_book_cover);
            Pair<View, String> p1 = Pair.create((View) mCoverImageView, transitionName);
            ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(this, p1);
            startActivity(intentBookActivity, transitionActivityOptions.toBundle());
        } else {
            startActivity(intentBookActivity);
        }
    }

    @Override
    public void showError(String message) {
        Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showUploadError() {
        Snackbar.make(parentLayout, R.string.error_message_default, Snackbar.LENGTH_LONG).show();
    }

}
