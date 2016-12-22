package pl.hypeapp.wykopolka.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.hypeapp.wykopolka.App;
import pl.hypeapp.wykopolka.R;
import pl.hypeapp.wykopolka.model.Book;
import rx.Observable;
import rx.subjects.PublishSubject;

public class WishListRecyclerAdapter extends RecyclerView.Adapter<WishListRecyclerAdapter.WishListRecyclerHolder> {
    private static final String WYKOPOLKA_IMG_HOST = App.WYKOPOLKA_IMG_HOST;
    private final PublishSubject<WishListRecyclerHolder> onBookClickSubject = PublishSubject.create();
    private final PublishSubject<Integer> onConfirmClickSubject = PublishSubject.create();
    private LayoutInflater mLayoutInflater;
    private List<Book> mDataSet = Collections.emptyList();
    private Context mContext;

    public WishListRecyclerAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        this.mContext = context;
    }

    @Override
    public WishListRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.wish_list_row, parent, false);
        return new WishListRecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(WishListRecyclerHolder booksRecyclerHolder, final int position) {
        Book current = mDataSet.get(position);
        final WishListRecyclerHolder holder = booksRecyclerHolder;
        holder.bookTitle.setText(current.getTitle());
        Glide.with(mContext)
                .load(WYKOPOLKA_IMG_HOST + current.getCover())
                .placeholder(R.drawable.default_book_cover)
                .error(R.drawable.default_book_cover)
                .override(300, 400)
                .thumbnail(0.9f)
                .into(holder.bookThumbnail);
        holder.bookThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBookClickSubject.onNext(holder);
            }
        });
        if (current.getTransferId() != null) {
            holder.confirmReceipt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onConfirmClickSubject.onNext(position);
                }
            });
        } else {
            holder.confirmReceipt.setVisibility(View.GONE);
            holder.inQueryTextView.setVisibility(View.VISIBLE);
        }
    }

    public Observable<WishListRecyclerHolder> getOnBookClick() {
        return onBookClickSubject.asObservable();
    }

    public Observable<Integer> getOnConfirmClick() {
        return onConfirmClickSubject.asObservable();
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void setData(List<Book> books) {
        mDataSet = books;
        notifyDataSetChanged();
    }

    public class WishListRecyclerHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_book_thumbnail) public ImageView bookThumbnail;
        @BindView(R.id.gradient) public ImageView gradient;
        @BindView(R.id.tv_book_title) TextView bookTitle;
        @BindView(R.id.card_view_added_book) CardView cardView;
        @BindView(R.id.btn_confirm_receipt) Button confirmReceipt;
        @BindView(R.id.tv_in_query) TextView inQueryTextView;

        WishListRecyclerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
