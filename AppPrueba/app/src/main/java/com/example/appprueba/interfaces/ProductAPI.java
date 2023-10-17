package com.example.appprueba.interfaces;

import com.example.appprueba.models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductAPI {
    @GET("v1/eeced007-6b29-4c9d-ab63-c115a990d927")
    public Call<List<Product>> getProducts();
}
