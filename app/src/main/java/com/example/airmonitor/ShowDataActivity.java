package com.example.airmonitor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowDataActivity extends AppCompatActivity {

    ImageButton back;
    List<HelperClass> list;
    RecyclerView recyclerData;
    ShowDataAdapter adapter;
    ProgressBar progressBar;
    TextView txtNoData;
    DatabaseReference reference;
    Button show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);

        back=findViewById(R.id.back);
        txtNoData=findViewById(R.id.txtNoData);
        txtNoData.setVisibility(View.GONE);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        recyclerData = findViewById(R.id.recyclerData);
        show=findViewById(R.id.show);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerData.setLayoutManager(layoutManager);

        list = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Data");
        Query lastQuery =reference.orderByKey().limitToLast(5);
        lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    HelperClass helperClass = dataSnapshot.getValue(HelperClass.class);
                    list.add(helperClass);
                }
                adapter = new ShowDataAdapter(ShowDataActivity.this,list);
                recyclerData.setAdapter(adapter);
                recyclerData.setLayoutManager(layoutManager);
                progressBar.setVisibility(View.GONE);
                if (list.size()==0)
                    txtNoData.setVisibility(View.VISIBLE);
                else
                    txtNoData.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        
        show.setOnClickListener(v -> startActivity(new Intent(ShowDataActivity.this,AnalysisActivity.class)));

        back.setOnClickListener(v -> onBackPressed());
    }
}