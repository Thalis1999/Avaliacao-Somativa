package com.appgjob.formativa_aula_atividade_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Banco extends AppCompatActivity {

    private FirebaseFirestore conexao;
    private ListView listaBd;
    private ListView lista_exibir;
    private int indice = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bd);

        String extra = getIntent().getStringExtra("testeExtra");
        Toast.makeText(Banco.this, extra, Toast.LENGTH_SHORT).show();

        lista_exibir = findViewById(R.id.listaBd);
        buscarBd();
    }

    public void buscarBd(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("dados")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            List<Data> lista = new ArrayList<Data>();
                            for(QueryDocumentSnapshot doc : task.getResult()){

                                if( ! doc.get("status").equals("0")){
                                    Data  p = new Data(
                                            doc.getId().toString(),
                                            doc.get("autenticacao").toString(),
                                            doc.get("status").toString()
                                    );
                                    lista.add(p);
                                }
                            }
                            Toast.makeText(Banco.this, "Conexão OK!", Toast.LENGTH_SHORT).show();
                            ArrayAdapter<Data> adapter = new ArrayAdapter<>(
                                    Banco.this,
                                    android.R.layout.simple_list_item_1, lista);
                            lista_exibir.setAdapter(adapter);

                        }else{
                            Toast.makeText(Banco.this, "Erro", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}