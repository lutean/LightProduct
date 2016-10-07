package com.prepod.lightproduct.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prepod.lightproduct.LightProduct;
import com.prepod.lightproduct.Product;
import com.prepod.lightproduct.ProductApiInterface;
import com.prepod.lightproduct.R;
import com.prepod.lightproduct.RecyclerItemClickListener;
import com.prepod.lightproduct.ProductsListAdapter;
import com.prepod.lightproduct.activities.ProductDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsListFragment extends Fragment{

    private LinearLayoutManager lLayout;
    private RecyclerView rView;
    private ProductsListAdapter rcAdapter;

    private List<Product> products = new ArrayList<>();

    public ProductsListFragment(){
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_products_list, container, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rView = (RecyclerView) getView().findViewById(R.id.rec_view);
        rView.setHasFixedSize(true);
        lLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rView.setLayoutManager(lLayout);
        rcAdapter = new ProductsListAdapter(getActivity(), products);
        rView.setAdapter(rcAdapter);

        rView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), rView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Log.v("My", "");
                        Intent product = new Intent(getActivity(), ProductDetailsActivity.class);
                        product.putExtra("product_id", products.get(position).getId());
                        product.putExtra("product_title", products.get(position).getTitle());
                        product.putExtra("product_text", products.get(position).getText());
                        product.putExtra("product_img", products.get(position).getImg());
                        startActivity(product);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        Log.v("My", "" + view);
                    }

                })
        );
        ProductApiInterface service = LightProduct.getInstance().getRetrofitClient().create(ProductApiInterface.class);

        Call<List<Product>> call = service.getProducts();

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                Log.v("My", "" + response);
                // if (null != response && null != response.body()) {
                for (int i=0; i<response.body().size(); i++){
                    products.add(response.body().get(i));
                }
                //products = response.body();
                rcAdapter.notifyDataSetChanged();
                // }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.v("My", "" + t);
            }
        });

    }

}
