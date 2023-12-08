package com.example.conectamovil.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.conectamovil.R;

public class ChatReceiverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_receiver);

        TextView receivedMessageTextView = findViewById(R.id.receivedMessageTextView);

        String receivedMessage = getIntent().getStringExtra("MESSAGE_KEY");

        receivedMessageTextView.setText(receivedMessage);
    }
}
