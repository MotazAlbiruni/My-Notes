package com.motazalbiruni.mynotes.ui.main;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.motazalbiruni.mynotes.database_room.NoteEntity;
import com.motazalbiruni.mynotes.database_room.NotesRepository;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private NotesRepository mNotesRepository;
    private LiveData<List<NoteEntity>> mLiveData;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mNotesRepository = NotesRepository.getRepository(application);
        mLiveData =  mNotesRepository.getAllNotes();
    }

    //getAllNotes
    public LiveData<List<NoteEntity>> getAllNotes(){
        return mLiveData;
    }

    //getNoteById
    public LiveData<NoteEntity> getNoteById(int id){
        return mNotesRepository.getNoteById(id);
    }

    //insert
    public void insert(NoteEntity noteEntity){
        mNotesRepository.insert(noteEntity);
    }

    //update
    public void update(NoteEntity noteEntity){
        mNotesRepository.update(noteEntity);
    }

    //deleteById
    public void deleteById(int id){
        mNotesRepository.deleteById(id);
    }
}
