package com.example.andreiiorga.waiterapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.example.andreiiorga.waiterapp.AsyncTasks.AsynchronousHttpClient;
import com.example.andreiiorga.waiterapp.R;
import com.example.andreiiorga.waiterapp.listAdapters.ProductListAdapter;
import com.example.andreiiorga.waiterapp.staticUtils.StaticStrings;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class TaskDetailsActivity extends AppCompatActivity {

    private ListView tasksListView;
    private TextView tableNoTextView;
    private TextView task_details;
    private ProductListAdapter productListAdapter;
    private CheckBox checkBox;
    private TextView subtitleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        tasksListView = (ListView) findViewById(R.id.products_list_view);
        task_details = (TextView) findViewById(R.id.task_details);
        subtitleText = (TextView) findViewById(R.id.subtitle_text);
        tableNoTextView = (TextView) findViewById(R.id.table_number_tx);
        checkBox = (CheckBox) findViewById(R.id.mark_as_done);

        JSONObject json = null;
        try {
            json =  new JSONObject(getIntent().getExtras().getString("json"));
            tableNoTextView.setText(getIntent().getExtras().getString("tableNo"));

            if(json.getString("taskType").equals("CHECK")){
                task_details.setText("Take the check to table:");
                subtitleText.setText("Price: " + json.getString("totalPrice") + " lei");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        Boolean done = getIntent().getExtras().getBoolean("done");

        if (done) {
            checkBox.setChecked(true);
            checkBox.setEnabled(false);
        } else {
            checkBox.setChecked(false);
        }

        final String taskId = getIntent().getExtras().getInt("taskId") + "";

        checkBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AsynchronousHttpClient.post(StaticStrings.MARK_TASKS_AS_DONE_ROUTE + taskId, null, new AsyncHttpResponseHandler() {
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

        productListAdapter = new ProductListAdapter(this, R.layout.row_products);

        try{
            productListAdapter.addProducts(json);

        }catch (Exception e){

        }

        tasksListView.setAdapter(productListAdapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("result", "a");
        setResult(RESULT_OK,intent);
        finish();
    }
}
