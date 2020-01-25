package com.websocket.app.webSocket;

import org.json.JSONObject;

public interface OnCallBackListener {
    public abstract void onSend(JSONObject object);

    public abstract void onReceive(JSONObject object);
}
