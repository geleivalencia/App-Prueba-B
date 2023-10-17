package com.example.appprueba;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appprueba.adapter.ProductAdapter;
import com.example.appprueba.interfaces.ProductAPI;
import com.example.appprueba.models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductDetail extends Fragment {
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_product_detail, container, false);
        getProducts();
        return view;
    }

    public void init(List<Product> list){
        ProductAdapter productAdapter = new ProductAdapter(list, view.getContext());
        RecyclerView recyclerView = view.findViewById(R.id.rvProducts);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(productAdapter);
    }


    private void getProducts(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://mocki.io/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        ProductAPI productAPI = retrofit.create(ProductAPI.class);

        Call<List<Product>> call = productAPI.getProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(!response.isSuccessful()){
                    return;
                }
                List<Product> productList = response.body();
                init(productList);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                //  jsonTextView.setText(t.getMessage());
            }
        });
    }
}