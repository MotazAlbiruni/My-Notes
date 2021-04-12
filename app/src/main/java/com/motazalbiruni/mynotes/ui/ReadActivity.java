package com.motazalbiruni.mynotes.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.motazalbiruni.mynotes.MyValues;
import com.motazalbiruni.mynotes.database_room.NoteEntity;
import com.motazalbiruni.mynotes.R;
import com.motazalbiruni.mynotes.ui.main.MainViewModel;

import java.util.Date;
import java.util.Objects;

public class ReadActivity extends AppCompatActivity {

    private MainViewModel viewModel;
    private EditText txt_title, txt_body;
    private Spinner spinner;
    private LinearLayout layout_style;

    private SharedPreferences pref;
    private static boolean checkStyle;
    private static Integer id_note;
    private static int type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_read);
        //ADMob
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView mAdView = findViewById(R.id.adView_mainActivity);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //file shared preferences
        pref = getSharedPreferences(MyValues.SHARED_FILE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor spEditor = pref.edit();
        spEditor.putInt("save",-1);
        spEditor.apply();

        layout_style = findViewById(R.id.layout_style);
        txt_title = findViewById(R.id.txtTitle_Read); //text for title note
        txt_body = findViewById(R.id.txtNote_Read); //text for body note
        //setting text size in reading
        String textSize = pref.getString(MyValues.TEXT_SIZE, MyValues.MEDIUM_TEXT);
        switch (textSize) {
            case MyValues.SMALL_TEXT:
                txt_title.setTextSize(16);
                txt_body.setTextSize(16);
                break;
            case MyValues.MEDIUM_TEXT:
                txt_title.setTextSize(20);
                txt_body.setTextSize(20);
                break;
            case MyValues.LARGE_TEXT:
                txt_title.setTextSize(24);
                txt_body.setTextSize(24);
                break;
        }//end switch

        Bundle extrasBundle = getIntent().getExtras(); //to get id for note click
        if (extrasBundle != null) {
            id_note = extrasBundle.getInt(MyValues.ID_KEY);
        }//end if(extrasBundle)

        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getApplication())).get(MainViewModel.class);
        //reset value of style
        checkStyle = false;
        //check condition
        if (id_note != MyValues.ADD) {
            //if id >= 0 get note form database
            viewModel.getNoteById(id_note).observe(this, new Observer<NoteEntity>() {
                @Override
                public void onChanged(NoteEntity noteEntity) {
                    //check condition
                    //when noteEntity isn't null invoked this
                    if (noteEntity != null) {
                        txt_title.setText(noteEntity.getTitle());
                        txt_body.setText(noteEntity.getBody());
                        type = noteEntity.getType();
                        changeBackground(noteEntity.getType());
                    }//end if
                }
            });
        } else {
            //id =-1 create new note
            type = 0;
            txt_title.setText("");
            txt_body.setText("");
        }//end if else

        txt_body.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                hideColorStyle();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                hideColorStyle();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }//end onCreate()

    //create option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }//end onCreateOptionsMenu()

    //handle item select
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.style_item:
                if (!checkStyle) {
                    appearColorStyle();
                } else {
                    hideColorStyle();
                }//end if else
                return true;
            case R.id.save_item:
                saveData();
                SharedPreferences.Editor spEditor = pref.edit();
                spEditor.putInt("save",1);
                spEditor.apply();
                return true;
            case R.id.delete_item:
                deleteFromData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }//end onOptionsItemSelected()

    private void saveData() {
        Date date = new Date();
        final String timeNow = (String) DateFormat.format("d-MMMM-yyyy", date.getTime());
        final String txt_title_editing = txt_title.getText().toString();
        final String txt_body_editing = txt_body.getText().toString();
        if (id_note != MyValues.ADD) {
            viewModel.getNoteById(id_note).observe(this, new Observer<NoteEntity>() {
                @Override
                public void onChanged(NoteEntity noteEntity) {
                    try {
                        if (!noteEntity.getBody().equals(txt_body_editing)
                                || !noteEntity.getTitle().equals(txt_title_editing)
                                || type != noteEntity.getType()) {
                            viewModel.update(new NoteEntity(id_note, txt_title_editing, txt_body_editing, type, timeNow));
                        }
                    } catch (Exception e) {
                        Log.d("onChange", e.getMessage());
                    }
                }//end onChanged()
            });
        } else {
            if (!txt_title_editing.equals(""))
                viewModel.insert(new NoteEntity(txt_title_editing, txt_body_editing, type, timeNow));
        }
        finish();
    }//end saveData()

    private void deleteFromData() {
        if (id_note != MyValues.ADD) {
            viewModel.deleteById(id_note);
            finish();
        }
    }//end deleteFromData()

    @Override
    protected void onPause() {
        super.onPause();
        int save = pref.getInt("save", -1);
        if (save == -1) {
            saveData();
        }
    }//end onPause()

    private void changeBackground(int type) {
        switch (type) {
            case 0:
                txt_body.setBackground(getResources().getDrawable(R.color.lightColor_1));
                txt_title.setBackground(getResources().getDrawable(R.color.lightColor_1));
                break;
            case 1:
                txt_body.setBackground(getResources().getDrawable(R.color.lightColor_2));
                txt_title.setBackground(getResources().getDrawable(R.color.lightColor_2));
                break;
            case 2:
                txt_body.setBackground(getResources().getDrawable(R.color.lightColor_3));
                txt_title.setBackground(getResources().getDrawable(R.color.lightColor_3));
                break;
            case 3:
                txt_body.setBackground(getResources().getDrawable(R.color.lightColor_4));
                txt_title.setBackground(getResources().getDrawable(R.color.lightColor_4));
                break;
            case 4:
                txt_body.setBackground(getResources().getDrawable(R.color.lightColor_5));
                txt_title.setBackground(getResources().getDrawable(R.color.lightColor_5));
                break;
            case 5:
                txt_body.setBackground(getResources().getDrawable(R.color.lightColor_6));
                txt_title.setBackground(getResources().getDrawable(R.color.lightColor_6));
                break;
        }//end switch
    }//end changeBackground

    @SuppressLint("NonConstantResourceId")
    public void colorStyle(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.color_item_1:
                type = 0;
                hideColorStyle();
                changeBackground(0);
                break;
            case R.id.color_item_2:
                type = 1;
                hideColorStyle();
                changeBackground(1);
                break;
            case R.id.color_item_3:
                type = 2;
                hideColorStyle();
                changeBackground(2);
                break;
            case R.id.color_item_4:
                type = 3;
                hideColorStyle();
                changeBackground(3);
                break;
            case R.id.color_item_5:
                type = 4;
                hideColorStyle();
                changeBackground(4);
                break;
            case R.id.color_item_6:
                type = 5;
                hideColorStyle();
                changeBackground(5);
                break;
        }//end switch(id)
    }//end colorStyle(view)

    private void hideColorStyle() {
        layout_style.setVisibility(View.GONE);
        txt_title.setVisibility(View.VISIBLE);
        checkStyle = false;
    }//end hideColorStyle()

    private void appearColorStyle() {
        layout_style.setVisibility(View.VISIBLE);
        txt_title.setVisibility(View.INVISIBLE);
        checkStyle = true;
    }//end appearColorStyle()

}//end Class ReadActivity