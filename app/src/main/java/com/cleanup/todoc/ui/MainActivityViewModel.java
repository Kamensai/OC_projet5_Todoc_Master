package com.cleanup.todoc.ui;


import android.app.Application;

import com.cleanup.todoc.ProjectRepository;
import com.cleanup.todoc.TaskRepository;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

public class MainActivityViewModel extends AndroidViewModel {

    private TaskRepository mTaskRepository;

    private ProjectRepository mProjectRepository;


    private MutableLiveData<List<Task>> updateTasks = new MutableLiveData<>();

    private MutableLiveData<List<Project>> updateProjects = new MutableLiveData<>();

    private MediatorLiveData<List<Task>> mListMediatorLiveData = new MediatorLiveData<>();

    //private final LiveData<List<Task>> mAllTasks;

    public MainActivityViewModel(Application application) {
        super(application);
        mTaskRepository = new TaskRepository(application);
        mProjectRepository = new ProjectRepository(application);
        // mAllTasks = mRepository.getAllTasks();
        // mAllProjects = mRepository.getAllProjects();
        mProjectRepository.getAllProjectsLiveData().observeForever(new Observer<List<Project>>() {
            @Override
            public void onChanged(List<Project> projects) {
                updateProjects.postValue(projects);
            }
        });
        mTaskRepository.getAllTasksLiveData().observeForever(new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                updateTasks.postValue(tasks);
            }
        });
    }

   // public LiveData<List<Task>> getAllTasks() { return mAllTasks; }

    public void getTasks(){
        mTaskRepository.getTasks();
    }

    public void getProjects(){
        mProjectRepository.getProjects();
    }


    public LiveData<List<Project>> getAllProjectsLiveData(){
        return updateProjects;
    }

    public LiveData<List<Task>> getAllTasksLiveData() {
        return updateTasks;
    }

    public void insert(Task task) { mTaskRepository.insert(task); }

    public void delete(Task task) { mTaskRepository.remove(task); }
}
