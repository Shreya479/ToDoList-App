package com.example.slate.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.slate.AddNewTask;
import com.example.slate.MainActivity;
import com.example.slate.Model.ToDoModel;
import com.example.slate.R;
import com.example.slate.utils.DataBaseHandler;


import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter <ToDoAdapter.ViewHolder> {

    private List<ToDoModel> toDoList;
    private MainActivity activity;
    private DataBaseHandler db;

    public ToDoAdapter(DataBaseHandler db , MainActivity activity){
        this.db = db;
        this.activity = activity;
}

public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_layout,parent,false);
        return new ViewHolder(itemView);
}

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    public void OnBindViewHolder(ViewHolder holder, int position){
        db.openDatabase();
        ToDoModel item = toDoList.get(position);
        holder.tasks.setText(item.getTask());
        holder.tasks.setChecked(toBoolean(item.getStatus()));
        holder.tasks.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    db.updateStatus(item.getId(), 1);
                }
                else {
                    db.updateStatus(item.getId(),0);
                }
            }
        });
}

public int getItemCount(){
        return toDoList.size();
}

private boolean toBoolean(int n)
{
        return n!=0;
}

public void setTasks (List<ToDoModel> todoList ){
        this.toDoList = todoList;
        notifyDataSetChanged();
}
    public Context getContext(){

        return activity;
    }

    public void deleteItem(int position){
        ToDoModel item = toDoList.get(position);
        db.deleteTask(item.getId());
        toDoList.remove(position);
        notifyItemRemoved(position);
    }
public void editItem(int position){
        ToDoModel item = toDoList.get(position);
    Bundle bundle = new Bundle();
    bundle.putInt("id",item.getId());
    bundle.putString("task", item.getTask());
    AddNewTask fragment= new AddNewTask();
    fragment.setArguments(bundle);
    fragment.show(activity.getSupportFragmentManager(), AddNewTask.TAG);
}
public static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox tasks;

        ViewHolder(View view){
            super(view);
            tasks = view.findViewById(R.id.todoCheckBox);
        }
}
        }


