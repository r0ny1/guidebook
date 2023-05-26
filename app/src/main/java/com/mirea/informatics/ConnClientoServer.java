package com.mirea.informatics;
import android.os.AsyncTask;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ConnClientoServer{
    OkHttpClient client = new OkHttpClient();
    public String url = "http://localhost:50797";


    public void whenSendPostRequest__thenCorrect()
            throws IOException {
        RequestBody formBody = new FormBody.Builder()
                .add("username", "test")
                .add("password", "test")
                .build();

        Request request = new Request.Builder()
                .url("http://localhost:50797" + "/users")
                .post(formBody)
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();

    }


    public void whenUploadFile__thenCorrect() throws IOException {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", "file.txt",
                        RequestBody.create(MediaType.parse("application/octet-stream"),
                                new File("src/test/resources/test.txt")))
                .build();

        Request request = new Request.Builder()
                .url("http://localhost:50797" + "/users/upload")
                .post(requestBody)
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        
    }


}




