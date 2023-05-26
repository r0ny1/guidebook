package com.mirea.informatics.stats;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class APIRequests {
    private final OkHttpClient client = new OkHttpClient();

    private final String defurl = "http://localhost";

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public void doGetRequest(String url) {
        Request request = new Request.Builder()
                .url(defurl+"/"+url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                System.out.println(response.body().string());
            }
        });
    }

    public void doGetRequest(String url, String json){
        Request request = new Request.Builder()
                .url(defurl+"/"+url)
                .post(RequestBody.create(JSON, json))
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                System.out.println(response.body().string());
            }
        });
    }

    public void doPostRequest(String url, String json){
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(defurl+"/"+url)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                System.out.println(response.body().string());
            }
        });
    }

    public void doPutRequest(String url, String json){
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(defurl+"/"+url)
                .put(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                System.out.println(response.body().string());
            }
        });
    }

    public String MakeJson(String email, String password){
        return
                "{'email':'"+email+
                "','password':'"+password+
                "'}";
    }

    public String MakeJson(String email, String password, String name, String group){
        return
                "{'name':'"+name+
                "','password':'"+password+
                "','email':'"+email+
                "','group':'"+group+
                "'}";
    }
}