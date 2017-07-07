package com.example.andreiiorga.waiterapp.listAdapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.andreiiorga.waiterapp.AsyncTasks.AsynchronousHttpClient;
import com.example.andreiiorga.waiterapp.Model.Task;
import com.example.andreiiorga.waiterapp.R;
import com.example.andreiiorga.waiterapp.staticUtils.StaticStrings;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by andreiiorga on 26/06/2017.
 */

public class TaskListAdapter extends ArrayAdapter<Task>{
    List<Task> taskList = new ArrayList<>();

    public TaskListAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }

    @Override
    public int getCount() {
        return taskList.size();
    }

    @Nullable
    @Override
    public Task getItem(int position) {
        return taskList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;

        row = convertView;
        final TaskHolder taskHolder;

        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_tasks, parent, false);
            taskHolder = new TaskHolder();

            taskHolder.tx_table_name = (TextView) row.findViewById(R.id.tx_table_name);
            taskHolder.tx_date = (TextView) row.findViewById(R.id.tx_date);
            taskHolder.checkBox = (CheckBox) row.findViewById(R.id.checkBox);

            row.setTag(taskHolder);

        } else {
            taskHolder = (TaskHolder) row.getTag();
        }

        final Task task = (Task) this.getItem(position);
        if(task.getTaskType().equals("CHECK")){
            taskHolder.tx_table_name.setText("Table no: " + task.getTableNo() + " - " + task.getTaskType() + " " + task.getPrice() + " lei");
        }else if(task.getTaskType().equals("HELP")){
            taskHolder.tx_table_name.setText("Go to table " + task.getTableNo() + " to offer help");
        }else{
            taskHolder.tx_table_name.setText("Table no: " + task.getTableNo());
        }

        taskHolder.tx_date.setText(task.getDate());

        if (task.getDone()) {
            taskHolder.checkBox.setChecked(true);
            taskHolder.checkBox.setEnabled(false);
        } else {
            taskHolder.checkBox.setChecked(false);
            taskHolder.checkBox.setEnabled(true);
        }


        taskHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AsynchronousHttpClient.post(StaticStrings.MARK_TASKS_AS_DONE_ROUTE + task.getId(), null, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        int s = 3;
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });
            }
        });


        return row;
    }
    public void add(Task product) {
        super.add(product);
        taskList.add(product);
    }

    static class TaskHolder {
        TextView tx_table_name, tx_date;
        CheckBox checkBox;
    }

    @Override
    public void clear() {
        taskList.clear();
        super.clear();
        notifyDataSetChanged();
    }
}
