package com.silverphoenix.itsolution.Category;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.silverphoenix.itsolution.R;
import com.silverphoenix.itsolution.Utils;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.Viewholder> {

    private List<CategoryData> categoryDataList;
    private Activity context;
    private CategoryRoomDB categoryRoomDB;

    public CategoryAdapter(List<CategoryData> categoryDataList, Activity context) {
        this.categoryDataList = categoryDataList;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.Viewholder holder, int position) {
        //Initialize data
        CategoryData categoryData = categoryDataList.get(position);
        categoryRoomDB = CategoryRoomDB.getInstance(context);
        holder.setData(categoryData);
    }

    @Override
    public int getItemCount() {
        return categoryDataList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private ImageView c_image;
        private TextView c_name;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            c_image=itemView.findViewById(R.id.c_image);
            c_name=itemView.findViewById(R.id.c_name);
        }

        public void setData(CategoryData categoryData) {
            String i = categoryData.getImage();
            Glide.with(context).load(i).into(c_image);
            c_name.setText(categoryData.getName());
        }
    }
}
