package com.silverphoenix.itsolution.Product;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.silverphoenix.itsolution.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.Viewholder> {

    private List<ProductData> productDataList;
    private Activity context;
    private ProductRoomDB productRoomDB;

    public ProductAdapter(List<ProductData> productDataList, Activity context) {
        this.productDataList = productDataList;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.Viewholder holder, int position) {
        //Initialize data
        ProductData productData = productDataList.get(position);
        productRoomDB = ProductRoomDB.getInstance(context);

        holder.setData(productData);
    }

    @Override
    public int getItemCount() {
        return productDataList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private ImageView p_image;
        private TextView p_name, p_mrp, p_discount, p_sell;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            p_image = itemView.findViewById(R.id.p_image);
            p_name = itemView.findViewById(R.id.p_name);
            p_mrp = itemView.findViewById(R.id.p_mrp);
            p_discount = itemView.findViewById(R.id.p_dis);
            p_sell = itemView.findViewById(R.id.p_sale_price);
        }

        private void setData(ProductData productData) {
            //p_image = i
            p_name.setText(productData.getName());
            p_mrp.setText(productData.getMrp());
            p_discount.setText(productData.getDiscount());
            p_sell.setText(productData.getSellprice());
        }
    }
}
