package com.cleanup.todoc.ui;


import android.app.Application;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.ProjectRepository;
import com.cleanup.todoc.repositories.TaskRepository;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

public class MainActivityViewModel extends AndroidViewModel {

    private final TaskRepository mTaskRepository;

    private final ProjectRepository mProjectRepository;


    private final MutableLiveData<List<Task>> updateTasks = new MutableLiveData<>();

    private final MutableLiveData<List<Project>> updateProjects = new MutableLiveData<>();

    public MainActivityViewModel(Application application) {
        super(application);
        mTaskRepository = new TaskRepository(application);
        mProjectRepository = new ProjectRepository(application);

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

    public void getTasks() {
        mTaskRepository.getTasks();
    }

    public void getProjects() {
        mProjectRepository.getProjects();
    }


    public LiveData<List<Project>> getAllProjectsLiveData() {
        return updateProjects;
    }

    public LiveData<List<Task>> getAllTasksLiveData() {
        return updateTasks;
    }

    public void insert(Task task) {
        mTaskRepository.insert(task);
    }

    public void delete(Task task) {
        mTaskRepository.remove(task);
    }
}
