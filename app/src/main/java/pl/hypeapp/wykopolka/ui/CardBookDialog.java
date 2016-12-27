package pl.hypeapp.wykopolka.ui;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

import pl.hypeapp.wykopolka.R;
import pl.hypeapp.wykopolka.model.Book;

public class CardBookDialog extends Dialog {
    private static final String WYKOPOLKA_BOOK_HOST_URL = "wykopolka.pl/book/";
    private Book book;
    private TextView bookId;
    private TextView bookTitle;
    private TextView bookAuthor;
    private TextView bookGenre;
    private TextView bookUrl;
    public Button dismissButton;

    public CardBookDialog(Context context) {
        super(context);
        setContentView(R.layout.dialog_card_book);
        this.setCancelable(true);
        this.setTitle(context.getString(R.string.dialog_card_title));
    }

    public void setBookData(Book book) {
        this.book = book;
        initDialogContent();
    }

    public Button getDismissButton() {
        return dismissButton;
    }

    private void initDialogContent() {
        bookId = (TextView) this.findViewById(R.id.tv_book_id);
        bookId.setText(book.getBookId());
        bookTitle = (TextView) this.findViewById(R.id.tv_book_title);
        bookTitle.setText(book.getTitle().trim());
        bookAuthor = (TextView) this.findViewById(R.id.tv_book_author);
        bookAuthor.setText(book.getAuthor().trim());
        bookGenre = (TextView) this.findViewById(R.id.tv_book_genre);
        bookGenre.setText(book.getGenre().trim());
        bookUrl = (TextView) this.findViewById(R.id.tv_book_url);
        String bookLink = WYKOPOLKA_BOOK_HOST_URL + book.getBookId();
        bookUrl.setText(bookLink);
        dismissButton = (Button) this.findViewById(R.id.btn_dismiss);
    }
}
