package com.example.frychicksorderapp.ui.manu;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.frychicksorderapp.R;
import com.example.frychicksorderapp.ui.Models.Category;
import com.example.frychicksorderapp.ui.RecyclerAdapters.MenuAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ManuFragment extends Fragment {


    private RecyclerView recycler;
    private MenuAdapter adapter;
    private FirebaseDatabase database;
    private DatabaseReference mRef;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_manu, container, false);

        initFirebase();


        recycler = root.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<Category> options = new FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(mRef.orderByChild("Name"), Category.class)
                .build();
        adapter = new MenuAdapter(options);

        recycler.setAdapter(adapter);
        return root;
    }


    private void initFirebase() {

        database = FirebaseDatabase.getInstance();
        mRef = database.getReference("Category");
        mRef.keepSynced(true);

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }


}