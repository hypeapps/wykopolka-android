package pl.hypeapp.wykopolka.adapter;

import android.content.Context;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public class SearchBookRecyclerAdapter extends RecyclerView.Adapter<SearchBookRecyclerAdapter.SearchBookRecyclerHolder> {
    private static final String WYKOPOLKA_IMG_HOST = App.WYKOPOLKA_IMG_HOST;
    private final PublishSubject<Pair<SearchBookRecyclerHolder, Book>> onClickSubject = PublishSubject.create();
    public LayoutInflater mLayoutInflater;
    private List<Book> mDataSet = Collections.emptyList();
    private Context mContext;

    public SearchBookRecyclerAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        this.mContext = context;
    }

    @Override
    public SearchBookRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.searched_book_item, parent, false);
        return new SearchBookRecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchBookRecyclerHolder searchBookRecyclerHolder, int position) {
        final Book current = mDataSet.get(position);
        final SearchBookRecyclerAdapter.SearchBookRecyclerHolder holder = searchBookRecyclerHolder;
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
                Pair<SearchBookRecyclerHolder, Book> clickedBook = new Pair<>(holder, current);
                onClickSubject.onNext(clickedBook);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public Observable<Pair<SearchBookRecyclerHolder, Book>> getOnBookClicks() {
        return onClickSubject.asObservable();
    }

    public void setData(List<Book> books) {
        mDataSet = books;
        notifyDataSetChanged();
    }

    public class SearchBookRecyclerHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_book_title) TextView bookTitle;
        @BindView(R.id.tv_book_author) TextView bookAuthor;
        @BindView(R.id.iv_book_cover) public ImageView bookCover;
        @BindView(R.id.ripple_layout) MaterialRippleLayout materialRippleLayout;

        public SearchBookRecyclerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
