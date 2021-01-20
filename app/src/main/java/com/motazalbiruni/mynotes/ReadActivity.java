package com.motazalbiruni.mynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ReadActivity extends AppCompatActivity {

    private MainViewModel viewModel;
    private EditText txt_title, txt_body;
    private Spinner spinner;
    private static Integer id_note;
    public static final String KEY_ID = "id_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle( R.string.title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_read);

        txt_title = findViewById(R.id.txtTitle_Read); //text for title note
        txt_body = findViewById(R.id.txtNote_Read); //text for body note
        spinner = findViewById(R.id.spinner_important);//choose the type of important to note

        List<String> list_spinner = new ArrayList<>();
        list_spinner.add(getResources().getString(R.string.a));
        list_spinner.add(getResources().getString(R.string.b));
        list_spinner.add(getResources().getString(R.string.c));

        //adapter of spinner
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(this,R.layout.spinner_item,list_spinner  );
        spinner.setAdapter(adapterSpinner);
        spinner.setEnabled(false);


        Bundle extrasBundle = getIntent().getExtras(); //to get id for note click
        if (extrasBundle != null) {
            id_note = extrasBundle.getInt(KEY_ID);
        }

        viewModel = ViewModelProviders.of(this, ViewModelProvider.AndroidViewModelFactory.
                getInstance(this.getApplication())).get(MainViewModel.class);

        if (id_note != -1) {
            //if id >= 1 get note form database
            viewModel.getNoteById(id_note).observe(this, new Observer<NoteEntity>() {
                @Override
                public void onChanged(NoteEntity noteEntity) {
                    if (noteEntity != null) { //noteEntity isn't null invoked this
                        txt_title.setText(noteEntity.getTitle());
                        txt_body.setText(noteEntity.getBody());
                        spinner.setSelection(noteEntity.getType());
                    }
                }
            });
        } else {
            //id =-1 create new note
            txt_title.setText("");
            txt_body.setText("");
            editData();
        }

    }
    //create option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }//end onCreateOptionsMenu()
    //handle item select
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.edit_item:
                Toast.makeText(this, getResources().getString(R.string.edit), Toast.LENGTH_SHORT).show();
                editData();
                return true;
            case R.id.save_item:
                saveData();
                return true;
            case R.id.delete_item:
                deleteFromData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }//end onOptionsItemSelected()

    private void saveData(){
        Toast.makeText(this, getResources().getString(R.string.save), Toast.LENGTH_SHORT).show();
        String txt_title_editing = txt_title.getText().toString();
        String txt_body_editing = txt_body.getText().toString();
        if (id_note != -1) {
            viewModel.update(new NoteEntity(id_note, txt_title_editing, txt_body_editing,spinner.getSelectedItemPosition()));
        } else {
            viewModel.insert(new NoteEntity(txt_title_editing, txt_body_editing,spinner.getSelectedItemPosition()));
        }
        finish();
    }

    private void editData(){
        txt_title.setEnabled(true);
        txt_body.setEnabled(true);
        spinner.setEnabled(true);
    }

    private void deleteFromData(){
        if (id_note != -1) {
            viewModel.deleteById(id_note);
            finish();
        }
        Toast.makeText(this,  getResources().getString(R.string.delete), Toast.LENGTH_SHORT).show();
    }
}//end Class