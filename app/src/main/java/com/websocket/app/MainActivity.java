package com.websocket.app;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.websocket.app.adapter.MessageAdapter;
import com.websocket.app.webSocket.OnCallBackListener;
import com.websocket.app.webSocket.WebSocketHelper;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    //listView and Adapter
    private ListView listView;
    private MessageAdapter adapter;
    //chat items
    private EditText edtMessage;
    private TextView txtSendMessage;
    //webSocket
    private WebSocketHelper webSocketHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initAction();
    }

    private void initView() {
        listView = findViewById(R.id.lstView);
        edtMessage = findViewById(R.id.edtMsg);
        txtSendMessage = findViewById(R.id.txtSend);
    }

    private void initAction() {
        initWebSocket();
        initListView();

        txtSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage(edtMessage.getText().toString());
            }
        });
    }

    private void initListView() {
        adapter = new MessageAdapter();
        listView.setAdapter(adapter);
    }

    private void addItemToAdapter(JSONObject object) {
        adapter.addItem(object);
        listView.post(new Runnable() {
            @Override
            public void run() {
                listView.setSelection(adapter.getCount() - 1);
            }
        });
    }

    private void initWebSocket() {
        webSocketHelper = new WebSocketHelper(MainActivity.this, new OnCallBackListener() {
            @Override
            public void onSend(JSONObject jsonObject) {
                addItemToAdapter(jsonObject);
            }

            @Override
            public void onReceive(JSONObject jsonObject) {
                addItemToAdapter(jsonObject);
            }
        });
    }

    private void sendMessage(String message) {
        webSocketHelper.sendMessage(message);
        edtMessage.getText().clear();
    }

}
