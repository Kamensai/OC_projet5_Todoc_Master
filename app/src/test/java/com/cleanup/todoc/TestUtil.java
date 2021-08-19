package com.cleanup.todoc;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class TestUtil {
    public static final List<Project> LIST_PROJECTS = Arrays.asList(
            new Project( App.getRes().getString(R.string.project_name_Tartampion), 0xFFEADAD1),
            new Project( App.getRes().getString(R.string.project_name_Lucidia), 0xFFB4CDBA),
            new Project( App.getRes().getString(R.string.project_name_Circus), 0xFFA3CED2));

    public static List<Project> getListProjects() {
        return new ArrayList<>(LIST_PROJECTS);
    }

    public static final List<Task> LIST_TASKS = Collections.singletonList(
            new Task(2L, App.getRes().getString(R.string.project_name_Lucidia), App.getRes().getString(R.string.task_trash), new Date().getTime()));

    public static List<Task> getTasks(){
        return new ArrayList<>(LIST_TASKS);
    }


}
