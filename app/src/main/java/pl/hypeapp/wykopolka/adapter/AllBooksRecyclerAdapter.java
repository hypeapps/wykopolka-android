package pl.hypeapp.wykopolka.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
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

public class AllBooksRecyclerAdapter extends RecyclerView.Adapter<AllBooksRecyclerAdapter.AllBooksRecyclerHolder> {
    //TODO: LOADING ITEM
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private static final String WYKOPOLKA_IMG_HOST = App.WYKOPOLKA_IMG_HOST;
    private final PublishSubject<AllBooksRecyclerAdapter.AllBooksRecyclerHolder> onClickSubject = PublishSubject.create();
    private LayoutInflater mLayoutInflater;
    private List<Book> mDataSet = Collections.emptyList();
    private Context mContext;

    public AllBooksRecyclerAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        this.mContext = context;
    }

    @Override
    public AllBooksRecyclerAdapter.AllBooksRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.vertical_book_item, parent, false);
        return new AllBooksRecyclerAdapter.AllBooksRecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(AllBooksRecyclerAdapter.AllBooksRecyclerHolder allBooksRecyclerHolder, int position) {
        Book current = mDataSet.get(position);
        final AllBooksRecyclerAdapter.AllBooksRecyclerHolder holder = allBooksRecyclerHolder;
        holder.bookTitle.setText(current.getTitle());
        holder.bookAuthor.setText(current.getAuthor());
        Glide.with(mContext)
                .load(WYKOPOLKA_IMG_HOST + current.getCover())
                .placeholder(R.drawable.default_book_cover)
                .error(R.drawable.default_book_cover)
                .override(300, 400)
                .thumbnail(0.9f)
                .into(holder.bookCover);
        holder.materialRippleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSubject.onNext(holder);
            }
        });
    }

    public Observable<AllBooksRecyclerAdapter.AllBooksRecyclerHolder> getOnBookClicks() {
        return onClickSubject.asObservable();
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void setData(List<Book> books) {
        mDataSet = books;
        notifyDataSetChanged();
    }

    public void setMoreData(List<Book> books) {
        mDataSet.addAll(books);
//        notifyDataSetChanged();
    }

    public class AllBooksRecyclerHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_book_title) TextView bookTitle;
        @BindView(R.id.tv_book_author) TextView bookAuthor;
        @BindView(R.id.iv_book_cover) public ImageView bookCover;
        @BindView(R.id.ripple_layout) MaterialRippleLayout materialRippleLayout;

        AllBooksRecyclerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    //TODO: LOADING ITEM
    class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.spin_loading);
        }
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }
}