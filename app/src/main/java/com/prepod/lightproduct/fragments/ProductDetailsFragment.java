package com.prepod.lightproduct.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.prepod.lightproduct.Consts;
import com.prepod.lightproduct.LightProduct;
import com.prepod.lightproduct.ProductApiInterface;
import com.prepod.lightproduct.containers.ProductReview;
import com.prepod.lightproduct.adapters.ProductReviewAdapter;
import com.prepod.lightproduct.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsFragment extends Fragment{

    private ImageView productImage;
    private TextView productText;
    private TextView productTitle;
    private ProgressBar progressBar;
    private RecyclerView rView;
    private LinearLayoutManager lLayout;
    private ProductReviewAdapter rcAdapter;
    private List<ProductReview> product = new ArrayList<>();

    public ProductDetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_details, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        productTitle = (TextView) getView().findViewById(R.id.product_title);
        productText = (TextView) getView().findViewById(R.id.product_text);
        productImage = (ImageView) getView().findViewById(R.id.product_image);
        progressBar = (ProgressBar) getView().findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.VISIBLE);

        rView = (RecyclerView) getView().findViewById(R.id.rec_view);
        rView.setHasFixedSize(true);
        lLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rView.setLayoutManager(lLayout);
        rcAdapter = new ProductReviewAdapter(getActivity(), product);
        rView.setAdapter(rcAdapter);


        int productId = getActivity().getIntent().getExtras().getInt("product_id", 0);
        String title = getActivity().getIntent().getExtras().getString("product_title");
        String text = getActivity().getIntent().getExtras().getString("product_text");
        String  img = getActivity().getIntent().getExtras().getString("product_img");
        String imageUrl = Consts.IMG_URL + img;

        productTitle.setText(title);
        productText.setText(text);
        Picasso.with(getActivity()).load(imageUrl).into(productImage);

        getReviews(productId);
    }

    private void getReviews(int productId){

        ProductApiInterface service = LightProduct.getInstance().getRetrofitClient().create(ProductApiInterface.class);

        Call<List<ProductReview>> call = service.getProductReviewById("" + productId);

        call.enqueue(new Callback<List<ProductReview>>() {
            @Override
            public void onResponse(Call<List<ProductReview>> call, Response<List<ProductReview>> response) {
                Log.v("My", "" + response);
                if (null != response && null != response.body()) {
                    for (int i = 0; i < response.body().size(); i++) {
                        product.add(response.body().get(i));
                    }
                    rcAdapter.notifyDataSetChanged();
                }
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<List<ProductReview>> call, Throwable t) {
                Log.v("My", "" + t);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v("My", "" );
    }
}
