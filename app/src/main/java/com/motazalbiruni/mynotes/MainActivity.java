package com.motazalbiruni.mynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import static com.motazalbiruni.mynotes.ReadActivity.KEY_ID;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final AdapterNotes adapterNotes = new AdapterNotes(this);
        recyclerView.setAdapter(adapterNotes);

        MainViewModel viewModel = ViewModelProviders.of(this, ViewModelProvider.AndroidViewModelFactory.
                getInstance(this.getApplication())).get(MainViewModel.class);

        viewModel.getAllNotes().observe(this, new Observer<List<NoteEntity>>() {
            @Override
            public void onChanged(List<NoteEntity> noteEntities) {
                Toast.makeText(getApplicationContext(), "title is : " +
                        noteEntities.get(0).getTitle() + "{ " +
                        noteEntities.get(0).getId() + " }", Toast.LENGTH_SHORT).show();
                adapterNotes.setList(noteEntities);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }//end onCreateOptionsMenu()

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id =item.getItemId();
        switch (id){
            case R.id.add_item:
                Toast.makeText(this, getResources().getString(R.string.add), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this,ReadActivity.class);
                intent.putExtra(KEY_ID,-1);
                startActivity(intent);
                return true;
            case R.id.setting:
                Toast.makeText(this,  getResources().getString(R.string.setting), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.about:
                Toast.makeText(this,  getResources().getString(R.string.about), Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}