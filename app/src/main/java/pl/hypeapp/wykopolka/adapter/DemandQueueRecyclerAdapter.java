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

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.hypeapp.wykopolka.App;
import pl.hypeapp.wykopolka.R;
import pl.hypeapp.wykopolka.model.Book;
import pl.hypeapp.wykopolka.model.DemandQueue;
import pl.hypeapp.wykopolka.model.PendingUser;
import pl.hypeapp.wykopolka.util.transformation.CropCircleTransformation;
import rx.Observable;
import rx.subjects.PublishSubject;

public class DemandQueueRecyclerAdapter extends RecyclerView.Adapter<DemandQueueRecyclerAdapter.DemandQueueRecyclerHolder> {
    private static final String WYKOPOLKA_IMG_HOST = App.WYKOPOLKA_IMG_HOST;
    private final PublishSubject<DemandQueueRecyclerAdapter.DemandQueueRecyclerHolder> onBookClickSubject = PublishSubject.create();
    private final PublishSubject<Integer> onTransferClickSubject = PublishSubject.create();
    private LayoutInflater mLayoutInflater;
    private DemandQueue mDataSet;
    private Context mContext;

    public DemandQueueRecyclerAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        this.mContext = context;
        mDataSet = new DemandQueue();
        mDataSet.setBooks(Collections.<Book>emptyList());
    }

    @Override
    public DemandQueueRecyclerAdapter.DemandQueueRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.demand_queue_row, parent, false);
        return new DemandQueueRecyclerAdapter.DemandQueueRecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(DemandQueueRecyclerAdapter.DemandQueueRecyclerHolder booksRecyclerHolder, final int position) {
        Book currentBook = mDataSet.getBooks().get(position);
        PendingUser currentPendingUser = mDataSet.getPendingUsers().get(position);
        final DemandQueueRecyclerAdapter.DemandQueueRecyclerHolder holder = booksRecyclerHolder;
        holder.bookTitle.setText(currentBook.getTitle());
        holder.author.setText(currentBook.getAuthor());
        holder.nickname.setText(currentPendingUser.getLoginFormatted());

        Glide.with(mContext)
                .load(WYKOPOLKA_IMG_HOST + currentBook.getCover())
                .placeholder(R.drawable.default_book_cover)
                .error(R.drawable.default_book_cover)
                .override(300, 400)
                .thumbnail(0.9f)
                .into(holder.bookThumbnail);
        Glide.with(mContext)
                .load(currentPendingUser.getAvatar())
                .placeholder(R.drawable.default_avatar)
                .error(R.drawable.default_avatar)
                .bitmapTransform(new CropCircleTransformation(mContext))
                .into(holder.avatar);
        holder.bookThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBookClickSubject.onNext(holder);
            }
        });
        holder.confirmTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTransferClickSubject.onNext(position);
            }
        });
    }

    public Observable<DemandQueueRecyclerAdapter.DemandQueueRecyclerHolder> getOnBookClick() {
        return onBookClickSubject.asObservable();
    }

    public Observable<Integer> getOnTransferClick() {
        return onTransferClickSubject.asObservable();
    }

    @Override
    public int getItemCount() {
        return mDataSet.getBooks().size();
    }

    public void setData(DemandQueue demandQueue) {
        mDataSet = demandQueue;
        notifyDataSetChanged();
    }

    public class DemandQueueRecyclerHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_book_thumbnail) public ImageView bookThumbnail;
        @BindView(R.id.gradient) public ImageView gradient;
        @BindView(R.id.iv_avatar) ImageView avatar;
        @BindView(R.id.nickname) TextView nickname;
        @BindView(R.id.tv_book_title) TextView bookTitle;
        @BindView(R.id.card_view_added_book) CardView cardView;
        @BindView(R.id.btn_confirm_transfer) Button confirmTransfer;
        @BindView(R.id.tv_book_author) TextView author;

        DemandQueueRecyclerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

