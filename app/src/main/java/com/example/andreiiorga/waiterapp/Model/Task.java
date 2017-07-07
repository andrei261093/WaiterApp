package com.example.andreiiorga.waiterapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by andreiiorga on 26/06/2017.
 */

public class Task implements Serializable {
    private int id;

    private String waiterName;
    private JSONObject taskJson;
    private Boolean isDone;
    private String tableNo;
    private String date = "date";
    private String taskType;
    private String price;

    public Task(JSONObject taskJson) {
        try {
            this.taskJson = new JSONObject(taskJson.getString("taskJson"));
            this.waiterName = taskJson.getString("waiterName");
            this.id = taskJson.getInt("id");
            this.isDone = taskJson.getBoolean("isDone");
            this.tableNo = "" + this.taskJson.getString("tableNo");
            this.date = taskJson.getString("onCreate");
            this.taskType = taskJson.getString("taskType");
            this.price = this.taskJson.getString("totalPrice");
            //  this.tableNo = this.taskJson.getString("date");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWaiterName() {
        return waiterName;
    }

    public void setWaiterName(String waiterName) {
        this.waiterName = waiterName;
    }

    public JSONObject getTaskJson() {
        return taskJson;
    }

    public void setTaskJson(JSONObject taskJson) {
        this.taskJson = taskJson;
    }

    public Boolean getDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }

    public String getTableNo() {
        return tableNo;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
