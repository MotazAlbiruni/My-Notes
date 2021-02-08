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
import android.widget.Toast;

import com.motazalbiruni.mynotes.R;

import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class SettingFragment extends Fragment {

    private SettingViewModel mViewModel;
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
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SettingViewModel.class);
        mViewModel.setContextSetting(context);
        final SharedPreferences pref = getActivity().getSharedPreferences("motazalbiruni.mynotes", MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();

        //setting language in app
        mViewModel.getLanguageLiveData().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                ArrayAdapter<String> languageAdaptor = new ArrayAdapter<>(context, R.layout.spinner_item_setting
                        , R.id.txt_spinner_item, strings);
                languageSpinner.setAdapter(languageAdaptor);
                switch (pref.getString("language", "ok")) {
                    case "arabic":
                        languageSpinner.setSelection(1);
                        break;
                    case "english":
                        languageSpinner.setSelection(2);
                        break;
                    case "german":
                        languageSpinner.setSelection(3);
                        break;
                }
            }
        });

        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        editor.putString("language", "arabic");
                        editor.apply();
//                        Toast.makeText(getActivity().getApplicationContext(), "Language is Arabic", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        editor.putString("language", "english");
                        editor.apply();
//                        Toast.makeText(getActivity().getApplicationContext(), "Language is English", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        editor.putString("language", "german");
                        editor.apply();
//                        Toast.makeText(getActivity().getApplicationContext(), "Language is german", Toast.LENGTH_SHORT).show();
                        break;
                }//end switch
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //setting display notes in main ui
        mViewModel.getDisplayNotesLiveData().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                ArrayAdapter<String> displayAdaptor = new ArrayAdapter<>(context, R.layout.spinner_item_setting
                        , R.id.txt_spinner_item, strings);
                displaySpinner.setAdapter(displayAdaptor);
                switch (pref.getString("display", "ok")) {
                    case "grid":
                        displaySpinner.setSelection(0);
                        break;
                    case "list":
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
                        editor.putString("display", "grid");
                        editor.apply();
//                        Toast.makeText(getActivity().getApplicationContext(), "Display notes is Grid", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        editor.putString("display", "list");
                        editor.apply();
//                        Toast.makeText(getActivity().getApplicationContext(), "Display notes is List", Toast.LENGTH_SHORT).show();
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
                switch (pref.getString("textSize", "ok")) {
                    case "small":
                        textSizeSpinner.setSelection(0);
                        break;
                    case "medium":
                        textSizeSpinner.setSelection(1);
                        break;
                    case "large":
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
                        editor.putString("textSize", "small");
                        editor.apply();
//                        Toast.makeText(getActivity().getApplicationContext(), "Text Size is Small", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        editor.putString("textSize", "medium");
                        editor.apply();
//                        Toast.makeText(getActivity().getApplicationContext(), "Text Size is Medium", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        editor.putString("textSize", "large");
                        editor.apply();
//                        Toast.makeText(getActivity().getApplicationContext(), "Text Size is Large", Toast.LENGTH_SHORT).show();
                        break;
                }//end switch
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }//end onViewCreated()

    //for change language
    public void changeLanguage(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        Configuration conf = res.getConfiguration();
        DisplayMetrics dm = res.getDisplayMetrics();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }//end changeLanguage()

}//end class