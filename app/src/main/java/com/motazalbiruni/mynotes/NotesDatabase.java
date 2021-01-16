package com.motazalbiruni.mynotes;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = NoteEntity.class,version = 1,exportSchema = false)
public abstract class NotesDatabase extends RoomDatabase {

    private static NotesDatabase inStance;
    private static final String DATABASE_NAME = "notes_database.db";

    public static synchronized NotesDatabase getDatabase(Context context) {
        if (inStance == null) {
            inStance = Room.databaseBuilder(context.getApplicationContext(),
                    NotesDatabase.class, DATABASE_NAME).createFromAsset("databases/notes_database.db")
                    .addCallback(mCallback).build();
        }
        return inStance;
    }

    public abstract NoteDAO getNoteDAO();

    public static RoomDatabase.Callback mCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };
}
