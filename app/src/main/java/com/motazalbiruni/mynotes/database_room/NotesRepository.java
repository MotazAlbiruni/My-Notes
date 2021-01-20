package com.motazalbiruni.mynotes.database_room;

import android.app.Application;
import android.os.AsyncTask;

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
//        new InsertAsyncTask(mNoteDAO).execute(noteEntity);
    }
    //update
    public void update(final NoteEntity noteEntity){
        MyExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mNoteDAO.update(noteEntity);
            }
        });
//        new UpdateAsyncTask(mNoteDAO).execute(noteEntity);
    }

    //delete
    public void delete(final NoteEntity noteEntity){
        MyExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mNoteDAO.delete(noteEntity);
            }
        });
//        new DeleteAsyncTask(mNoteDAO).execute(noteEntity);
    }

    //deleteAll
    public void deleteAll(){
        MyExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mNoteDAO.deleteAll();
            }
        });
//        new DeleteAllAsyncTask(mNoteDAO).execute();
    }

    //deleteById
    public void deleteById(final int id){
        MyExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mNoteDAO.deleteById(id);
            }
        });
//        new DeleteByIDAsyncTask(mNoteDAO).execute(id);
    }

//
//    private class InsertAsyncTask extends AsyncTask<NoteEntity ,Void,Void> {
//        private NoteDAO dao;
//
//        protected InsertAsyncTask(NoteDAO dao) {
//            this.dao = dao;
//        }
//
//        @Override
//        protected Void doInBackground(NoteEntity... noteEntities) {
//            dao.insert(noteEntities[0]);
//            return null;
//        }
//    }
//
//    private class UpdateAsyncTask extends AsyncTask<NoteEntity ,Void,Void> {
//        private NoteDAO dao;
//
//        public UpdateAsyncTask(NoteDAO dao) {
//            this.dao = dao;
//        }
//
//        @Override
//        protected Void doInBackground(NoteEntity... noteEntities) {
//            dao.update(noteEntities[0]);
//            return null;
//        }
//    }
//
//    private class DeleteAsyncTask extends AsyncTask<NoteEntity ,Void,Void> {
//        private NoteDAO dao;
//
//        public DeleteAsyncTask(NoteDAO dao) {
//            this.dao = dao;
//        }
//
//        @Override
//        protected Void doInBackground(NoteEntity... noteEntities) {
//            dao.delete(noteEntities[0]);
//            return null;
//        }
//    }
//
//    private class DeleteAllAsyncTask extends AsyncTask<Void ,Void,Void> {
//        private NoteDAO dao;
//
//        public DeleteAllAsyncTask(NoteDAO dao) {
//            this.dao = dao;
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            dao.deleteAll();
//            return null;
//        }
//    }
//
//    private class DeleteByIDAsyncTask extends AsyncTask<Integer ,Void,Void> {
//        private NoteDAO dao;
//
//        public DeleteByIDAsyncTask(NoteDAO dao) {
//            this.dao = dao;
//        }
//
//        @Override
//        protected Void doInBackground(Integer... integers) {
//            dao.deleteById(integers[0]);
//            return null;
//        }
//    }

}
