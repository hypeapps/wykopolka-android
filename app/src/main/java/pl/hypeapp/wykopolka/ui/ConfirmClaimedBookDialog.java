package pl.hypeapp.wykopolka.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.ybq.android.spinkit.style.Wave;

import pl.hypeapp.wykopolka.App;
import pl.hypeapp.wykopolka.R;
import pl.hypeapp.wykopolka.model.Book;

public class ConfirmClaimedBookDialog extends Dialog {
    private Book book;
    private TextView bookTitle;
    private ImageView bookCover;
    private TextView qualityText;
    private SeekBar qualitySeekBar;
    private ProgressBar spinLoading;
    private Context context;
    private int quality;
    public Button confirmButton;

    public ConfirmClaimedBookDialog(Context context) {
        super(context);
        setContentView(R.layout.dialog_confirm_claim);
        this.setCancelable(true);
        this.context = context;
        this.setTitle(context.getString(R.string.dialog_confim_title));
    }

    public void setBookData(Book book) {
        this.book = book;
        initDialogContent();
    }

    public Button getConfirmButton() {
        return confirmButton;
    }

    public int getQuality() {
        return quality;
    }

    public void startLoading() {
        if (spinLoading != null) {
            Wave wave = new Wave();
            spinLoading.setIndeterminateDrawable(wave);
            spinLoading.setVisibility(View.VISIBLE);
        }
    }

    public void stopLoading() {
        if (spinLoading != null) {
            spinLoading.setVisibility(View.GONE);
        }
    }

    private void initDialogContent() {
        bookTitle = (TextView) this.findViewById(R.id.tv_book_title);
        bookTitle.setText(book.getTitle().trim());
        bookCover = (ImageView) this.findViewById(R.id.iv_book_cover);
        qualityText = (TextView) this.findViewById(R.id.tv_quality);
        spinLoading = (ProgressBar) this.findViewById(R.id.dialog_loading);
        qualitySeekBar = (SeekBar) this.findViewById(R.id.seek_bar_quality);
        qualitySeekBar.setProgress(4);
        quality = 5;
        qualitySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                qualityText.setText(String.valueOf(i + 1));
                quality = i + 1;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        confirmButton = (Button) this.findViewById(R.id.btn_confirm);
        Glide.with(context).load(App.WYKOPOLKA_IMG_HOST + book.getCover()).into(bookCover);
    }
}
