package com.cleanup.todoc;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.StringDef;

/**
 * Created by <Victor Khamvongsa> on <26/08/2021>
 */
public class SortMethodDef {
    @Retention(RetentionPolicy.SOURCE)
    // Enumerate valid values for this interface
    @StringDef({ALPHABETICAL, ALPHABETICAL_INVERTED, OLD_FIRST, RECENT_FIRST, PROJECT_NAME, SORT_METHOD})
    // Create an interface for validating String types
    public @interface SortMethodStringDef {
    }

    // Declare the constants
    public static final String ALPHABETICAL = "ALPHABETICAL";
    public static final String ALPHABETICAL_INVERTED = "ALPHABETICAL_INVERTED";
    public static final String OLD_FIRST = "OLD_FIRST";
    public static final String RECENT_FIRST = "RECENT_FIRST";
    public static final String PROJECT_NAME = "PROJECT_NAME";
    public static final String SORT_METHOD = "SORT_METHOD";
}
