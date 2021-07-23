package com.cleanup.todoc.room;

import android.content.Context;
import android.content.res.Resources;

import com.cleanup.todoc.App;
import com.cleanup.todoc.R;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.service.TaskDao;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Task.class, Project.class}, version = 1, exportSchema = false)
public abstract class RoomDatabase extends androidx.room.RoomDatabase {
    public abstract TaskDao taskDao();

    private static volatile RoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static RoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RoomDatabase.class, "task_database").addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static androidx.room.RoomDatabase.Callback sRoomDatabaseCallback = new androidx.room.RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more tasks, just add them.
                TaskDao dao = INSTANCE.taskDao();

                Task tasko = new Task( 2L, App.getRes().getString(R.string.task_trash), new Date().getTime());
                dao.insert(tasko);

                Project project = new Project( App.getRes().getString(R.string.project_name_Tartampion), 0xFFEADAD1);
                dao.insert(project);

                Project project1 = new Project( App.getRes().getString(R.string.project_name_Lucidia), 0xFFB4CDBA);
                dao.insert(project1);

                Project project2 = new Project( App.getRes().getString(R.string.project_name_Circus), 0xFFA3CED2);
                dao.insert(project2);

            });
        }
    };
}
