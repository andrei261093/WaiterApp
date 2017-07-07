package com.example.andreiiorga.waiterapp.listAdapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andreiiorga.waiterapp.AsyncTasks.AsynchronousHttpClient;
import com.example.andreiiorga.waiterapp.Model.Product;
import com.example.andreiiorga.waiterapp.Model.Task;
import com.example.andreiiorga.waiterapp.R;
import com.example.andreiiorga.waiterapp.staticUtils.StaticStrings;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by andreiiorga on 27/06/2017.
 */

public class ProductListAdapter extends ArrayAdapter<Product> {

    List<Product> products = new ArrayList<>();

    public ProductListAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Nullable
    @Override
    public Product getItem(int position) {
        return products.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;

        row = convertView;
        final ProductHolder productHolder;

        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_products, parent, false);
            productHolder = new ProductHolder();

            productHolder.tx_product_name = (TextView) row.findViewById(R.id.tx_product_name);
            productHolder.image = (ImageView) row.findViewById(R.id.image_product);

            row.setTag(productHolder);

        } else {
            productHolder = (ProductHolder) row.getTag();
        }

        final Product product = (Product) this.getItem(position);
        productHolder.tx_product_name.setText(product.getName());
        Picasso.with(getContext())
                .load(product.getImageUrl())
                .into(productHolder.image);


        return row;
    }
    public void add(Product product) {
        super.add(product);
        products.add(product);
    }

    public void addProducts(JSONObject task) throws JSONException {
        JSONArray productsJson = task.getJSONArray("products");
        for(int i=0;i<productsJson.length();i++){
            JSONObject productJson = (JSONObject) productsJson.get(i);
            Product product = new Product(productJson.getString("name"), productJson.getString("imageUrl"));
            add(product);
        }

    }

    static class ProductHolder {
        TextView tx_product_name;
        ImageView image;
    }

    @Override
    public void clear() {
        products.clear();
        super.clear();
        notifyDataSetChanged();
    }


}
