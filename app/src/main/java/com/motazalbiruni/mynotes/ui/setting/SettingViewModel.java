package com.motazalbiruni.mynotes.ui.setting;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.motazalbiruni.mynotes.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SettingViewModel extends ViewModel {
    @SuppressLint("StaticFieldLeak")
    private Context context;
    private MutableLiveData<List<String>> languageLiveData;
    private MutableLiveData<List<String>> displayNotesLiveData;
    private MutableLiveData<List<String>> textSizeLiveData;

    //show language
    public MutableLiveData<List<String>> getLanguageLiveData() {
        if (languageLiveData == null) {
            languageLiveData = new MutableLiveData<>();
        }
        loadLanguageLiveData();
        return languageLiveData;
    }

    private void loadLanguageLiveData() {
        List<String> list = Arrays.asList(context.getResources().getStringArray(R.array.language));
        languageLiveData.setValue(list);
    }

    //show type of display of notes
    public MutableLiveData<List<String>> getDisplayNotesLiveData(){
        if(displayNotesLiveData == null){
            displayNotesLiveData = new MutableLiveData<>();
        }
        loadDisplayNotesLiveData();
        return displayNotesLiveData;
    }

    private void loadDisplayNotesLiveData() {
        List<String> list = Arrays.asList(context.getResources().getStringArray(R.array.displayNotes));
        displayNotesLiveData.setValue(list);
    }

    //show list of text size
    public MutableLiveData<List<String>> getTextSizeLiveData(){
        if(textSizeLiveData == null){
            textSizeLiveData = new MutableLiveData<>();
        }
        loadTextSizeLiveData();
        return textSizeLiveData;
    }

    private void loadTextSizeLiveData() {
        List<String> list = Arrays.asList(context.getResources().getStringArray(R.array.textSize));
        textSizeLiveData.setValue(list);
    }

    //set context
    public void setContextSetting(Context context) {
        this.context = context;
    }
}//end Class