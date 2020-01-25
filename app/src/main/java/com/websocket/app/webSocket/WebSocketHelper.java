package com.websocket.app.webSocket;

import android.app.Activity;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class WebSocketHelper {

    private Activity activity;
    private OnCallBackListener onCallBackListener;

    private WebSocket webSocket;
    private OkHttpClient client;
    private Request request;

    public WebSocketHelper(Activity activity, OnCallBackListener onCallBackListener) {
        this.activity = activity;
        this.onCallBackListener = onCallBackListener;
        initWebSocket();
    }

    private void initWebSocket() {
        client = new OkHttpClient();
        request = new Request.Builder().url("ws://192.168.1.57:8080").build();
        webSocket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                super.onClosed(webSocket, code, reason);
            }

            @Override
            public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                super.onClosing(webSocket, code, reason);
            }

            @Override
            public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
                super.onFailure(webSocket, t, response);
            }

            @Override
            public void onMessage(@NotNull WebSocket webSocket, @NotNull final String text) {
                super.onMessage(webSocket, text);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("message", text);
                            jsonObject.put("byServer", true);
                            onCallBackListener.onReceive(jsonObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
                super.onMessage(webSocket, bytes);
            }

            @Override
            public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
                super.onOpen(webSocket, response);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, "connect to server successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    public void sendMessage(String message) {
        if (!message.isEmpty()) {
            webSocket.send(message);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("message", message);
                jsonObject.put("byServer", false);
                onCallBackListener.onSend(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}
