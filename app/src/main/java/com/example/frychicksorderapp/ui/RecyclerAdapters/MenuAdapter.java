package com.example.frychicksorderapp.ui.RecyclerAdapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.frychicksorderapp.R;
import com.example.frychicksorderapp.ui.ItemClickListner;
import com.example.frychicksorderapp.ui.Models.Category;
import com.example.frychicksorderapp.ui.SubMenu.sub_menu_class;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;


public class MenuAdapter extends FirebaseRecyclerAdapter<Category, MenuAdapter.MenuHolder> {



    public MenuAdapter(@NonNull FirebaseRecyclerOptions<Category> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final MenuHolder holder, int position, @NonNull final Category model) {
        holder.foodName.setText(model.getName());
        Picasso.get().load(model.getImage()).into(holder.foodImage);
        holder.setItemClickListner(new ItemClickListner() {
            @Override
            public void onClick(View view, int position, boolean isLongClicked) {
                Intent intent=new Intent(view.getContext(), sub_menu_class.class);
                intent.putExtra("MenuId",getRef(position).getKey());
                view.getContext().startActivity(intent);

            }
        });
    }

    @NonNull
    @Override
    public MenuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_list,parent,false);
        return new MenuHolder(view);
    }

    public class MenuHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView foodName;
        ImageView foodImage;

        private ItemClickListner itemClickListner;

        public MenuHolder(@NonNull View itemView) {
            super(itemView);
            foodImage=itemView.findViewById(R.id.menu_image);
            foodName=itemView.findViewById(R.id.menu_name);
            itemView.setOnClickListener(this);
        }


        public void setItemClickListner(ItemClickListner itemClickListner) {
            this.itemClickListner = itemClickListner;
        }

        @Override
        public void onClick(View v) {
            itemClickListner.onClick(v,getAdapterPosition(),false);
        }
    }
}
