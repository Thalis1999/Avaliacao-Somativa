package com.appgjob.formativa_aula_atividade_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {

    private final String ID_CANAL = "canalAppNotificacao";
    private final int COD_NOTIFICACAO = 8795;
    private FirebaseFirestore conexao = FirebaseFirestore.getInstance();
    NotificationManager servico;
    private Button consulta;
    private Button verificacao;
    private EditText chave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chave = findViewById(R.id.chave);
        consulta = findViewById(R.id.btnBuscar);
        createNotificationChannel();
    }

    public void notificacaoCriacao(String mensagem) {

        Intent intent = new Intent(this, Banco.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, ID_CANAL)
                .setSmallIcon(R.drawable.icone_notificacao)
                .setContentTitle("Mensagem alô")
                .setContentText(mensagem)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(178, builder.build());
    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.descricao_canal);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(ID_CANAL, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_SMS}, 100);
            }
        }
    }

    public void banco(View view){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("dados")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot doc : task.getResult()){

                                if(doc.getId().equals(chave.getText().toString())) {
                                    if (doc.get("status").toString().equals("1")) {
                                        update(doc.getId(), "0");
                                    } else {
                                        update(doc.getId(), "1");
                                    }
                                    notificacaoCriacao(doc.get("autenticacao").toString());
                                    break;
                                }else{
                                    notificacaoCriacao("not");
                                }

                            }
                        }
                    }
                });
    }

    public void message_auth(String autenticacao){
        Toast.makeText(MainActivity.this, "Auth: " + autenticacao, Toast.LENGTH_SHORT).show();
    }
    public void update(String documento, String valor){
        //colocar a collectionPath que sera atualizada.
        CollectionReference banco = conexao.collection("dados");
        banco.document(documento).update("status",valor);

    }
}