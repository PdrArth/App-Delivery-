package com.pdrarth.deliviryapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Tela_Login extends AppCompatActivity {

    private EditText nome,senha;
    private Button logar;
    private ProgressBar progressBar;
    private TextView mensagem_error;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login);
        getSupportActionBar().hide();
        Components();
        logar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String  nomeusuario = nome.getText().toString();
                String  senhausuario = senha.getText().toString();

                if(nomeusuario.isEmpty() || senhausuario.isEmpty()){
                    mensagem_error.setText("Preencha todos os campos");
                }else {
                    mensagem_error.setText("");
                    Logar();
                }
            }
        });
    }
    public void TelaCadastro(View view){
        Intent intent =  new Intent(Tela_Login.this, Tela_Cadastro.class);
        startActivity(intent);
    }
    public void Logar(){
        String  nomeusuario = nome.getText().toString();
        String  senhausuario = senha.getText().toString();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(nomeusuario, senhausuario).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful()){
                    ProgressBar();
            }
            else {
                String error;
                try {
                    throw task.getException();
                }
                catch (Exception e){
                    error = "Error ao logar usuario";
                }
                mensagem_error.setText(error);
            }
            }
        });

    }
    public void Tela_Produts(){
        Intent intent = new Intent(Tela_Login.this, List_Produts.class);
        startActivity(intent);
        finish();
    }
    public void ProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Tela_Produts();
            }
        },3000);
    }
    public void Components(){

        nome = findViewById(R.id.email);
        senha = findViewById(R.id.senha);
        logar = findViewById(R.id.button);
        progressBar = findViewById(R.id.progressBar);
        mensagem_error = findViewById(R.id.textView2);

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser != null){
            Tela_Produts();
        }
    }
}