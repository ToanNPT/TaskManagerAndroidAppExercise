package com.example.taskmanager;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TaskAdapter extends ArrayAdapter<TaskModel> {
    Activity context;
    int resource;
    ImageButton editbtn;
    ImageButton deletebtn;
    TaskOpenHelper helper;


    public TaskAdapter(Activity context, int resource) {
            super(context,resource);
            this.context = context;
            this.resource = resource;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View v = inflater.inflate(resource,null);

            helper = new TaskOpenHelper(context);
            TaskModel task = getItem(position);
            TextView taskName = v.findViewById(R.id.nameTask);
            TextView content = v.findViewById(R.id.contentTask);
            editbtn = v.findViewById(R.id.editTaskItem);
            deletebtn = v.findViewById(R.id.deleteTaskItem);

            deletebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    helper.onDelete(task);
                    remove(task);
                    notifyDataSetChanged();
                }
            });

            editbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent detail = new Intent(context, Detail.class);
                    detail.putExtra(Detail.ID, task.getId().toString());
                    detail.putExtra(Detail.NAME, task.getName().toString());
                    detail.putExtra(Detail.CONTENT, task.getContent().toString());
                    detail.putExtra(Constants.ACTION, Constants.EDIT_ACTION);
                    context.startActivity(detail);
                }
            });


            taskName.setText(task.getName());
            content.setText(task.getContent());

            return v;
        }
}
