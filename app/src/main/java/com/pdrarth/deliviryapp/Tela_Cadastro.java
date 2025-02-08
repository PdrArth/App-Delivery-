package com.pdrarth.deliviryapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.ktx.Firebase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class Tela_Cadastro extends AppCompatActivity {
    private CircleImageView profile_image;
    private Button escolherfoto;
    private EditText cadastrar_nome, cadastrar_email, cadastrar_senha;
    private TextView texto_Error;
    private Button cadastrar_Usuario;

    private String usuasioID;
    private Uri url_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro);
        InicarComponetes();
        cadastrar_nome.addTextChangedListener(textWatcher);
        cadastrar_email.addTextChangedListener(textWatcher);
        cadastrar_senha.addTextChangedListener(textWatcher);

        cadastrar_Usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CadastrarUsuario(v);
            }
        });

//        escolherfoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SelecionarFoto();
//            }
//        });

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

    public void CadastrarUsuario(View v) {

        String email = cadastrar_email.getText().toString();
        String senha = cadastrar_senha.getText().toString();


        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    SalvarNome();
                    Snackbar snackbar = Snackbar.make(v, "Cadastro Realizado com Sucesso", Snackbar.LENGTH_INDEFINITE)
                            .setAction("ok", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    finish();
                                }
                            });
                    snackbar.show();

                } else {
                    String error = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        error = "Senha com menor de 6 caracteres";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        error = "Email invalido";
                    } catch (FirebaseAuthUserCollisionException e) {
                        error = "Usuario ja cadastro";
                    } catch (FirebaseNetworkException e) {
                        error = "Sem conexao";
                    } catch (Exception e) {
                        error = "Error ao cadastar";
                    }
                    texto_Error.setText(error);

                }
            }
        });
    }

    public void SalvarNome() {

        String nomeusuario = cadastrar_nome.getText().toString();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> usuarios = new HashMap<>();
        usuarios.put("nome", nomeusuario);

        usuasioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Usuarios").document(usuasioID);
        documentReference.set(usuarios).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.i("Sucesso", "onSuccess: ");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("Error", "onFailure: ");
            }
        });

    }

    //public void SalvarDadosUsuario(){
//
//        String nome_Arquivo = UUID.randomUUID().toString();
//        final StorageReference reference = FirebaseStorage.getInstance().getReference("/imagens/" +nome_Arquivo);
//        reference.putFile(url_image)
//                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                            @Override
//                            public void onSuccess(Uri uri) {
//                                Log.i("Sucesso ", url_image.toString());
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                            }
//                        });
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//
//                    }
//                });
//
//
//}
    private void InicarComponetes() {
        profile_image = findViewById(R.id.profile_image);
        escolherfoto = findViewById(R.id.escolherfoto);
        cadastrar_nome = findViewById(R.id.nome);
        cadastrar_email = findViewById(R.id.email);
        cadastrar_senha = findViewById(R.id.senha);
        texto_Error = findViewById(R.id.texterroe);
        cadastrar_Usuario = findViewById(R.id.cadastrar);
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String nome = cadastrar_nome.getText().toString();
            String email = cadastrar_email.getText().toString();
            String senha = cadastrar_senha.getText().toString();

            if (!nome.isEmpty() && !email.isEmpty() && !senha.isEmpty()) {
                cadastrar_Usuario.setEnabled(true);
                cadastrar_Usuario.setBackgroundColor(getColor(R.color.dark_red));
            } else {
                cadastrar_Usuario.setEnabled(false);
                cadastrar_Usuario.setBackgroundColor(getColor(R.color.gray));
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };
}