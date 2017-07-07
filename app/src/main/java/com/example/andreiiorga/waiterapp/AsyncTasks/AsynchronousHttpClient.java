package com.example.andreiiorga.waiterapp.AsyncTasks;

/**
 * Created by andreiiorga on 26/06/2017.
 */
import com.example.andreiiorga.waiterapp.staticUtils.StaticStrings;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


public class AsynchronousHttpClient {
    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return StaticStrings.SERVER_ADDRESS + relativeUrl;
    }
}