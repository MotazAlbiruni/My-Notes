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
import android.widget.Toast;

import com.motazalbiruni.mynotes.database_room.NoteEntity;
import com.motazalbiruni.mynotes.R;
import com.motazalbiruni.mynotes.ui.MoreSetting;
import com.motazalbiruni.mynotes.ui.ReadActivity;

import java.util.List;

import static com.motazalbiruni.mynotes.ui.ReadActivity.KEY_ID;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences pref = getSharedPreferences("motazalbiruni.mynotes", MODE_PRIVATE);
        //get show notes from file setting
        String showNotes = pref.getString("display", "welcome");

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
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
        recyclerView.setAdapter(adapterNotes);
        MainViewModel viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getApplication())).get(MainViewModel.class);

        viewModel.getAllNotes().observe(this, new Observer<List<NoteEntity>>() {
            @Override
            public void onChanged(List<NoteEntity> noteEntities) {
                adapterNotes.setList(noteEntities);
            }
        });

    }//end onCreate()

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
                Toast.makeText(this, getResources().getString(R.string.add), Toast.LENGTH_SHORT).show();
                moveToaActivity(-1, this, ReadActivity.class);
                return true;
            case R.id.setting:
                Toast.makeText(this, getResources().getString(R.string.setting), Toast.LENGTH_SHORT).show();
                moveToaActivity(0, this, MoreSetting.class);
                return true;
            case R.id.about:
                Toast.makeText(this, getResources().getString(R.string.about), Toast.LENGTH_SHORT).show();
                moveToaActivity(1, this, MoreSetting.class);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }//end switch
    }//end onOptionsItemSelected()

    public void moveToaActivity(int value, Context context, Class aClass) {
        Intent intentAbout = new Intent(context, aClass);
        intentAbout.putExtra("id_key", value);
        startActivity(intentAbout);
    }
}//end MainActivity Class