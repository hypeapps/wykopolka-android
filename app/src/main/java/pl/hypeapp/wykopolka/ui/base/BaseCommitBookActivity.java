package pl.hypeapp.wykopolka.ui.base;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pascalwelsch.compositeandroid.activity.CompositeActivity;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.hypeapp.wykopolka.R;
import pl.hypeapp.wykopolka.provider.FileContentProvider;
import pl.hypeapp.wykopolka.view.BaseCommitBookView;

public class BaseCommitBookActivity extends CompositeActivity implements BaseCommitBookView {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int CAMERA_RESULT = 1;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 722;
    private Bitmap mCoverBitmap = null;
    private View parentLayout;
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

    protected void initViews(View view) {
        this.parentLayout = view;
        ButterKnife.bind(view, this);
        mSeekBarQuality.setProgress(4);
        mSeekBarRating.setProgress(4);
        mSeekBarRating.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.e("seek", " " + i);
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

    private void dispatchTakePictureIntent() {
        PackageManager pm = getPackageManager();
        if (pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            i.putExtra(MediaStore.EXTRA_OUTPUT, FileContentProvider.CONTENT_URI);
            startActivityForResult(i, CAMERA_RESULT);
        } else {
            Toast.makeText(getBaseContext(), "Camera is not available", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            File out = new File(getFilesDir(), "newImage.jpg");
            if (!out.exists()) {
                Toast.makeText(getBaseContext(), "Error while capturing image", Toast.LENGTH_LONG).show();
                return;
            }
            Bitmap mBitmap = BitmapFactory.decodeFile(out.getAbsolutePath());
            mCoverBitmap = mBitmap;
            mCoverImageView.setImageBitmap(mCoverBitmap);
        }
    }

    @Override
    public void setCover(String coverUrl) {
        if (mCoverImageView != null) Glide.with(this).load(coverUrl).into(mCoverImageView);
    }

    @Override
    public void setTitle(String titile) {
        if (mBookTitleEditText != null) mBookTitleEditText.setText(titile);
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
    public void setRating(String rating) {
        if (mRatingTextView != null) mRatingTextView.setText(rating);
    }

    @Override
    public void setQuality(String quality) {
        if (mQualityTextView != null) {
            mQualityTextView.setText(quality);
        }
    }

    @Override
    public Bitmap getCover() {
        return mCoverBitmap;
    }

    @Override
    public String getTitile() {
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
    public String getRating() {
        return mRatingTextView.getText().toString();
    }

    @Override
    public String getQuality() {
        return mQualityTextView.getText().toString();
    }

    @Override
    public void showError(String message) {
        Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void hideError() {

    }
}
