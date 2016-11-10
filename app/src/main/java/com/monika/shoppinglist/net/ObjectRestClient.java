package com.monika.shoppinglist.net;

import android.content.Context;
import android.util.JsonReader;
import android.util.Log;

import com.monika.shoppinglist.R;
import com.monika.shoppinglist.content.Object;
import com.monika.shoppinglist.util.OkCancellableCall;
import com.monika.shoppinglist.util.OnErrorListener;
import com.monika.shoppinglist.util.OnSuccessListener;
import com.monika.shoppinglist.util.ResourceListReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Mónika on 2016. 11. 10..
 */

public class ObjectRestClient {
    private static final String TAG = ObjectRestClient.class.getSimpleName();

    private final OkHttpClient mOkHttpClient;
    private final String mApiUrl;
    private final String mObjectUrl;

    public ObjectRestClient(Context context) {
        mOkHttpClient = new OkHttpClient();
        mApiUrl = context.getString(R.string.api_url);
        mObjectUrl = mApiUrl.concat("/Object");
        Log.d(TAG, "ObjectRestClient created");
    }

    public List<Object> getAll() throws IOException { //sync operation
        Request request = new Request.Builder().url(mObjectUrl).build();
        Call call = null;
        try {
            call = mOkHttpClient.newCall(request);
            Log.d(TAG, "getAll started");
            Response response = call.execute();
            Log.d(TAG, "getAll completed");
            JsonReader reader = new JsonReader(new InputStreamReader(response.body().byteStream(), "UTF-8"));
            return new ResourceListReader<Object>(new ObjectReader()).read(reader);
        } catch (IOException e) {
            Log.e(TAG, "getAll failed", e);
            throw e;
        }
    }

    public OkCancellableCall getAllAsync(final OnSuccessListener<List<Object>> osl, final OnErrorListener oel) { //async operation
        Request request = new Request.Builder().url(mObjectUrl).build();
        Call call = null;
        try {
            call = mOkHttpClient.newCall(request);
            Log.d(TAG, "getAllAsync enqueued");
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(TAG, "getAllAsync failed", e);
                    oel.onError(e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.d(TAG, "getAllAsync completed");
                    JsonReader reader = new JsonReader(new InputStreamReader(response.body().byteStream(), "UTF-8"));
                    osl.onSuccess(new ResourceListReader<Object>(new ObjectReader()).read(reader));
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "getAllAsync failed", e);
            oel.onError(e);
        } finally {
            return new OkCancellableCall(call);
        }
    }
}
