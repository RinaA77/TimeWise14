package com.example.timewise14.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ReminderRepository {

    private ReminderDao reminderDao;
    private LiveData<List<Reminder>> allReminders;

    public ReminderRepository(Application application) {
        ReminderDatabase db = ReminderDatabase.getDatabase(application);
        reminderDao = db.reminderDao();
        allReminders = reminderDao.getAllReminders();
    }

    public LiveData<List<Reminder>> getAllReminders() {
        return allReminders;
    }

    public void insert(Reminder reminder) {
        new InsertReminderAsyncTask(reminderDao).execute(reminder);
    }

    public void delete(Reminder reminder) {
        new DeleteReminderAsyncTask(reminderDao).execute(reminder);
    }

    public void update(Reminder reminder) {
        new UpdateReminderAsyncTask(reminderDao).execute(reminder);
    }

    // AsyncTasks
    private static class InsertReminderAsyncTask extends AsyncTask<Reminder, Void, Void> {
        private ReminderDao reminderDao;

        private InsertReminderAsyncTask(ReminderDao reminderDao) {
            this.reminderDao = reminderDao;
        }

        @Override
        protected Void doInBackground(Reminder... reminders) {
            reminderDao.insert(reminders[0]);
            return null;
        }
    }

    private static class DeleteReminderAsyncTask extends AsyncTask<Reminder, Void, Void> {
        private ReminderDao reminderDao;

        private DeleteReminderAsyncTask(ReminderDao reminderDao) {
            this.reminderDao = reminderDao;
        }

        @Override
        protected Void doInBackground(Reminder... reminders) {
            reminderDao.delete(reminders[0]);
            return null;
        }
    }

    private static class UpdateReminderAsyncTask extends AsyncTask<Reminder, Void, Void> {
        private ReminderDao reminderDao;

        private UpdateReminderAsyncTask(ReminderDao reminderDao) {
            this.reminderDao = reminderDao;
        }

        @Override
        protected Void doInBackground(Reminder... reminders) {
            reminderDao.update(reminders[0]);
            return null;
        }
    }
}
