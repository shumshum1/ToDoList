package com.example.todolist;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AllMissionFragment extends Fragment {

    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private List<TaskItem> taskList;
    public AllMissionFragment() {
        // חובה בנאי ריק לפרגמנט
    }

    public static AllMissionFragment newInstance() {
        return new AllMissionFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_mission, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FShalper fShalper = new FShalper();
        // אתחול ה-RecyclerView
        recyclerView = view.findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // יצירת רשימת משימות לדוגמה (במקום טעינת נתונים מ-Firestore)
        taskList = new ArrayList<>();
        taskList = fShalper.getAllTasks("email");
        // אתחול ה-Adapter
        taskAdapter = new TaskAdapter(taskList);
        recyclerView.setAdapter(taskAdapter);
    }

    // ניתן להוסיף פונקציה לעדכון הרשימה בעתיד
    public void updateTaskList(List<TaskItem> newTaskList) {
        taskAdapter.updateTaskList(newTaskList);
    }
}
