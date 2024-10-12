package com.oscar.apiconsumer;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView userRecyclerView;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userRecyclerView = findViewById(R.id.userRecyclerView);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button fetchButton = findViewById(R.id.fetchButton);
        fetchButton.setOnClickListener(v -> {
            ApiClient apiClient = new ApiClient(this);
            apiClient.fetchUsers();
        });
    }

    public void updateUserList(List<User> users) {
        userAdapter = new UserAdapter(users);
        userRecyclerView.setAdapter(userAdapter);
    }
}
