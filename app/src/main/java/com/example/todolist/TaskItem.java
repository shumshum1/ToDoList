package com.example.todolist;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TaskItem {
    private String name; // שם המשימה
    private String content; // תוכן המשימה
    private int priority; // עדיפות (לדוגמה: 1 - נמוכה, 2 - בינונית, 3 - גבוהה)
    private LocalDate deadlineDate; // תאריך דד ליין
    private LocalTime deadlineTime; // שעת יעד
    private LocalDateTime createdAt; // תאריך ושעת יצירת המשימה
    private boolean hasReminder; // האם יש התראה
    private LocalDateTime reminderDateTime; // תאריך ושעת התראה
    private int reminderId; // ID של ההתראה

    // בנאי
    public TaskItem(String name, String content, int priority, LocalDate deadlineDate, LocalTime deadlineTime,
                    boolean hasReminder, LocalDateTime reminderDateTime, int reminderId) {
        this.name = name;
        this.content = content;
        this.priority = priority;
        this.deadlineDate = deadlineDate;
        this.deadlineTime = deadlineTime;
        this.createdAt = LocalDateTime.now(); // התאריך והשעה הנוכחיים בזמן יצירת המשימה
        this.hasReminder = hasReminder;
        this.reminderDateTime = reminderDateTime;
        this.reminderId = reminderId;
    }

    public TaskItem() {

    }

    // גטרים
    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public int getPriority() {
        return priority;
    }

    public LocalDate getDeadlineDate() {
        return deadlineDate;
    }

    public LocalTime getDeadlineTime() {
        return deadlineTime;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean hasReminder() {
        return hasReminder;
    }

    public LocalDateTime getReminderDateTime() {
        return reminderDateTime;
    }

    public int getReminderId() {
        return reminderId;
    }

    // סטרים
    public void setName(String name) {
        this.name = name;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setDeadlineDate(LocalDate deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public void setDeadlineTime(LocalTime deadlineTime) {
        this.deadlineTime = deadlineTime;
    }

    public void setHasReminder(boolean hasReminder) {
        this.hasReminder = hasReminder;
    }

    public void setReminderDateTime(LocalDateTime reminderDateTime) {
        this.reminderDateTime = reminderDateTime;
    }

    public void setReminderId(int reminderId) {
        this.reminderId = reminderId;
    }

    // הדפסה של פרטי המשימה
    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", priority=" + priority +
                ", deadlineDate=" + deadlineDate +
                ", deadlineTime=" + deadlineTime +
                ", createdAt=" + createdAt +
                ", hasReminder=" + hasReminder +
                ", reminderDateTime=" + reminderDateTime +
                ", reminderId=" + reminderId +
                '}';
    }
}
