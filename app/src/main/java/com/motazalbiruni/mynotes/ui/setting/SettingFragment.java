package com.motazalbiruni.mynotes.ui.setting;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.motazalbiruni.mynotes.MyValues;
import com.motazalbiruni.mynotes.R;

import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class SettingFragment extends Fragment {

    private Spinner languageSpinner, displaySpinner, textSizeSpinner;
    private Context context;

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        context = getContext();
        View view = inflater.inflate(R.layout.setting_fragment, container, false);
        languageSpinner = view.findViewById(R.id.spinner_language);
        displaySpinner = view.findViewById(R.id.spinner_displayNotes);
        textSizeSpinner = view.findViewById(R.id.spinner_textSize);
        return view;
    }//end onCreateView()

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SettingViewModel mViewModel = new ViewModelProvider(this).get(SettingViewModel.class);
        mViewModel.setContextSetting(context);
        final SharedPreferences pref = getActivity().getSharedPreferences(MyValues.SHARED_FILE_NAME, MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();

        //setting display notes in main ui
        mViewModel.getDisplayNotesLiveData().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                ArrayAdapter<String> displayAdaptor = new ArrayAdapter<>(context, R.layout.spinner_item_setting
                        , R.id.txt_spinner_item, strings);
                displaySpinner.setAdapter(displayAdaptor);
                switch (pref.getString(MyValues.DISPLAY_RECYCLER, MyValues.DISPLAY_GRID)) {
                    case MyValues.DISPLAY_GRID:
                        displaySpinner.setSelection(0);
                        break;
                    case MyValues.DISPLAY_LIST:
                        displaySpinner.setSelection(1);
                        break;
                }
            }
        });

        displaySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        editor.putString(MyValues.DISPLAY_RECYCLER, MyValues.DISPLAY_GRID);
                        editor.apply();
                        break;
                    case 1:
                        editor.putString(MyValues.DISPLAY_RECYCLER, MyValues.DISPLAY_LIST);
                        editor.apply();
                        break;
                }//end switch
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //setting text size
        mViewModel.getTextSizeLiveData().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                ArrayAdapter<String> textAdaptor = new ArrayAdapter<>(context, R.layout.spinner_item_setting
                        , R.id.txt_spinner_item, strings);
                textSizeSpinner.setAdapter(textAdaptor);
                switch (pref.getString(MyValues.TEXT_SIZE, MyValues.SMALL_TEXT)) {
                    case MyValues.SMALL_TEXT:
                        textSizeSpinner.setSelection(0);
                        break;
                    case MyValues.MEDIUM_TEXT:
                        textSizeSpinner.setSelection(1);
                        break;
                    case MyValues.LARGE_TEXT:
                        textSizeSpinner.setSelection(2);
                        break;
                }
            }
        });

        textSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        editor.putString(MyValues.TEXT_SIZE,  MyValues.SMALL_TEXT);
                        editor.apply();
                        break;
                    case 1:
                        editor.putString(MyValues.TEXT_SIZE, MyValues.MEDIUM_TEXT);
                        editor.apply();
                        break;
                    case 2:
                        editor.putString(MyValues.TEXT_SIZE, MyValues.LARGE_TEXT);
                        editor.apply();
                        break;
                }//end switch
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }//end onViewCreated()

}//end class