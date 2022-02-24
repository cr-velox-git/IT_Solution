package com.silverphoenix.itsolution;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.silverphoenix.itsolution.Product.ProductAdapter;
import com.silverphoenix.itsolution.Product.ProductData;
import com.silverphoenix.itsolution.Product.ProductRoomDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ProductActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<ProductData> productDataList;
    private RequestQueue requestQueue;
    ProductRoomDB productDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        recyclerView = findViewById(R.id.p_recycyle);
        requestQueue = Server.getmInstance(this).getRequestQueue();

        //start linear layout manager
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);

        //Initalize database
        productDatabase = ProductRoomDB.getInstance(this);
        productDataList = productDatabase.productDao().getAll();

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo !=null && networkInfo.isConnected()){
            fetchData();
        }else{
            Toast.makeText(this, "required network connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchData(){
        productDatabase.productDao().reset(productDataList);
        productDataList.clear();

        String url = "https://snedamart.com/app/api/item.php?catid=11&subid=12";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0;i<response.length();i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String id = jsonObject.getString("Id");
                        String name = jsonObject.getString("Name");
                        String image = jsonObject.getString("Image");

                        ProductData data = new ProductData();
                        data.setID(Integer.parseInt(id));
                        data.setImage(image);
                        data.setName(name);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                productDataList.addAll(productDatabase.productDao().getAll());

                productAdapter = new ProductAdapter(productDataList, ProductActivity.this);
                recyclerView.setAdapter(productAdapter);
                productAdapter.notifyDataSetChanged();
                Toast.makeText(ProductActivity.this, "data fetched", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProductActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}