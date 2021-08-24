package com.cleanup.todoc;

import android.app.Application;

import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.room.RoomDatabase;
import com.cleanup.todoc.service.TaskDao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

public class TaskRepository {
    private final TaskDao mTaskDao;

    private final MutableLiveData<List<Task>> mAllTasks = new MutableLiveData<>();

    public TaskRepository(Application application) {
        RoomDatabase db = RoomDatabase.getDatabase(application);
        mTaskDao = db.taskDao();
        // mAllTasks = mTaskDao.getAlphabetizedWords();
        // mAllProjects = mTaskDao.getAlphabetizedProjects();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Task>> getAllTasksLiveData() {
        return mAllTasks;
    }

    public void getTasks(){
        mTaskDao.getAlphabetizedTasks().observeForever(new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                mAllTasks.setValue(tasks);
            }
        });
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(final Task task) {
        RoomDatabase.databaseWriteExecutor.execute(() -> {
            mTaskDao.insert(task);
        });
    }

    public void remove(final Task task) {
        RoomDatabase.databaseWriteExecutor.execute(() -> {
            mTaskDao.delete(task);
        });
    }
}
