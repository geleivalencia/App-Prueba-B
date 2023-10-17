package com.example.appprueba;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.appprueba.adapter.ProductAdapter;
import com.example.appprueba.interfaces.ProductAPI;
import com.example.appprueba.models.Batter;
import com.example.appprueba.models.Batters;
import com.example.appprueba.models.Product;
import com.example.appprueba.models.Topping;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Home extends AppCompatActivity {
    private TextView jsonTextView;
    Button btnLogout, btnDetails;
    int contador = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnLogout = findViewById(R.id.btnLogout);
        btnDetails = findViewById(R.id.btnDetails);

        //jsonTextView = findViewById(R.id.jsonText);
        //getProducts();
        getUsername();
        replaceFragment(new ProductList());

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(contador == 0){
                    replaceFragment(new ProductDetail());
                    btnDetails.setText(getResources().getString(R.string.seeToppings));
                    contador++;
                } else if (contador == 1){
                    replaceFragment(new toppings());
                    btnDetails.setText(getResources().getString(R.string.seeList));
                    contador++;
                } else {
                    replaceFragment(new ProductList());
                    btnDetails.setText(getResources().getString(R.string.seeBatters));
                    contador=0;
                }

            }
        });
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
                    jsonTextView.setText("CODIGO: " + response.code());
                    return;
                }
                List<Product> productList = response.body();
                //replaceFragment(new ProductList());
                init(productList);

                /*for(Product product: productList){
                    String content = "";
                    content += "ID: " + product.getId() + "\n";
                    content += "Type: " + product.getType() + "\n";
                    content += "Name: " + product.getName() + "\n";
                    content += "PPU: " + product.getPpu() + "\n";

                    Batters batters = product.getBatters();
                    if(batters != null){
                        for (Batter batter: batters.getBatter()){
                            if(batter != null) {
                                content += "Batters: \n";
                                content += "Id: " + batter.getId() + "\n";
                                content += "Type: " + batter.getType() + "\n";
                            }
                        }
                    }

                    List<Topping> toppings = product.getTopping();
                    if(toppings != null) {
                        content += "Toppings: \n";
                        for (Topping topping : toppings) {
                                content += "Id: " + topping.getId() + "\n";
                                content += "Type: " + topping.getType() + "\n";
                        }
                    }

                   // jsonTextView.append(content);
                }*/
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
              //  jsonTextView.setText(t.getMessage());
            }
        });
    }

    public void getUsername(){
        Bundle extras = getIntent().getExtras();
        String username = extras.getString("username");

        welcomeMessage(Home.this, username);
    }

    public void welcomeMessage(Context context, String username){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("¡Bienvenido " + username + "!");
        builder.show();
    }

    public void init(List<Product> list){
        ProductAdapter productAdapter = new ProductAdapter(list, this);
        RecyclerView recyclerView = findViewById(R.id.rvProducts);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(productAdapter);
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLC, fragment);
        fragmentTransaction.commit();
    }
}