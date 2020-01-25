package com.websocket.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.websocket.app.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends BaseAdapter {

    private List<JSONObject> messageList = new ArrayList<>();

    @Override
    public int getCount() {
        return messageList.size();
    }

    @Override
    public Object getItem(int position) {
        return messageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if (view == null)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);

        initView(view);
        initAction(position);

        return view;
    }

    public void addItem(JSONObject object) {
        messageList.add(object);
        notifyDataSetChanged();
    }

    private TextView receivedMessage, sentMessage;

    private void initView(View view) {
        sentMessage = view.findViewById(R.id.sentMessage);
        receivedMessage = view.findViewById(R.id.receivedMessage);
    }

    private void initAction(int position) {
        JSONObject object = messageList.get(position);
        try {
            if (object.getBoolean("byServer")) {
                receivedMessage.setVisibility(View.VISIBLE);
                receivedMessage.setText(object.getString("message"));
                sentMessage.setVisibility(View.INVISIBLE);
            } else {
                sentMessage.setVisibility(View.VISIBLE);
                sentMessage.setText(object.getString("message"));
                receivedMessage.setVisibility(View.INVISIBLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
