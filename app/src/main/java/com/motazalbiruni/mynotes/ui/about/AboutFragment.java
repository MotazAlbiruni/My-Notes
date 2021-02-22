package com.motazalbiruni.mynotes.ui.about;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.motazalbiruni.mynotes.BuildConfig;
import com.motazalbiruni.mynotes.MyValues;
import com.motazalbiruni.mynotes.R;

public class AboutFragment extends Fragment {

    private ImageView image_facebook, image_youTube;

    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.about_fragment, container, false);
        TextView txt = view.findViewById(R.id.txt_about_version);
        image_facebook = view.findViewById(R.id.image_faceBook);
        image_youTube = view.findViewById(R.id.image_youTube);
        String versionName = BuildConfig.VERSION_NAME;
        txt.setText(String.format("%s : %s",this.getResources().getString(R.string.version), versionName));
        return view;
    }//end onCreateView()

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AboutViewModel mViewModel = new ViewModelProvider(this).get(AboutViewModel.class);

        image_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(MyValues.facebook_App));
                    startActivity(intent);
                }catch (Exception e){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(MyValues.facebook));
                    startActivity(intent);
                }
            }
        });//end faceBook

        image_youTube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(MyValues.youTube_App));
                    startActivity(intent);
                }catch (Exception e){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(MyValues.youTube));
                    startActivity(intent);
                }
            }
        });//end youTube
        // TODO: Use the ViewModel
    }//end onActivityCreated
}//end class