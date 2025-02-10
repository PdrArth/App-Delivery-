package com.pdrarth.deliviryapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Atualizar_Dados extends AppCompatActivity {

    private EditText atualizar_nome;
    private CircleImageView attimagem;
    private URI url_image ;
    private Button Escolherfoto, AttDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizar_dados);
        Componentes();

        Escolherfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //SelecionarFoto();
            }
        });
        AttDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = atualizar_nome.getText().toString();
                if(nome.isEmpty()){
                    Snackbar snackbar = Snackbar.make(view,"Preencha todos os campos", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
                else{SalvarNome(view);
                }
            }
        });



    }
    public void SalvarNome(View view) {

        String nomeusuario = atualizar_nome.getText().toString();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> usuarios = new HashMap<>();
        usuarios.put("nome", nomeusuario);

        String usuasioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Usuarios").document(usuasioID);
        documentReference.update("nome",  nomeusuario).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Snackbar snackbar = Snackbar.make(view, "Nome Atualizado com sucesso", Snackbar.LENGTH_INDEFINITE)
                        .setAction("ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                finish();
                            }
                        });
                snackbar.show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    //    ActivityResultLauncher<Intent> intentActivityResultLauncher = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    if (result.getResultCode() == Activity.RESULT_OK) {
//                        Intent data = result.getData();
//                        url_image = data.getData();
//                        try {
//                            profile_image.setImageURI(url_image);
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                }
//            }
//    );

//    public void SelecionarFoto() {
//
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType("image/*");
//        intentActivityResultLauncher.launch(intent);
//
//    }

    public void Componentes(){
        atualizar_nome = findViewById(R.id.editarnome);
        //attimagem = findViewById(R.id.attfoto);
        Escolherfoto = findViewById(R.id.attfoto);
        AttDados = findViewById(R.id.attperfil);
    }
}