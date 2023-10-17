package com.example.appprueba.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appprueba.R;
import com.example.appprueba.models.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<Product> products;
    private LayoutInflater layoutInflater;
    private Context context;

    public ProductAdapter(List<Product> itemList, Context context){
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.products = itemList;
    }

    @Override
    public int getItemCount(){
        return products.size();
    }

    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = layoutInflater.inflate(R.layout.list_element, null);
        return new ProductAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProductAdapter.ViewHolder holder, final  int position){
        holder.bindData(products.get(position));
    }

    public void setItems(List<Product> listProducts){
        products = listProducts;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView id, type, name, ppu;

        ViewHolder(View itemView){
            super(itemView);
            id = itemView.findViewById(R.id.txtId);
            type = itemView.findViewById(R.id.txtType);
            name = itemView.findViewById(R.id.txtName);
            ppu = itemView.findViewById(R.id.txtUPP);
        }

        void bindData(final Product product){
            id.setText(product.getId());
            type.setText(product.getType());
            name.setText(product.getName());
            ppu.setText(Float.toString(product.getPpu()));
        }
    }
}
