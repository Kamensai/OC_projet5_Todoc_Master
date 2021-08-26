package com.cleanup.todoc.database.dao;

import com.cleanup.todoc.model.Project;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface ProjectDao {

        // allowing the insert of the same task multiple times by passing a
        // conflict resolution strategy

        @Insert(onConflict = OnConflictStrategy.IGNORE)
        void insert(Project project);

        @Query("SELECT * FROM project_table ORDER BY name ASC")
        LiveData<List<Project>> getAlphabetizedProjects();

        /**
        * For test
         * @return Projects
        */
        @Query("SELECT * FROM project_table")
        List<Project> getAllProjects();
    }
