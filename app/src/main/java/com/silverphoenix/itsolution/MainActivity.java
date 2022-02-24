package com.silverphoenix.itsolution;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.silverphoenix.itsolution.Category.CategoryAdapter;
import com.silverphoenix.itsolution.Category.CategoryData;
import com.silverphoenix.itsolution.Category.CategoryRoomDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private Button productBtn;
    private RecyclerView recyclerView;
    private CategoryAdapter categoryAdapter;
    private List<CategoryData> categoryDataList;
    private RequestQueue requestQueue;
    CategoryRoomDB categoryDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productBtn = findViewById(R.id.product_btn);
        recyclerView = findViewById(R.id.cat_recycyle);
        requestQueue = Server.getmInstance(this).getRequestQueue();

        //start linear layout manager
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);

        //Initalize database
        categoryDatabase = CategoryRoomDB.getInstance(this);
        categoryDataList = categoryDatabase.categoryDao().getAll();



        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo !=null && networkInfo.isConnected()){
            fetchData();
        }else{
            Toast.makeText(this, "required network connection", Toast.LENGTH_SHORT).show();
        }

        productBtn.setOnClickListener(v -> {
            Intent productIntent = new Intent(MainActivity.this,ProductActivity.class);
            startActivity(productIntent);
        });
    }

    private void fetchData(){
        categoryDatabase.categoryDao().reset(categoryDataList);
        categoryDataList.clear();

        String url = "https://www.snedamart.com/app/api/category.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0;i<response.length();i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String status = jsonObject.getString("StatusMessage");
                        Toast.makeText(MainActivity.this, status, Toast.LENGTH_SHORT).show();
                        JSONObject category = jsonObject.getJSONObject("Category");

                        for (int j =0; j<category.length();j++){
                            String id = category.getString("Id");
                            String name = category.getString("Name");
                            String image = category.getString("Image");

                            CategoryData data = new CategoryData();
                            data.setID(Integer.parseInt(id));
                            data.setImage(image);
                            data.setName(name);
                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                categoryDataList.addAll(categoryDatabase.categoryDao().getAll());

                categoryAdapter = new CategoryAdapter(categoryDataList, MainActivity.this);
                recyclerView.setAdapter(categoryAdapter);
                categoryAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "data fetched", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}