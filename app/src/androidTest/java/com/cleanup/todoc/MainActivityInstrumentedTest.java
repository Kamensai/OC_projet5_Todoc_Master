package com.cleanup.todoc;

import android.view.View;
import android.widget.TextView;

import com.cleanup.todoc.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Objects;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.cleanup.todoc.TestUtils.withRecyclerView;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Instrumented test, which will execute on an Android device.
 * @author Gaëtan HERFRAY
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTest {
    @Rule
    public final ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void addAndRemoveTask() {
        MainActivity activity = rule.getActivity();
        TextView lblNoTask = activity.findViewById(R.id.lbl_no_task);
        RecyclerView listTasks = activity.findViewById(R.id.list_tasks);

        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("Tâche example"));
        onView(withId(android.R.id.button1)).perform(click());

        // Check that lblTask is not displayed anymore
        assertThat(lblNoTask.getVisibility(), equalTo(View.GONE));
        // Check that recyclerView is displayed
        assertThat(listTasks.getVisibility(), equalTo(View.VISIBLE));
        // Check that it contains one element only
        assertThat(Objects.requireNonNull(listTasks.getAdapter()).getItemCount(), equalTo(1));

        onView(withId(R.id.img_delete)).perform(click());

        // Check that lblTask is displayed
        assertThat(lblNoTask.getVisibility(), equalTo(View.VISIBLE));
        // Check that recyclerView is not displayed anymore
        assertThat(listTasks.getVisibility(), equalTo(View.GONE));
    }

    @Test
    public void sortTasks() {

        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("aaa Tâche example"));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("zzz Tâche example"));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("hhh Tâche example"));
        onView(withId(android.R.id.button1)).perform(click());

        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(matches(withText("aaa Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_task_name))
                .check(matches(withText("zzz Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_task_name))
                .check(matches(withText("hhh Tâche example")));

        // Sort alphabetical
        onView(withId(R.id.action_filter)).perform(click());
        onView(withText(R.string.sort_alphabetical)).perform(click());
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(matches(withText("aaa Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_task_name))
                .check(matches(withText("hhh Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_task_name))
                .check(matches(withText("zzz Tâche example")));

        // Sort alphabetical inverted
        onView(withId(R.id.action_filter)).perform(click());
        onView(withText(R.string.sort_alphabetical_invert)).perform(click());
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(matches(withText("zzz Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_task_name))
                .check(matches(withText("hhh Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_task_name))
                .check(matches(withText("aaa Tâche example")));

        // Sort old first
        onView(withId(R.id.action_filter)).perform(click());
        onView(withText(R.string.sort_oldest_first)).perform(click());
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(matches(withText("aaa Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_task_name))
                .check(matches(withText("zzz Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_task_name))
                .check(matches(withText("hhh Tâche example")));

        // Sort recent first
        onView(withId(R.id.action_filter)).perform(click());
        onView(withText(R.string.sort_recent_first)).perform(click());
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(matches(withText("hhh Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_task_name))
                .check(matches(withText("zzz Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_task_name))
                .check(matches(withText("aaa Tâche example")));


        // Sort by ProjectName

        // On choisit le projet "Lucidia" dans le Spinner
        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("coucou Tâche example"));
        onView(withId(R.id.project_spinner)).perform(click());
        onView(withText(R.string.project_name_Lucidia)).inRoot(isPlatformPopup()).perform(click());
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("salut Tâche example"));
        onView(withId(R.id.project_spinner)).perform(click());
        onView(withText(R.string.project_name_Tartampion)).inRoot(isPlatformPopup()).perform(click());
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("bonjour Tâche example"));
        onView(withId(R.id.project_spinner)).perform(click());
        onView(withText(R.string.project_name_Lucidia)).inRoot(isPlatformPopup()).perform(click());
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("au revoir Tâche example"));
        onView(withId(R.id.project_spinner)).perform(click());
        onView(withText(R.string.project_name_Tartampion)).inRoot(isPlatformPopup()).perform(click());
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.action_filter)).perform(click());
        onView(withText(R.string.sort_project_name)).perform(click());

        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(matches(withText("hhh Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_task_name))
                .check(matches(withText("zzz Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_task_name))
                .check(matches(withText("aaa Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(3, R.id.lbl_task_name))
                .check(matches(withText("bonjour Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(4, R.id.lbl_task_name))
                .check(matches(withText("coucou Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(5, R.id.lbl_task_name))
                .check(matches(withText("au revoir Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(6, R.id.lbl_task_name))
                .check(matches(withText("salut Tâche example")));
    }
}
