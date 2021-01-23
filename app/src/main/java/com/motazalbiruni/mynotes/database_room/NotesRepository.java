package com.motazalbiruni.mynotes.database_room;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;

public class NotesRepository {

    //fields
    private static NotesRepository mNotesRepository;
    private static NoteEntity mNoteEntity;
    private NoteDAO mNoteDAO;
    private LiveData<List<NoteEntity>> mListLiveData;
    private LiveData<NoteEntity> mEntityLiveData;

    public NotesRepository(Application application) {
        mNoteDAO = NotesDatabase.getDatabase(application).getNoteDAO();
        mListLiveData = mNoteDAO.getAllNotes();
    }

    //singleton
    public static synchronized NotesRepository getRepository(Application application){
        if(mNotesRepository == null){
            mNotesRepository = new NotesRepository(application);
        }
        return mNotesRepository;
    }//end getRepository

    public static NoteEntity getNoteEntity() {
        return mNoteEntity;
    }

    public static void setNoteEntity(NoteEntity mNoteEntity) {
        NotesRepository.mNoteEntity = mNoteEntity;
    }

    //operation

    //getAllNotes
    public LiveData<List<NoteEntity>> getAllNotes(){
        return mListLiveData;
    }

    //getNoteById
    public LiveData<NoteEntity> getNoteById(final int id){
        return mNoteDAO.getNoteById(id);
    }

    //insert
    public void insert(final NoteEntity noteEntity){
        MyExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mNoteDAO.insert(noteEntity);
            }
        });
    }
    //update
    public void update(final NoteEntity noteEntity){
        MyExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mNoteDAO.update(noteEntity);
            }
        });
    }

    //delete
    public void delete(final NoteEntity noteEntity){
        MyExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mNoteDAO.delete(noteEntity);
            }
        });
    }

    //deleteAll
    public void deleteAll(){
        MyExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mNoteDAO.deleteAll();
            }
        });
    }

    //deleteById
    public void deleteById(final int id){
        MyExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mNoteDAO.deleteById(id);
            }
        });
    }

}//end mNotesRepository class
