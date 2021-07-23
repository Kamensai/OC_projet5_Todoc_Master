package com.cleanup.todoc.model;


import android.app.Application;

import com.cleanup.todoc.TaskRepository;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

public class TaskViewModel extends AndroidViewModel {

    private TaskRepository mRepository;

    private final LiveData<List<Project>> mAllProjects;

    private MutableLiveData<List<Task>> updateTasks = new MutableLiveData<>();

    private MediatorLiveData<List<Task>> mListMediatorLiveData = new MediatorLiveData<>();

    //private final LiveData<List<Task>> mAllTasks;

    public TaskViewModel (Application application) {
        super(application);
        mRepository = new TaskRepository(application);
        // mAllTasks = mRepository.getAllTasks();
        mAllProjects = mRepository.getAllProjects();
        mRepository.getAllTasks().observeForever(new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                updateTasks.postValue(tasks);
            }
        });
    }

   // public LiveData<List<Task>> getAllTasks() { return mAllTasks; }

    public void getTasks(){
        mRepository.getTasks();
    }

    public LiveData<List<Project>> getAllProjects(){
        return mAllProjects;
    }

    public LiveData<List<Task>> getUpdateTasks() {
        return updateTasks;
    }

    public void insert(Task task) { mRepository.insert(task); }

    public void delete(Task task) { mRepository.remove(task); }
}
