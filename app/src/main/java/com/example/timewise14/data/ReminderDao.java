// app/src/main/java/com/example/projek14/data/ReminderDao.java
package com.example.timewise14.data; // PERBARUI: package sesuai nama package baru

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ReminderDao {
    @Insert
    void insert(com.example.timewise14.data.Reminder reminder);

    @Update
    void update(com.example.timewise14.data.Reminder reminder);

    @Delete
    void delete(com.example.timewise14.data.Reminder reminder);

    @Query("SELECT * FROM reminders ORDER BY time ASC")
    LiveData<List<com.example.timewise14.data.Reminder>> getAllReminders();

    @Query("SELECT * FROM reminders WHERE id = :reminderId")
    com.example.timewise14.data.Reminder getReminderById(int reminderId);
}