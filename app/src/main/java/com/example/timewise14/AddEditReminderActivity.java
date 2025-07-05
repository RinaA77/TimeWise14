package com.example.timewise14;

import static android.content.Intent.getIntent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker; // PERBAIKI: import TimePicker
import android.widget.Toast;

import com.example.timewise14.data.Reminder; // PERBARUI: import sesuai nama package baru
import com.example.timewise14.viewmodel.ReminderViewModel; // PERBARUI: import sesuai nama package baru

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.Executors;

public class AddEditReminderActivity extends AppCompatActivity {

    private EditText etTitle;
    private Button btnSelectDate, btnSelectTime, btnSaveReminder;
    private TextView tvSelectedDate, tvSelectedTime;

    private Calendar calendar;
    private ReminderViewModel reminderViewModel;
    private int reminderId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_reminder);

        etTitle = findViewById(R.id.et_title);
        btnSelectDate = findViewById(R.id.btn_select_date);
        btnSelectTime = findViewById(R.id.btn_select_time);
        btnSaveReminder = findViewById(R.id.btn_save_reminder);
        tvSelectedDate = findViewById(R.id.tv_selected_date);
        tvSelectedTime = findViewById(R.id.tv_selected_time);

        calendar = Calendar.getInstance();
        reminderViewModel = new ViewModelProvider(this).get(ReminderViewModel.class);

        Intent intent = getIntent();
        if (intent.hasExtra(MainActivity.EXTRA_REMINDER_ID)) {
            setTitle("Edit Pengingat");
            reminderId = intent.getIntExtra(MainActivity.EXTRA_REMINDER_ID, -1);
            etTitle.setText(intent.getStringExtra(MainActivity.EXTRA_REMINDER_TITLE));
            calendar.setTimeInMillis(intent.getLongExtra(MainActivity.EXTRA_REMINDER_TIME, System.currentTimeMillis()));
            updateDateAndTimeTextView();
        } else {
            setTitle("Tambah Pengingat Baru");
        }

        btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        btnSelectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });

        btnSaveReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveReminder();
            }
        });
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateDateAndTimeTextView();
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void showTimePickerDialog() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        calendar.set(Calendar.SECOND, 0);
                        updateDateAndTimeTextView();
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    private void updateDateAndTimeTextView() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMMyyyy", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        tvSelectedDate.setText("Tanggal: " + dateFormat.format(calendar.getTime()));
        tvSelectedTime.setText("Jam: " + timeFormat.format(calendar.getTime()));
    }

    private void saveReminder() {
        String title = etTitle.getText().toString().trim();
        long reminderTime = calendar.getTimeInMillis();

        if (TextUtils.isEmpty(title)) {
            etTitle.setError("Judul pengingat tidak boleh kosong");
            return;
        }

        if (reminderTime <= System.currentTimeMillis()) {
            Toast.makeText(this, "Waktu pengingat harus di masa depan", Toast.LENGTH_SHORT).show();
            return;
        }

        Reminder newReminder = new Reminder(title, reminderTime);

        if (reminderId == -1) {



            Executors.newSingleThreadExecutor().execute(() -> {
                reminderViewModel.insert(newReminder);




























                runOnUiThread(() -> {
                    Toast.makeText(this, "Pengingat ditambahkan!", Toast.LENGTH_SHORT).show();



                    setAlarm(newReminder);
                    finish();
                });
            });
        } else {
            newReminder.setId(reminderId);
            reminderViewModel.update(newReminder);
            Toast.makeText(this, "Pengingat diperbarui!", Toast.LENGTH_SHORT).show();
            ReminderBroadcastReceiver.cancelReminder(this, reminderId);
            setAlarm(newReminder);
            finish();
        }
    }

    private void setAlarm(Reminder reminder) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, ReminderBroadcastReceiver.class);
        intent.setAction("com.example.projek14.ACTION_REMINDER");
        intent.putExtra("reminder_title", reminder.getTitle());
        intent.putExtra("reminder_id", reminder.getId());

        int pendingIntentId;
        if (reminderId == -1) {




            pendingIntentId = (int) System.currentTimeMillis();
        } else {
            pendingIntentId = reminder.getId();
        }


        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, pendingIntentId, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        if (alarmManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, reminder.getTime(), pendingIntent);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, reminder.getTime(), pendingIntent);
            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, reminder.getTime(), pendingIntent);
            }
            Toast.makeText(this, "Alarm diatur untuk " + reminder.getTitle(), Toast.LENGTH_SHORT).show();
        }
    }
}