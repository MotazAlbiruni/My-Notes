package com.motazalbiruni.mynotes.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.motazalbiruni.mynotes.MyValues;
import com.motazalbiruni.mynotes.R;
import com.motazalbiruni.mynotes.ui.about.AboutFragment;
import com.motazalbiruni.mynotes.ui.setting.SettingFragment;

import java.util.Objects;

public class MoreSetting extends AppCompatActivity {

    private static int keyMore = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.more_setting_activity);

        Bundle extrasBundle = getIntent().getExtras(); //to get id for note click
        if (extrasBundle != null) {
            keyMore = extrasBundle.getInt(MyValues.ID_KEY);
        }//end if(extrasBundle)

        if (savedInstanceState == null) {
            switch (keyMore){
                case MyValues.SETTING:
                    Objects.requireNonNull(getSupportActionBar()).setTitle( R.string.setting);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, SettingFragment.newInstance())
                            .commitNow();
                    break;
                case MyValues.ABOUT:
                    Objects.requireNonNull(getSupportActionBar()).setTitle( R.string.about);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, AboutFragment.newInstance())
                            .commitNow();
                    break;
            }//end switch
        }//end if
    }//end onCreate()
}//end class