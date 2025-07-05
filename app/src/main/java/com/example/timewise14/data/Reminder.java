package com.example.timewise14.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reminders")
public class Reminder {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "time")
    public long time; // Unix timestamp

    public Reminder(String title, long time) {
        this.title = title;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public long getTime() {
        return time;
    }

    public void setId(int id) {
        this.id = id;
    }
}