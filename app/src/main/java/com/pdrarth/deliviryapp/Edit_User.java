package com.pdrarth.deliviryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Edit_User extends AppCompatActivity {
    private TextView nome_usuario, email_usuario;
    private Button edit_user;
    private String usuario_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        Components();
        edit_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditProfile();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        usuario_id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        DocumentReference documentReference = db.collection("Usuarios").document(usuario_id);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null) {
                    nome_usuario.setText(value.getString("nome"));
                    email_usuario.setText(email);


                }
            }
        });
    }
    public void EditProfile(){
        Intent intent = new Intent(Edit_User.this,Atualizar_Dados.class);
        startActivity(intent);

    }
    public void Components() {
        nome_usuario = findViewById(R.id.name_user);
        email_usuario = findViewById(R.id.name_email);
        edit_user = findViewById(R.id.edit_info);
    }
}