package com.example.todolist;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AddMissionFragment extends Fragment {

    private EditText editTextTaskName, editTextTaskContent, editTextPriority,
            editTextDeadlineDate, editTextDeadlineTime, editTextReminderDateTime;
    private CheckBox checkBoxReminder;
    private Button buttonAddTask;

    public AddMissionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_mission, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // השגת האלמנטים מה-XML
        editTextTaskName = view.findViewById(R.id.editTextTaskName);
        editTextTaskContent = view.findViewById(R.id.editTextTaskContent);
        editTextPriority = view.findViewById(R.id.editTextPriority);
        editTextDeadlineDate = view.findViewById(R.id.editTextDeadlineDate);
        editTextDeadlineTime = view.findViewById(R.id.editTextDeadlineTime);
        checkBoxReminder = view.findViewById(R.id.checkBoxReminder);
        editTextReminderDateTime = view.findViewById(R.id.editTextReminderDateTime);
        buttonAddTask = view.findViewById(R.id.buttonAddTask);

        // שליטה על נראות שדה ההתראה
        checkBoxReminder.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                editTextReminderDateTime.setVisibility(View.VISIBLE);
            } else {
                editTextReminderDateTime.setVisibility(View.GONE);
            }
        });

        // מאזין ללחיצה על הכפתור להוספת משימה
        buttonAddTask.setOnClickListener(v -> saveTask());
    }

    private void saveTask() {
        String name = editTextTaskName.getText().toString().trim();
        String content = editTextTaskContent.getText().toString().trim();
        String priorityStr = editTextPriority.getText().toString().trim();
        String deadlineDateStr = editTextDeadlineDate.getText().toString().trim();
        String deadlineTimeStr = editTextDeadlineTime.getText().toString().trim();
        boolean hasReminder = checkBoxReminder.isChecked();
        String reminderDateTimeStr = editTextReminderDateTime.getText().toString().trim();

        // בדיקות תקינות
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(content) || TextUtils.isEmpty(priorityStr) ||
                TextUtils.isEmpty(deadlineDateStr) || TextUtils.isEmpty(deadlineTimeStr)) {
            Toast.makeText(getContext(), "נא למלא את כל השדות", Toast.LENGTH_SHORT).show();
            return;
        }

        int priority;
        try {
            priority = Integer.parseInt(priorityStr);
            if (priority < 1 || priority > 3) {
                Toast.makeText(getContext(), "העדיפות צריכה להיות בין 1 ל-3", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "העדיפות חייבת להיות מספר", Toast.LENGTH_SHORT).show();
            return;
        }

        LocalDate deadlineDate;
        LocalTime deadlineTime;
        try {
            deadlineDate = LocalDate.parse(deadlineDateStr);
            deadlineTime = LocalTime.parse(deadlineTimeStr);
        } catch (Exception e) {
            Toast.makeText(getContext(), "פורמט תאריך/שעה שגוי (YYYY-MM-DD HH:MM)", Toast.LENGTH_SHORT).show();
            return;
        }

        LocalDateTime reminderDateTime = null;
        int reminderId = -1;

        if (hasReminder) {
            if (TextUtils.isEmpty(reminderDateTimeStr)) {
                Toast.makeText(getContext(), "נא להזין תאריך התראה", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                reminderDateTime = LocalDateTime.parse(reminderDateTimeStr);
                reminderId = (int) (Math.random() * 10000); // יצירת ID רנדומלי להתראה
            } catch (Exception e) {
                Toast.makeText(getContext(), "פורמט תאריך התראה שגוי (YYYY-MM-DDTHH:MM)", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // יצירת אובייקט `Task`
        TaskItem newTask = new TaskItem(name, content, priority, deadlineDate, deadlineTime, hasReminder, reminderDateTime, reminderId);
        Log.i("AddMissionFragment","task saved");
        FShalper fShalper = new FShalper();
        fShalper.addTask("email",newTask);
        // כאן תוכל לשמור את המשימה ל-DB או להעביר אותה לרשימה
        Toast.makeText(getContext(), "משימה נוספה בהצלחה!", Toast.LENGTH_SHORT).show();
    }
}
