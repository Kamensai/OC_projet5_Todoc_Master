package com.cleanup.todoc.ui;

import android.content.res.ColorStateList;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.cleanup.todoc.R;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * <p>Adapter which handles the list of tasks to display in the dedicated RecyclerView.</p>
 *
 * @author Gaëtan HERFRAY
 */
public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskViewHolder>{
    /**
     * The list of tasks the adapter deals with
     */
    @NonNull
    private List<Task> mTasks = new ArrayList<>();

    @NonNull
    private List<Project> projects = new ArrayList<>();

    /**
     * The listener for when a task needs to be deleted
     */
    @NonNull
    private final DeleteTaskListener deleteTaskListener;

    /**
     * Instantiates a new TasksAdapter.
     *
     */
    TasksAdapter( @NonNull final DeleteTaskListener deleteTaskListener) {
        this.deleteTaskListener = deleteTaskListener;
    }

    TasksAdapter(@NotNull List<Task> tasks, @NonNull final DeleteTaskListener deleteTaskListener) {
        mTasks = tasks;
        this.deleteTaskListener = deleteTaskListener;
    }

    void updateProjects(@NonNull final List<Project> projects) {
        this.projects = projects;
        notifyDataSetChanged();
    }

    /**
     * Updates the list of tasks the adapter deals with.
     *
     * @param tasks the list of tasks the adapter deals with to set
     */
    void updateAndSortTasks(@NonNull final List<Task> tasks, String sortMethod) {
        this.mTasks = tasks;
        sortTasks(sortMethod);
        notifyDataSetChanged();
    }

    void sortTasks(String sortMethod){
        if(!mTasks.isEmpty()) {
            switch (sortMethod) {
                case "ALPHABETICAL":
                    Collections.sort(mTasks, new Task.TaskAZComparator());
                    break;
                case "ALPHABETICAL_INVERTED":
                    Collections.sort(mTasks, new Task.TaskZAComparator());
                    break;
                case "RECENT_FIRST":
                    Collections.sort(mTasks, new Task.TaskRecentComparator());
                    break;
                case "OLD_FIRST":
                    Collections.sort(mTasks, new Task.TaskOldComparator());
                    break;
                case "PROJECT_NAME":
                    Collections.sort(mTasks, Task.taskProjectNameComparator);
                    break;
                    //mTasks = getTasksByProject(mSortMethod);
                    //mTasks.stream().filter(task -> Objects.requireNonNull(task.getProject(projects)).getName().equals(mSortMethod)).forEach((task -> mTasks.add(task)));
                                //removeIf(task -> !Objects.requireNonNull(task.getProject(projects)).getName().equals(mSortMethod));
                //case "LUCIDIA":
                    //Collections.sort(mTasks, Task.taskProjectNameComparator);

                    //mSortMethod = LUCIDIA;
                    //mTasks = getTasksByProject(mSortMethod);
                       // mTasks.stream().filter(task -> Objects.requireNonNull(task.getProject(projects)).getName().equals(mSortMethod)).forEach((task -> mTasks.add(task)));
                //case "TARTAMPION":
                    //Collections.sort(mTasks, Task.taskProjectNameComparator);

                    //mSortMethod = TARTAMPION;
                    //mTasks = getTasksByProject(mSortMethod);
                    // mTasks.stream().filter(task -> Objects.requireNonNull(task.getProject(projects)).getName().equals(mSortMethod)).forEach((task -> mTasks.add(task)));

                default:break;
            }
            notifyDataSetChanged();
        }
    }
/*
    public List<Task> getTasksByProject(String projectChosen) {
        List<Task> mListByProject = new ArrayList<>();
        for (int i = 0; i < mTasks.size(); i++) {
            Task task = mTasks.get(i);
            if (projectChosen.equalsIgnoreCase(Objects.requireNonNull(task.getProject(projects)).getName())) {
                mListByProject.add(task);
            }
        }
        return mListByProject;
    }

 */

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_task, viewGroup, false);
        return new TaskViewHolder(view, deleteTaskListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int position) {
        taskViewHolder.bind(mTasks.get(position));
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    /**
     * Listener for deleting tasks
     */
    public interface DeleteTaskListener {
        /**
         * Called when a task needs to be deleted.
         *
         * @param task the task that needs to be deleted
         */
        void onDeleteTask(Task task);
    }

    /**
     * <p>ViewHolder for task items in the tasks list</p>
     *
     * @author Gaëtan HERFRAY
     */
    class TaskViewHolder extends RecyclerView.ViewHolder {
        /**
         * The circle icon showing the color of the project
         */
        private final AppCompatImageView imgProject;

        /**
         * The TextView displaying the name of the task
         */
        private final TextView lblTaskName;

        /**
         * The TextView displaying the name of the project
         */
        private final TextView lblProjectName;

        /**
         * The delete icon
         */
        private final AppCompatImageView imgDelete;

        /**
         * The listener for when a task needs to be deleted
         */
        private final DeleteTaskListener deleteTaskListener;

        /**
         * Instantiates a new TaskViewHolder.
         *
         * @param itemView the view of the task item
         * @param deleteTaskListener the listener for when a task needs to be deleted to set
         */
        TaskViewHolder(@NonNull View itemView, @NonNull DeleteTaskListener deleteTaskListener) {
            super(itemView);

            this.deleteTaskListener = deleteTaskListener;

            imgProject = itemView.findViewById(R.id.img_project);
            lblTaskName = itemView.findViewById(R.id.lbl_task_name);
            lblProjectName = itemView.findViewById(R.id.lbl_project_name);
            imgDelete = itemView.findViewById(R.id.img_delete);

            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Object tag = view.getTag();
                    if (tag instanceof Task) {
                        TaskViewHolder.this.deleteTaskListener.onDeleteTask((Task) tag);
                    }
                }
            });
        }

        /**
         * Binds a task to the item view.
         *
         * @param task the task to bind in the item view
         */
        void bind(Task task) {
            lblTaskName.setText(task.getName());
            imgDelete.setTag(task);

            final Project taskProject = task.getProject(projects);
            if (taskProject != null) {
                imgProject.setSupportImageTintList(ColorStateList.valueOf(taskProject.getColor()));
                lblProjectName.setText(task.getProjectName());
            } else {
                imgProject.setVisibility(View.INVISIBLE);
                lblProjectName.setText("");
            }

        }
    }
}
