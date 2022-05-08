package com.example.taskmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String CONTENT = "content";
    private TaskOpenHelper taskOpenHelper;
    List<TaskModel> tasks;
    ListView listView;
    TaskAdapter adapter;
    FloatingActionButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addButton = findViewById(R.id.floatingActionButton2);
        tasks = new ArrayList<TaskModel>();
        taskOpenHelper = new TaskOpenHelper(this);

        tasks = taskOpenHelper.onGetAll();

        listView = findViewById(R.id.listView);
        adapter = new TaskAdapter(this, R.layout.activity_task_item);
        System.out.print(tasks);
        adapter.addAll(tasks);
        listView.setAdapter(adapter);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        tasks = taskOpenHelper.onGetAll();
        adapter.clear();
        adapter.addAll(tasks);
        System.out.print(tasks);
        listView.setAdapter(adapter);
    }

    public void onAdd(View v){
        Intent detail = new Intent(this, Detail.class);
        detail.putExtra(Constants.ACTION, Constants.INSERT_ACTION);
        startActivity(detail);
    }


}