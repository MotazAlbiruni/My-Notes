package com.motazalbiruni.mynotes.ui.setting;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.motazalbiruni.mynotes.R;

import java.util.Arrays;
import java.util.List;

public class SettingViewModel extends ViewModel {
    @SuppressLint("StaticFieldLeak")
    private Context context;
    private MutableLiveData<List<String>> languageLiveData;

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

    public void setContextSetting(Context context) {
        this.context = context;
    }
}//end Class