package com.motazalbiruni.mynotes.ui.about;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.motazalbiruni.mynotes.BuildConfig;
import com.motazalbiruni.mynotes.R;

public class AboutFragment extends Fragment {

    private AboutViewModel mViewModel;

    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.about_fragment, container, false);
        TextView txt = view.findViewById(R.id.txt_about_version);
        String versionName = BuildConfig.VERSION_NAME;
        txt.setText("Version : "+versionName);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AboutViewModel.class);

        // TODO: Use the ViewModel
    }

}