package pl.hypeapp.wykopolka.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.hypeapp.wykopolka.R;
import pl.hypeapp.wykopolka.model.BookView;

public class BooksRecyclerAdapter extends RecyclerView.Adapter<BooksRecyclerAdapter.BooksRecyclerHolder> {
    private LayoutInflater mLayoutInflater;
    private List<BookView> mDataSet = Collections.emptyList();

    public BooksRecyclerAdapter(Context context, List<BookView> dataSet) {
        mLayoutInflater = LayoutInflater.from(context);
        mDataSet = dataSet;
    }

    @Override
    public BooksRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.book_row, parent, false);
        BooksRecyclerHolder viewHolder =
                new BooksRecyclerHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BooksRecyclerHolder holder, int position) {
        BookView current = mDataSet.get(position);
        holder.bookTitle.setText(current.title);
        holder.bookAuthor.setText(current.author);
        holder.bookThumbnail.setImageResource(current.thumbnail);
    }

    @Override
    public int getItemCount() { return mDataSet.size(); }

    public class BooksRecyclerHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_book_title) TextView bookTitle;
        @BindView(R.id.tv_book_author) TextView bookAuthor;
        @BindView(R.id.iv_book_thumbnail) ImageView bookThumbnail;

        public BooksRecyclerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
