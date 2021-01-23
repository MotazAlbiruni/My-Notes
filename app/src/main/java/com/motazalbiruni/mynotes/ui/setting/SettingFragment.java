package com.motazalbiruni.mynotes.ui.setting;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.motazalbiruni.mynotes.R;
import com.motazalbiruni.mynotes.ui.about.AboutViewModel;

import java.util.List;

public class SettingFragment extends Fragment {

    private SettingViewModel mViewModel;
    private Spinner languageSpinner;
    private TextView textView;
    private Context context;

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        context = getContext();
        return inflater.inflate(R.layout.setting_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SettingViewModel.class);
        mViewModel.setContextSetting(context);
        textView = view.findViewById(R.id.txt_language);
        languageSpinner = view.findViewById(R.id.spinner_language);
        mViewModel.getLanguageLiveData().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                ArrayAdapter<String> languageAdaptor = new ArrayAdapter<String>(context,
                        android.R.layout.simple_spinner_item,strings);
                languageSpinner.setAdapter(languageAdaptor);
            }
        });
    }
}