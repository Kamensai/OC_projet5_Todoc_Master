package com.cleanup.todoc.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * <p>Model for the tasks of the application.</p>
 *
 * @author Gaëtan HERFRAY
 */
@Entity(tableName = "task_table")
public class Task {
    /**
     * The unique identifier of the task
     */
    @PrimaryKey (autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private long id;

    /**
     * The unique identifier of the project associated to the task
     */
    @NonNull
    @ColumnInfo(name = "projectId")
    private long projectId;

    @NonNull
    @ColumnInfo(name = "projectName")
    private String projectName;

    /**
     * The name of the task
     */
    // Suppress warning because setName is called in constructor
    @SuppressWarnings("NullableProblems")
    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    /**
     * The timestamp when the task has been created
     */
    @NonNull
    @ColumnInfo(name = "creationTimestamp")
    private long creationTimestamp;

    /**
     * Instantiates a new Task.
     *
     * @param projectId         the unique identifier of the project associated to the task to set
     * @param name              the name of the task to set
     * @param creationTimestamp the timestamp when the task has been created to set
     */
    public Task(long projectId, @NotNull String projectName, @NonNull String name, long creationTimestamp) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.name = name;
        this.creationTimestamp = creationTimestamp;
    }

    /**
     * Returns the unique identifier of the task.
     *
     * @return the unique identifier of the task
     */
    public long getId() { return id; }

    /**
     * Sets the unique identifier of the task.
     *
     * @param id the unique idenifier of the task to set
     */
    public void setId(long id) {
        this.id = id;
    }


    public long getProjectId() {
        return projectId;
    }
    /**
     * Sets the unique identifier of the project associated to the task.
     *
     * @param projectId the unique identifier of the project associated to the task to set
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * Returns the project associated to the task.
     *
     * @return the project associated to the task
     */
    @Nullable
    public Project getProject(List<Project> projects) {
        return Project.getProjectById(projectId, projects);
    }

    @NotNull
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(List<Project> projects) {
        this.projectName =  Objects.requireNonNull(Project.getProjectById(projectId, projects)).getName();
    }

    /**
     * Returns the name of the task.
     *
     * @return the name of the task
     */
    @NonNull
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the task.
     *
     * @param name the name of the task to set
     */
    public void setName(@NonNull String name) {
        this.name = name;
    }

    /**
     * Sets the timestamp when the task has been created.
     *
     * @param creationTimestamp the timestamp when the task has been created to set
     */
    public void setCreationTimestamp(long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public long getCreationTimestamp() {
        return creationTimestamp;
    }


    public final static Comparator<Task> taskProjectNameComparator = (r1, r2) -> Objects.requireNonNull(r1.getProjectName()).compareTo(Objects.requireNonNull(r2.getProjectName()));


    /**
     * Comparator to sort task from A to Z
     */
    public static class TaskAZComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return left.name.compareTo(right.name);
        }
    }

    /**
     * Comparator to sort task from Z to A
     */
    public static class TaskZAComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return right.name.compareTo(left.name);
        }
    }

    /**
     * Comparator to sort task from last created to first created
     */
    public static class TaskRecentComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return (int) (right.creationTimestamp - left.creationTimestamp);
        }
    }

    /**
     * Comparator to sort task from first created to last created
     */
    public static class TaskOldComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return (int) (left.creationTimestamp - right.creationTimestamp);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Task)) {
            return false;
        }

        Task that = (Task) other;

        // Custom equality check here.
        return  this.projectName.equals(that.projectName)
                && this.name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int hashCode = 1;

        hashCode = hashCode * 37 + this.projectName.hashCode();
        hashCode = hashCode * 37 + this.name.hashCode();

        return hashCode;
    }
}
