package com.cleanup.todoc;

import android.app.Application;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.room.RoomDatabase;
import com.cleanup.todoc.service.ProjectDao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

public class ProjectRepository {
    private ProjectDao mProjectDao;

    private MutableLiveData<List<Project>> mAllProjects = new MutableLiveData<>();


    public ProjectRepository(Application application) {
        RoomDatabase db = RoomDatabase.getDatabase(application);
        mProjectDao = db.projectDao();
        // mAllTasks = mTaskDao.getAlphabetizedWords();
        // mAllProjects = mTaskDao.getAlphabetizedProjects();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.

    public LiveData<List<Project>> getAllProjectsLiveData() {
        return mAllProjects;
    }

    public void getProjects(){
        mProjectDao.getAlphabetizedProjects().observeForever(new Observer<List<Project>>() {
            @Override
            public void onChanged(List<Project> projects) {
                mAllProjects.setValue(projects);
            }
        });
    }
}
