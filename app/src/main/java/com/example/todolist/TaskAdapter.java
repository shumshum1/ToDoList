package com.example.todolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<TaskItem> taskList;

    // בנאי המקבל רשימת משימות
    public TaskAdapter(List<TaskItem> taskList) {
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        TaskItem task = taskList.get(position);
        holder.txtTaskName.setText(task.getName());
        holder.txtTaskContent.setText(task.getContent());
        holder.txtPriority.setText("Priority: " + task.getPriority());
        holder.txtDeadline.setText("Due: " + task.getDeadlineDate() + " " + task.getDeadlineTime());
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    // מעדכן את הרשימה במקרה של שינוי
    public void updateTaskList(List<TaskItem> newTaskList) {
        this.taskList = newTaskList;
        notifyDataSetChanged();
    }

    // מחזיק את ה-View של כל משימה
    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView txtTaskName, txtTaskContent, txtPriority, txtDeadline;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTaskName = itemView.findViewById(R.id.txtTaskName);
            txtTaskContent = itemView.findViewById(R.id.txtTaskContent);
            txtPriority = itemView.findViewById(R.id.txtPriority);
            txtDeadline = itemView.findViewById(R.id.txtDeadline);
        }
    }
}
