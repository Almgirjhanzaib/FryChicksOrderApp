package com.example.frychicksorderapp.ui.RecyclerAdapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.frychicksorderapp.R;
import com.example.frychicksorderapp.ui.Models.FoodItemsModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FoodItemRecylerAdapter extends RecyclerView.Adapter<FoodItemRecylerAdapter.FoodItemsHolder> {
    List<FoodItemsModel> itemsList;
    Context context;

    public FoodItemRecylerAdapter(List<FoodItemsModel> itemsList, Context context) {
        this.itemsList = itemsList;
        this.context = context;
    }

    @NonNull
    @Override
    public FoodItemsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.items_list_submenu,parent,false);
        return new FoodItemsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FoodItemsHolder holder, int position) {
        final FoodItemsModel itemsModel = itemsList.get(position);
        holder.itemName.setText(itemsModel.getName());
       Picasso.get().load(itemsModel.getImage()).into(holder.itemImg);

       holder.itemView.setOnClickListener((View v) -> {
           Toast.makeText(context, itemsModel.getName(), Toast.LENGTH_SHORT).show();

       });


    }

    @Override
    public int getItemCount() {
        try{
            return itemsList.size();
        }
        catch (NullPointerException e)
        {
            Log.d("Error", e.getMessage() + "Error");
            return 0;
        }


    }

    public  class  FoodItemsHolder extends RecyclerView.ViewHolder{
        TextView itemName,itemNamelog,itemDesc;
        ImageView itemImg;
        public FoodItemsHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            itemImg = itemView.findViewById(R.id.itemImage);
            itemNamelog = itemView.findViewById(R.id.itemNamelog);
            itemDesc = itemView.findViewById(R.id.itemDesc);
        }
    }
}
