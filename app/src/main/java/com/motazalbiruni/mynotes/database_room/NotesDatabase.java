package com.motazalbiruni.mynotes.database_room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.motazalbiruni.mynotes.MyValues;
import com.motazalbiruni.mynotes.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = NoteEntity.class,version = 1,exportSchema = false)
public abstract class NotesDatabase extends RoomDatabase {

    private static Context mcontext;
    private static NotesDatabase inStance;
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(4);

    public static synchronized NotesDatabase getDatabase(Context context) {
        mcontext = context;
        if (inStance == null) {
            inStance = Room.databaseBuilder(context.getApplicationContext(),
                    NotesDatabase.class, MyValues.DATABASE_NAME).allowMainThreadQueries()
                    .addCallback(mCallback).build();
        }
        return inStance;
    }

    public abstract NoteDAO getNoteDAO();

    public static RoomDatabase.Callback mCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            EXECUTOR_SERVICE.execute(new Runnable() {
                @Override
                public void run() {
                    NoteEntity noteEntity = new NoteEntity(mcontext.getResources().getString(R.string.my_note),
                            mcontext.getResources().getString(R.string.body_note),
                            1,"25-march");
                    inStance.getNoteDAO().insert(noteEntity);
                }
            });
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };

}//end NotesDatabase class
