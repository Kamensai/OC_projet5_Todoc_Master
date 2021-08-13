package com.cleanup.todoc.service;

import com.cleanup.todoc.model.Task;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface TaskDao {
    // allowing the insert of the same task multiple times by passing a
    // conflict resolution strategy
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Task task);

    @Delete
    void delete(Task task);

    @Query("SELECT * FROM task_table ORDER BY name ASC")
    LiveData<List<Task>> getAlphabetizedTasks();

    /**
     * For test
     * @return Tasks
     */
    @Query("SELECT * FROM task_table")
    List<Task> getAllTasks();
}
