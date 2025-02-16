package com.pdrarth.deliviryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.pdrarth.deliviryapp.Adapter.Adapter_Produto;
import com.pdrarth.deliviryapp.Model.Produto;

import java.util.ArrayList;
import java.util.List;

public class List_Produts extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Adapter_Produto adapter;
    private List<Produto> produtos_list;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_produts);

        recyclerView = findViewById(R.id.recyclerview_list_produts);
        produtos_list = new ArrayList<>();
        adapter = new Adapter_Produto(getApplicationContext(), produtos_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("Produtos").orderBy("nome")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                            Produto produto = queryDocumentSnapshot.toObject(Produto.class);
                            produtos_list.add(produto);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.person) {
            Intent intent = new Intent(List_Produts.this, Edit_User.class);
            startActivity(intent);

        } else if (itemId == R.id.pedidos) {

        } else if (itemId == R.id.deslogar) {

            Toast.makeText(List_Produts.this, "Usuario Deslogado", Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(List_Produts.this, Tela_Login.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}