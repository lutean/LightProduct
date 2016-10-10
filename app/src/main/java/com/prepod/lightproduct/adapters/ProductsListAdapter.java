package com.prepod.lightproduct.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.prepod.lightproduct.Consts;
import com.prepod.lightproduct.R;
import com.prepod.lightproduct.containers.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductsListAdapter extends RecyclerView.Adapter<ProductsListAdapter.RecyclerViewHolders> {

    private List<Product> itemList;
    private Context context;

    public ProductsListAdapter(Context context, List<Product> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {

        holder.title.setText(itemList.get(position).getTitle());
      //  holder.text.setText(itemList.get(position).getText());

        String imageUrl = Consts.IMG_URL + itemList.get(position).getImg();
        Picasso.with(context).load(imageUrl).into(holder.productImage);
        //holder.productImage.setImageURI(Uri.parse(Consts.IMG_URL + itemList.get(position).getImg()));
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public class RecyclerViewHolders extends RecyclerView.ViewHolder{

        public TextView title;
        public TextView text;
        public ImageView productImage;

        public RecyclerViewHolders(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.name);
           // text = (TextView)itemView.findViewById(R.id.txtStatusMsg);
            productImage = (ImageView)itemView.findViewById(R.id.productPic);
        }
    }
}