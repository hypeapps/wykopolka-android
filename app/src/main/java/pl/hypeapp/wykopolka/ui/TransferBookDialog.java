package pl.hypeapp.wykopolka.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.ybq.android.spinkit.style.Wave;

import pl.hypeapp.wykopolka.App;
import pl.hypeapp.wykopolka.R;
import pl.hypeapp.wykopolka.model.Book;
import pl.hypeapp.wykopolka.model.PendingUser;

public class TransferBookDialog extends Dialog {
    private Book book;
    private PendingUser pendingUser;
    private ProgressBar spinLoading;
    private Context context;
    private Button confirmTransferButton;

    public TransferBookDialog(Context context) {
        super(context);
        setContentView(R.layout.dialog_transfer_book);
        this.setCancelable(true);
        this.context = context;
        this.setTitle(context.getString(R.string.dialog_transfer_book_title));
    }

    public void setDialogData(Pair<Book, PendingUser> bookPendingUserPair) {
        this.book = bookPendingUserPair.first;
        this.pendingUser = bookPendingUserPair.second;
        initDialogContent();
    }

    public Button getConfirmTransferButton() {
        return confirmTransferButton;
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
        TextView bookTitle = (TextView) this.findViewById(R.id.tv_book_title);
        bookTitle.setText(book.getTitle().trim());
        ImageView bookCover = (ImageView) this.findViewById(R.id.iv_book_cover);
        spinLoading = (ProgressBar) this.findViewById(R.id.dialog_loading);
        confirmTransferButton = (Button) this.findViewById(R.id.btn_confirm);
        Glide.with(context).load(App.WYKOPOLKA_IMG_HOST + book.getCover()).into(bookCover);
        TextView userPw = (TextView) this.findViewById(R.id.dialog_user_pw);
        userPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String privateMessageUrl = pendingUser.getPriv();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(privateMessageUrl));
                context.startActivity(intent);
            }
        });
        TextView aboutUserAddress = (TextView) this.findViewById(R.id.dialog_about_user_address);
        if (isAddressFilled()) {
            aboutUserAddress.setText(context.getString(R.string.dialog_address_content_present));
            View addressView = (View) this.findViewById(R.id.dialog_address_layout);
            addressView.setVisibility(View.VISIBLE);
            TextView name = (TextView) this.findViewById(R.id.dialog_user_name);
            name.setText(pendingUser.getName());
            TextView address = (TextView) this.findViewById(R.id.dialog_user_address);
            address.setText(pendingUser.getAddress());
            TextView postal = (TextView) this.findViewById(R.id.dialog_user_postal);
            postal.setText(pendingUser.getPostal());
            TextView city = (TextView) this.findViewById(R.id.dialog_user_city);
            city.setText(pendingUser.getCity());
        } else {
            aboutUserAddress.setText(context.getString(R.string.dialog_no_address_content));
        }
    }

    private boolean isAddressFilled() {
        return pendingUser.getAddress().length() > 0;
    }
}
