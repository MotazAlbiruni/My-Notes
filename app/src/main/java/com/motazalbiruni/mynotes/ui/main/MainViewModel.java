package com.motazalbiruni.mynotes.ui.main;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.motazalbiruni.mynotes.database_room.NoteEntity;
import com.motazalbiruni.mynotes.database_room.NotesRepository;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private final NotesRepository mNotesRepository;//for repository object
    private final LiveData<List<NoteEntity>> mLiveData; //for live list data to adaptor
    private final MutableLiveData<String> mSelectedLiveData; //for set value of number items selected

    public MainViewModel(@NonNull Application application) {
        super(application);
        mNotesRepository = NotesRepository.getRepository(application);
        mLiveData =  mNotesRepository.getAllNotes();
        mSelectedLiveData = new MutableLiveData<>();
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

    //get size of selected
    public MutableLiveData<String> getSelectedLiveData() {
        return mSelectedLiveData;
    }
    //set size of selected
    public void setSelectedLiveData(String selectedLiveData) {
        this.mSelectedLiveData.setValue(selectedLiveData);
    }

}//end MainViewModel Class
