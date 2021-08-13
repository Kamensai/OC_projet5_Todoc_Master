package com.cleanup.todoc;

import android.content.Context;
import android.os.Build;

import com.cleanup.todoc.model.MainActivityViewModel;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.room.RoomDatabase;
import com.cleanup.todoc.service.ProjectDao;
import com.cleanup.todoc.service.TaskDao;
import com.cleanup.todoc.ui.MainActivity;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for tasks
 *
 * @author Gaëtan HERFRAY
 */

@RunWith(RobolectricTestRunner.class)
public class TaskUnitTest {
    private TaskDao mTaskDao;
    private ProjectDao mProjectsDao;
    private RoomDatabase db;

    @Before
    public void createDb(){
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context, RoomDatabase.class).allowMainThreadQueries().build();
        mTaskDao = db.taskDao();
        mProjectsDao = db.projectDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void getProjectsWithSuccess() throws Exception {
        Project project = new Project( App.getRes().getString(R.string.project_name_Tartampion), 0xFFEADAD1);
        mProjectsDao.insert(project);
        Project project1 = new Project( App.getRes().getString(R.string.project_name_Lucidia), 0xFFB4CDBA);
        mProjectsDao.insert(project1);
        Project project2 = new Project( App.getRes().getString(R.string.project_name_Circus), 0xFFA3CED2);
        mProjectsDao.insert(project2);
        List<Project> mListProjectsFromTest = TestUtil.getListProjects();
        List<Project> mListProjectsFromDb = mProjectsDao.getAllProjects();

        org.hamcrest.MatcherAssert.assertThat(mListProjectsFromDb, IsIterableContainingInAnyOrder.containsInAnyOrder(Objects.requireNonNull(mListProjectsFromTest.toArray())));

        //Assert.assertEquals(mListProjectsFromTest.get(0).getName(),mListProjectsFromDb.get(0).getName());
        //Assert.assertEquals(mListProjectsFromTest.get(1).getName(),mListProjectsFromDb.get(1).getName());
        //Assert.assertEquals(mListProjectsFromTest.get(2).getName(),mListProjectsFromDb.get(2).getName());
    }

    @Test
    public void test_projects() {

        final Task task1 = new Task(1, "Projet Tartampion", "task 1", new Date().getTime());
        final Task task2 = new Task(2, "Projet Lucidia", "task 2", new Date().getTime());
        final Task task3 = new Task(3, "Projet Circus", "task 3", new Date().getTime());
        final Task task4 = new Task(4, "", "task 4", new Date().getTime());

        assertEquals("Projet Tartampion", task1.getProjectName());
        assertEquals("Projet Lucidia", task2.getProjectName());
        assertEquals("Projet Circus", task3.getProjectName());
        assertEquals( "", task4.getProjectName());
    }

    @Test
    public void test_az_comparator() {
        final Task task1 = new Task(1, "", "aaa", 123);
        final Task task2 = new Task(2, "", "zzz", 124);
        final Task task3 = new Task(3, "", "hhh", 125);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new Task.TaskAZComparator());

        assertSame(tasks.get(0), task1);
        assertSame(tasks.get(1), task3);
        assertSame(tasks.get(2), task2);
    }

    @Test
    public void test_za_comparator() {
        final Task task1 = new Task(1, "", "aaa", 123);
        final Task task2 = new Task(2, "", "zzz", 124);
        final Task task3 = new Task(3, "", "hhh", 125);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new Task.TaskZAComparator());

        assertSame(tasks.get(0), task2);
        assertSame(tasks.get(1), task3);
        assertSame(tasks.get(2), task1);
    }

    @Test
    public void test_recent_comparator() {
        final Task task1 = new Task(1, "", "aaa", 123);
        final Task task2 = new Task(2, "", "zzz", 124);
        final Task task3 = new Task(3, "", "hhh", 125);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new Task.TaskRecentComparator());

        assertSame(tasks.get(0), task3);
        assertSame(tasks.get(1), task2);
        assertSame(tasks.get(2), task1);
    }

    @Test
    public void test_old_comparator() {
        final Task task1 = new Task(1, "", "aaa", 123);
        final Task task2 = new Task(2, "", "zzz", 124);
        final Task task3 = new Task(3, "", "hhh", 125);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new Task.TaskOldComparator());

        assertSame(tasks.get(0), task1);
        assertSame(tasks.get(1), task2);
        assertSame(tasks.get(2), task3);
    }

    @Test
    public void addTaskAndDeleteWithSuccess() {
        Task taskToAdd = TestUtil.getTasks().get(0);
            mTaskDao.insert(taskToAdd);

        Task taskAdded = mTaskDao.getAllTasks().get(0);

        //Assert.assertThat(mListTasksFromDb.get(0), equalTo(taskToAdd));
        Assert.assertTrue(mTaskDao.getAllTasks().contains(taskAdded));

        //Assert.assertEquals(mTaskDao.getAllTasks().get(0).getName(), taskToAdd.getName());

        Task taskToDelete = mTaskDao.getAllTasks().get(0);
            mTaskDao.delete(taskToDelete);

        Assert.assertFalse(mTaskDao.getAllTasks().contains(taskToDelete));
    }
}