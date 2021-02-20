package com.motazalbiruni.mynotes;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.util.Locale;

public class MyValues {
    public final static String DATABASE_NAME = "notes_database";//for database file name
    public final static String SHARED_FILE_NAME = "motazalbiruni.mynotes";//for SharedPreferences file name

    public final static String ID_KEY = "ID_KEY";//for frame to add or setting or about;
    public final static int ADD = -1;
    public final static int SETTING = 0;
    public final static int ABOUT = 1;

    public final static int SPLASH_DISPLAY_TIME = 1000;//for splash time delay

    public final static String DISPLAY_RECYCLER = "ID_DISPLAY";//for display recycler view
    public final static String DISPLAY_GRID = "GRID";//for display grid
    public final static String DISPLAY_LIST = "LIST";//for display list

    public final static String TEXT_SIZE = "TEXT_SIZE";//for control text size
    public final static String SMALL_TEXT = "SMALL_TEXT";//for small text size
    public final static String MEDIUM_TEXT = "MEDIUM_TEXT";//for medium text size
    public final static String LARGE_TEXT = "LARGE_TEXT";//for large text size

    public final static String LANGUAGE = "LANGUAGE";//for language
    public final static String ARABIC = "ar";//for arabic
    public final static String ENGLISH = "en";//for english
    public final static String GERMAN = "de";//for german

}//end class
