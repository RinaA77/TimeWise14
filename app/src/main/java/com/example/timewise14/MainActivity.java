package com.example.timewise14;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.timewise14.adapter.ReminderAdapter; // Import adapter dari package adapter
import com.example.timewise14.data.Reminder; // Import Reminder dari package data
import com.example.timewise14.viewmodel.ReminderViewModel; // Import ViewModel dari package viewmodel

import java.util.List;

public class MainActivity extends AppCompatActivity implements ReminderAdapter.OnItemClickListener {


    public static final String EXTRA_REMINDER_ID = "com.example.timewise14.EXTRA_REMINDER_ID";
    public static final String EXTRA_REMINDER_TITLE = "com.example.timewise14.EXTRA_REMINDER_TITLE";
    public static final String EXTRA_REMINDER_TIME = "com.example.timewise14.EXTRA_REMINDER_TIME";

    private ReminderViewModel reminderViewModel;
    private ReminderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RecyclerView recyclerView = findViewById(R.id.rv_reminders);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setHasFixedSize(true);


        adapter = new ReminderAdapter();
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(this);


        reminderViewModel= new ViewModelProvider(this).get(ReminderViewModel.class);

        reminderViewModel.getAllReminders().observe(this, new Observer<List<Reminder>>() {
            @Override
            public void onChanged(List<Reminder> reminders) {

                adapter.setReminders(reminders);
            }
        });


        FloatingActionButton fabAddReminder = findViewById(R.id.fab_add_reminder);
        fabAddReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, AddEditReminderActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemClick(Reminder reminder) {

        Intent intent = new Intent(MainActivity.this, AddEditReminderActivity.class);

        intent.putExtra(EXTRA_REMINDER_ID, reminder.getId());
        intent.putExtra(EXTRA_REMINDER_TITLE, reminder.getTitle());
        intent.putExtra(EXTRA_REMINDER_TIME, reminder.getTime());
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(Reminder reminder) {

        reminderViewModel.delete(reminder);
        Toast.makeText(this, "Pengingat dihapus!", Toast.LENGTH_SHORT).show();

        ReminderBroadcastReceiver.cancelReminder(this, reminder.getId());
    }
}
