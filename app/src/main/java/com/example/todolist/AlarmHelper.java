package com.example.todolist;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class AlarmHelper {

    public static void setReminder(Context context, TaskItem task) {
        if (task.hasReminder()) {
            scheduleAlarm(context, task.getReminderDateTime(), task, 1001);
        }
        scheduleAlarm(context, LocalDateTime.of(task.getDeadlineDate(), task.getDeadlineTime()), task, 1002);
    }

    @SuppressLint("ScheduleExactAlarm")
    private static void scheduleAlarm(Context context, LocalDateTime dateTime, TaskItem task, int requestCode) {
        long triggerTime = dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        Intent intent = new Intent(context, MyNotification.class);
        intent.putExtra("taskName", task.getName());
        intent.putExtra("taskContent", task.getContent());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
        }
    }
}
