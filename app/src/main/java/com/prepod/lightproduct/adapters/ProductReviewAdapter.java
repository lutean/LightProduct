package com.prepod.lightproduct.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.prepod.lightproduct.R;
import com.prepod.lightproduct.containers.ProductReview;

import java.util.List;

public class ProductReviewAdapter extends RecyclerView.Adapter<ProductReviewAdapter.RecyclerViewHolders> {

    private List<ProductReview> itemList;
    private Context context;

    public ProductReviewAdapter(Context context, List<ProductReview> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public ProductReviewAdapter.RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_review_item, null);
        ProductReviewAdapter.RecyclerViewHolders rcv = new ProductReviewAdapter.RecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(ProductReviewAdapter.RecyclerViewHolders holder, int position) {

        holder.name.setText(itemList.get(position).getCreatedBy().getUsername());
        holder.text.setText(itemList.get(position).getText());

        float rate = itemList.get(position).getRate();

        holder.ratingBar.setRating(rate);
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public class RecyclerViewHolders extends RecyclerView.ViewHolder{

        public RatingBar ratingBar;
        public TextView name;
        public TextView text;

        public RecyclerViewHolders(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.name);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
            text = (TextView) itemView.findViewById(R.id.text);
        }
    }
}
