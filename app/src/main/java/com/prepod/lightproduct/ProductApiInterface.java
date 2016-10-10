package com.prepod.lightproduct;

import com.prepod.lightproduct.containers.Product;
import com.prepod.lightproduct.containers.ProductReview;
import com.prepod.lightproduct.containers.Review;
import com.prepod.lightproduct.containers.Token;
import com.prepod.lightproduct.containers.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ProductApiInterface {


    @GET("api/products")
    Call<List<Product>> getProducts();

    @GET("api/reviews/{product_id}")
    Call<List<ProductReview>> getProductReviewById(@Path("product_id") String productId);

    @POST("api/reviews/{product_id}")
    Call<Review> sendProductReview(@Header("Authorization") String authorization, @Path("product_id") String productId, @Body Review review);

    @POST("api/register/")
    Call<Token> registerUser(@Body User user);

    @POST("api/login/")
    Call<Token> loginUser(@Body User user);

}
