package com.cleanup.todoc;

import android.app.Application;

import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.room.TaskRoomDatabase;
import com.cleanup.todoc.service.TaskDao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

public class TaskRepository {
    private TaskDao mTaskDao;
    private MutableLiveData<List<Task>> mAllTasks = new MutableLiveData<>();


    public TaskRepository(Application application) {
        TaskRoomDatabase db = TaskRoomDatabase.getDatabase(application);
        mTaskDao = db.taskDao();
        // mAllTasks = mTaskDao.getAlphabetizedWords();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Task>> getAllTasks() {
        return mAllTasks;
    }

    public void getTasks(){
        mTaskDao.getAlphabetizedWords().observeForever(new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                mAllTasks.setValue(tasks);
            }
        });
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(final Task task) {
        TaskRoomDatabase.databaseWriteExecutor.execute(() -> {
            mTaskDao.insert(task);
        });
    }

    public void remove(final Task task) {
        TaskRoomDatabase.databaseWriteExecutor.execute(() -> {
            mTaskDao.delete(task);
        });
    }
}
