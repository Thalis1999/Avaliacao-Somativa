package com.appgjob.formativa_aula_atividade_android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class BroadcastSMS extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            Object[] pdus = (Object[]) extras.get("pdus");
            SmsMessage[] sms = new SmsMessage[pdus.length];
            String conteudo = "";
            for (int i=0 ; i<pdus.length ; i++) {
                sms[i] = SmsMessage.createFromPdu(
                        (byte[]) pdus[i], extras.getString("format"));
                conteudo += sms[i].getMessageBody();
            }
            Toast.makeText(context, conteudo, Toast.LENGTH_SHORT).show();
        }
    }
}