package com.example.frychicksorderapp.ui.SubMenu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.frychicksorderapp.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.frychicksorderapp.ui.Models.FoodItemsModel;
import com.example.frychicksorderapp.ui.RecyclerAdapters.FoodItemRecylerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class sub_menu_class extends AppCompatActivity {

    Button addBtn, minusBtn;
    TextView mItemQnty, mItemNameTxt, mItemPrice, mItemDesc, mItemDiscount;
    int itemQnty, itemNo;
    RecyclerView mItemsRecycler;
    View view;
    List<FoodItemsModel> foodItems;
    FloatingActionButton addToCart;
    FirebaseDatabase listDb;
    DatabaseReference listRef;
    ProgressDialog loadProgress;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_menu_class);


        key = getIntent().getStringExtra("MenuId");
        initViews();
        initDb();
        quantitySelection();
        loadItemsRecyler(key);
    }

    private void getMenuID() {

    }

    private void initDb() {
        listDb = FirebaseDatabase.getInstance();
        listRef = listDb.getReference("Foods");
    }

    public void initViews() {
        addBtn = findViewById(R.id.add_btn);
        minusBtn = findViewById(R.id.subtract_btn);
        mItemQnty = findViewById(R.id.item_quantity_txtbox);
        mItemsRecycler = findViewById(R.id.showItemsRcycl);
        addToCart = findViewById(R.id.add_to_cart);
        mItemsRecycler = findViewById(R.id.showItemsRcycl);
        mItemNameTxt = findViewById(R.id.itemName);
        loadProgress = new ProgressDialog(sub_menu_class.this);
        loadProgress.setMessage("Loading Data...");
        loadProgress.show();
        foodItems = new ArrayList<>();
    }


    public void loadItemsRecyler(String key) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mItemsRecycler.setLayoutManager(layoutManager);
        listRef.orderByChild("MenuId").equalTo(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    try {
                        FoodItemsModel item = snapshot.getValue(FoodItemsModel.class);
                        foodItems.add(item);
                    } catch (NullPointerException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
                mItemsRecycler.setAdapter(new FoodItemRecylerAdapter(foodItems, getApplicationContext()));
                loadProgress.dismiss();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getDetails(), Toast.LENGTH_SHORT).show();
                loadProgress.dismiss();

            }
        });
    }

    public void quantitySelection() {
        itemQnty = 0;
        String str = Integer.toString(itemQnty);
        mItemQnty.setText(str);
        final String qnty = mItemQnty.getText().toString();
        itemNo = Integer.parseInt(qnty);
        if (itemNo >= 0) {

            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemNo++;
                    itemQnty = itemNo;
                    String str = Integer.toString(itemNo);
                    mItemQnty.setText(str);
                }
            });
            minusBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemQnty == 0) {
                        Toast.makeText(getApplicationContext(), "You don't in minus", Toast.LENGTH_SHORT).show();

                    } else {
                        itemNo--;
                        itemQnty = itemNo;
                        String str = Integer.toString(itemNo);
                        mItemQnty.setText(str);

                    }
                }
            });

        }
    }


}
