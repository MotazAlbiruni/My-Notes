package com.motazalbiruni.mynotes.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.motazalbiruni.mynotes.database_room.NoteEntity;
import com.motazalbiruni.mynotes.R;
import com.motazalbiruni.mynotes.ui.main.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class ReadActivity extends AppCompatActivity {

    private MainViewModel viewModel;
    private EditText txt_title, txt_body;
    private Spinner spinner;
    private static Integer id_note;
    public static final String KEY_ID = "id_key";

    ArrayAdapter<String> adapterSpinner;
    List<String> list_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle( R.string.title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_read);

        txt_title = findViewById(R.id.txtTitle_Read); //text for title note
        txt_body = findViewById(R.id.txtNote_Read); //text for body note
        spinner = findViewById(R.id.spinner_important);//choose the type of important to note

        list_spinner = new ArrayList<>();
        list_spinner.add(getResources().getString(R.string.a));
        list_spinner.add(getResources().getString(R.string.b));
        list_spinner.add(getResources().getString(R.string.c));

        //adapter of spinner
        adapterSpinner = new ArrayAdapter<String>(this,R.layout.spinner_item_blue,list_spinner  );
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
                        changeBackground(noteEntity.getType());

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

    @SuppressLint("UseCompatLoadingForDrawables")
    private void changeBackground(int type){
        switch (type){
            case 0:
                txt_body.setBackground(getDrawable(R.drawable.edit_text_border_blue));
                txt_title.setBackground(getDrawable(R.drawable.edit_text_border_blue));
                adapterSpinner = new ArrayAdapter<String>(this,R.layout.spinner_item_blue,list_spinner);
                spinner.setAdapter(adapterSpinner);
                spinner.setBackground(getDrawable(R.drawable.edit_text_border_blue));
                break;
            case 1:
                txt_body.setBackground(getDrawable(R.drawable.edit_text_border_green));
                txt_title.setBackground(getDrawable(R.drawable.edit_text_border_green));
                adapterSpinner = new ArrayAdapter<String>(this,R.layout.spinner_item_green,list_spinner);
                spinner.setAdapter(adapterSpinner);
                spinner.setBackground(getDrawable(R.drawable.edit_text_border_green));
                break;
            case 2:
                txt_body.setBackground(getDrawable(R.drawable.edit_text_border_orange));
                txt_title.setBackground(getDrawable(R.drawable.edit_text_border_orange));
                adapterSpinner = new ArrayAdapter<String>(this,R.layout.spinner_item_orange,list_spinner);
                spinner.setAdapter(adapterSpinner);
                spinner.setBackground(getDrawable(R.drawable.edit_text_border_orange));
                break;
        }//end switch
    }//end changeBackground
}//end Class