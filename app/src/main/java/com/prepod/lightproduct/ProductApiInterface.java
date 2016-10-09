package com.prepod.lightproduct;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ProductApiInterface {


    @GET("api/products")
    Call<List<Product>> getProducts();

    @GET("api/reviews/{product_id}")
    Call<List<ProductReview>> getProductReviewById(@Path("product_id") String productId);

    @POST("api/register/")
    Call<Token> registerUser(@Body User user);

    @POST("api/login/")
    Call<Token> loginUser(@Body User user);

}
