package com.example.taskmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Detail extends AppCompatActivity {

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String CONTENT = "content";
    private String action;
    EditText txtname;
    EditText txtcontent;
    TaskOpenHelper helper ;
    private String idTask;
    private String nameTask;
    private String contentTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        txtname = findViewById(R.id.txtname);
        txtcontent = findViewById(R.id.txtcontent);
        helper = new TaskOpenHelper(this);

        Intent intent = getIntent();
        idTask = intent.getStringExtra(Detail.ID);
        nameTask = intent.getStringExtra(Detail.NAME);
        contentTask = intent.getStringExtra(Detail.CONTENT);
        action = intent.getStringExtra(Constants.ACTION);

        txtname.setText(nameTask);
        txtcontent.setText(contentTask);
    }

    public void onSave(View v){

        try{

            if(action.equals(Constants.INSERT_ACTION)){
                String id = "id" + txtname.getText().toString();
                String name = txtname.getText().toString();
                String content = txtcontent.getText().toString();
                TaskModel newTask = new TaskModel(id, name, content);
                helper.onInsert(newTask);

            }
            else{
                if(action.equals(Constants.EDIT_ACTION)){
                    String name = txtname.getText().toString();
                    String content = txtcontent.getText().toString();
                    TaskModel newTask = new TaskModel(this.idTask, name, content);
                    helper.onUpdate(newTask);
                }
            }

            Toast.makeText(this," Success", Toast.LENGTH_SHORT).show();
            Intent back = new Intent(this, MainActivity.class);
            finish();
            startActivity(back);

        }catch(SQLiteException e){
            txtname.setText("");
            txtcontent.setText("");
            Toast.makeText(this," Fail to add new product", Toast.LENGTH_SHORT).show();
        }
    }
}