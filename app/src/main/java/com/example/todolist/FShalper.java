package com.example.todolist;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class FShalper {
    private static final String TAG = "FShalper";
    private final FirebaseFirestore db;
    private FirebaseAuth firebaseAuth;
    private String email;
    public FShalper() {
        this.db = FirebaseFirestore.getInstance();
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.email = firebaseAuth.getCurrentUser().getEmail();
    }

    // 1. יצירת רמה חדשה עבור המשתמש
    public void createUserLevel(String email) {
        Map<String, Object> levelData = new HashMap<>();
        levelData.put("email", email);
        levelData.put("level", 1); // רמה התחלתית
        levelData.put("createdAt", System.currentTimeMillis());

        db.collection("users").document(email)
                .set(levelData)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "User level created successfully"))
                .addOnFailureListener(e -> Log.e(TAG, "Error creating user level", e));
    }

    // 2.i הוספת משימה
    public void addTask(String userEmail, TaskItem task) {
        CollectionReference tasksRef = db.collection("users").document(userEmail).collection("tasks");

        Map<String, Object> taskData = new HashMap<>();
        taskData.put("name", task.getName());
        taskData.put("content", task.getContent());
        taskData.put("priority", task.getPriority());
        taskData.put("deadlineDate", task.getDeadlineDate().toString());
        taskData.put("deadlineTime", task.getDeadlineTime().toString());
        taskData.put("createdAt", System.currentTimeMillis());
        taskData.put("hasReminder", task.hasReminder());
        taskData.put("reminderDateTime", task.getReminderDateTime() != null ? task.getReminderDateTime().toString() : null);
        taskData.put("reminderId", task.getReminderId());

        tasksRef.add(taskData)
                .addOnSuccessListener(documentReference -> Log.d(TAG, "Task added successfully with ID: " + documentReference.getId()))
                .addOnFailureListener(e -> Log.e(TAG, "Error adding task", e));
    }

    // 2.ii קריאת כל המשימות
    public List<TaskItem> getAllTasks(String userEmail) {
        List<TaskItem> taskList = new ArrayList<>();
        CollectionReference tasksRef = db.collection("users").document(userEmail).collection("tasks");

        try {
            QuerySnapshot querySnapshot = Tasks.await(tasksRef.get());

            for (QueryDocumentSnapshot document : querySnapshot) {
                TaskItem task = new TaskItem(
                        document.getString("name"),
                        document.getString("content"),
                        document.getLong("priority") != null ? document.getLong("priority").intValue() : 1,
                        LocalDate.parse(document.getString("deadlineDate")),
                        LocalTime.parse(document.getString("deadlineTime")),
                        document.getBoolean("hasReminder") != null ? document.getBoolean("hasReminder") : false,
                        document.getString("reminderDateTime") != null ? LocalDateTime.parse(document.getString("reminderDateTime")) : null,
                        document.getLong("reminderId") != null ? document.getLong("reminderId").intValue() : 0
                );
                taskList.add(task);
            }
        } catch (ExecutionException | InterruptedException e) {
            Log.e(TAG, "Error fetching tasks", e);
        }

        return taskList;
    }

    // 2.iii מחיקת משימה
    public void deleteTask(String userEmail, String taskId) {
        DocumentReference taskRef = db.collection("users").document(userEmail).collection("tasks").document(taskId);

        taskRef.delete()
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Task deleted successfully"))
                .addOnFailureListener(e -> Log.e(TAG, "Error deleting task", e));
    }
}
