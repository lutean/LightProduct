package com.prepod.lightproduct;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ProductApiInterface {


    @GET("api/products")
    Call<List<Product>> getProducts();

    @GET("api/reviews/{product_id}")
    Call<List<ProductReview>> getProductReviewById(@Path("product_id") String productId);

    @POST("api/register/")
    Call<User> registerUser();

    @POST("api/login/")
    Call<User> loginUser();

}
