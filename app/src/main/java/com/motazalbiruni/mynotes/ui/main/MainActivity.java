package com.motazalbiruni.mynotes.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.motazalbiruni.mynotes.database_room.NoteEntity;
import com.motazalbiruni.mynotes.R;
import com.motazalbiruni.mynotes.ui.MoreSetting;
import com.motazalbiruni.mynotes.ui.ReadActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ADMob
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView mAdView = findViewById(R.id.adView_mainActivity);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        recyclerView = findViewById(R.id.recyclerView);
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);

        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getApplication())).get(MainViewModel.class);

        viewRecyclerView();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToaActivity(-1, getApplicationContext(), ReadActivity.class);
            }
        });
    }//end onCreate()

    @Override
    protected void onResume() {
        super.onResume();
        viewRecyclerView();
    }//end onResume()

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }//end onCreateOptionsMenu()

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.add_item:
//                Toast.makeText(this, getResources().getString(R.string.add), Toast.LENGTH_SHORT).show();
                moveToaActivity(-1, this, ReadActivity.class);
                return true;
            case R.id.setting:
//                Toast.makeText(this, getResources().getString(R.string.setting), Toast.LENGTH_SHORT).show();
                moveToaActivity(0, this, MoreSetting.class);
                return true;
            case R.id.about:
//                Toast.makeText(this, getResources().getString(R.string.about), Toast.LENGTH_SHORT).show();
                moveToaActivity(1, this, MoreSetting.class);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }//end switch
    }//end onOptionsItemSelected()

    public void moveToaActivity(int value, Context context, Class Class) {
        Intent intentAbout = new Intent(context, Class);
        intentAbout.putExtra("id_key", value);
        startActivity(intentAbout);
    }//end moveToaActivity()

    private void viewRecyclerView() {
        SharedPreferences pref = getSharedPreferences("motazalbiruni.mynotes", MODE_PRIVATE);
        //get show notes from file setting
        String showNotes = pref.getString("display", "grid");

        switch (showNotes) {
            case "grid":
                StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,
                        LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(staggeredGridLayoutManager);
                break;
            case "list":
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                recyclerView.setLayoutManager(linearLayoutManager);
                break;
        }
        final AdapterNotes adapterNotes = new AdapterNotes(this);

        List<NoteEntity> entityList = new ArrayList<>();
//        entityList.add(new NoteEntity("note mizo", "the best app for note !", 1, "25-3"));
//        adapterNotes.setList(entityList);

        viewModel.getAllNotes().observe(this, new Observer<List<NoteEntity>>() {
            @Override
            public void onChanged(List<NoteEntity> noteEntities) {
                adapterNotes.setList(noteEntities);
            }
        });
        recyclerView.setAdapter(adapterNotes);
    }//end viewRecyclerView()

}//end MainActivity Class