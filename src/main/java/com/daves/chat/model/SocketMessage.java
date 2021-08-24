package com.daves.chat.model;

import com.daves.chat.enums.SocketMessageType;
import org.json.JSONException;
import org.json.JSONObject;

public class SocketMessage {
    protected SocketMessageType type;
    protected JSONObject data = new JSONObject();

    public SocketMessage(SocketMessageType type) {
        this.type = type;
    }

    public SocketMessage(JSONObject jsonObject) throws JSONException {
        this.type = SocketMessageType.valueOf(jsonObject.getString("type"));
        this.data = jsonObject.getJSONObject("data");
    }

    public static SocketMessage fromJSON(JSONObject jsonObject) throws JSONException {
        return new SocketMessage(jsonObject);
    }

    public SocketMessageType getType() {
        return type;
    }

    public void setType(SocketMessageType type) {
        this.type = type;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public Object getValue(String key) throws JSONException {
        return data.get(key);
    }

    public void putData(String key, Object value) {
        try {
            data.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", type);
        jsonObject.put("data", data);
        return jsonObject;
    }
}
