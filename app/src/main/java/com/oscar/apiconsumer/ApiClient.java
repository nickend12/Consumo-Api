package com.oscar.apiconsumer;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class ApiClient {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/users"; // Cambia la URL según sea necesario
    private static final String TAG = "ApiClient";
    private final Context context;

    public ApiClient(Context context) {
        this.context = context;
    }

    public void fetchUsers() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(BASE_URL)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                showToast("Error al consumir la API: " + e.getMessage());
                Log.e(TAG, "Error en la llamada a la API: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    processApiResponse(responseData);
                } else {
                    showToast("Error en la respuesta de la API: " + response.message());
                    Log.e(TAG, "Error en la respuesta: " + response.message());
                }
            }
        });
    }

    private void processApiResponse(String responseData) {
        Gson gson = new Gson();
        Type userListType = new TypeToken<List<User>>() {}.getType();
        List<User> users = gson.fromJson(responseData, userListType);

        ((MainActivity) context).runOnUiThread(() -> {
            ((MainActivity) context).updateUserList(users);
        });
    }

    private void showToast(final String message) {
        ((MainActivity) context).runOnUiThread(() -> Toast.makeText(context, message, Toast.LENGTH_SHORT).show());
    }
}
