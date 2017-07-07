package com.example.andreiiorga.waiterapp.activities;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andreiiorga.waiterapp.AsyncTasks.AsynchronousHttpClient;
import com.example.andreiiorga.waiterapp.Model.Task;
import com.example.andreiiorga.waiterapp.R;
import com.example.andreiiorga.waiterapp.listAdapters.TaskListAdapter;
import com.example.andreiiorga.waiterapp.staticUtils.StaticStrings;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class TasksActivity extends AppCompatActivity {
    TaskListAdapter taskListAdapter;
    ListView taskListView;
    TextView tx_name;
    SwipeRefreshLayout swipeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        taskListView = (ListView) findViewById(R.id.tasks_list_view);
        tx_name = (TextView) findViewById(R.id.waiter_name);
        tx_name.setText(StaticStrings.WAITERS_NAME);

        taskListAdapter = new TaskListAdapter(this, R.layout.row_tasks);
        taskListView.setEmptyView(findViewById(R.id.empty_list_image_view));
        taskListView.setAdapter(taskListAdapter);
        getTasks();
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_to_refresh_layout);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                String url = StaticStrings.GET_TASKS_ROUTE  + StaticStrings.TABLE_ZONE;
                AsynchronousHttpClient.get(url, null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                        taskListAdapter.clear();
                        try{
                            for (int i = 0; i < timeline.length(); i++) {
                                Task task = new Task(timeline.getJSONObject(i));
                                taskListAdapter.add(task);
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        taskListAdapter.notifyDataSetChanged();
                        swipeLayout.setRefreshing(false);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }
                });


            }
        });

        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Task task = (Task) taskListView.getItemAtPosition(i);

                if(task.getTaskType().equals("HELP")){

                }else{
                    Intent intent = new Intent(getBaseContext(), TaskDetailsActivity.class);


                    intent.putExtra("json", task.getTaskJson().toString());
                    intent.putExtra("tableNo", task.getTableNo());
                    intent.putExtra("done", task.getDone());
                    intent.putExtra("taskId", task.getId());


                    startActivityForResult(intent, 1);
                }
            }
        });

    }

    public void getTasks() {
        String url = StaticStrings.GET_TASKS_ROUTE  + StaticStrings.TABLE_ZONE;


        AsynchronousHttpClient.get(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                taskListAdapter.clear();
                try{
                    for (int i = 0; i < timeline.length(); i++) {
                        Task task = new Task(timeline.getJSONObject(i));
                        taskListAdapter.add(task);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                taskListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTasks();
    }

    @Override
    public void onBackPressed() {
    }
}
