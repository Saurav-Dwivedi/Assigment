package com.example.myapplication.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Model;
import com.example.myapplication.view.activity.UpdateTaskActivity;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.TasksViewHolder> {

    private Context mCtx;
    private List<Model> taskList;

    public ProductAdapter(Context mCtx, List<Model> taskList) {
        this.mCtx = mCtx;
        this.taskList = taskList;
    }

    @Override
    public TasksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_single_item, parent, false);
        return new TasksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TasksViewHolder holder, int position) {
        Model m = taskList.get(position);
        holder.textViewName.setText(m.getName());
        holder.textViewPrice.setText(m.getRegular_price() + "");
        holder.textViewDescription.setText(m.getDescription() + "");
        byte[] byteArray = m.getImage();
        try {
            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            Log.e("bpm", bmp + "");
            holder.productImage.setImageBitmap(bmp);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.cardView.setOnClickListener(view -> {
            Model task = taskList.get(position);

            Intent intent = new Intent(mCtx, UpdateTaskActivity.class);
            intent.putExtra("model", task);

            mCtx.startActivity(intent);
        });


    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    class TasksViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewPrice, textViewDescription;
        ImageView productImage;
        CardView cardView;


        public TasksViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.tv_product_name);
            textViewPrice = itemView.findViewById(R.id.tv_price);
            textViewDescription = itemView.findViewById(R.id.tv_description);
            productImage = itemView.findViewById(R.id.iv_product_image);
            cardView = itemView.findViewById(R.id.cardview);


        }


    }
}