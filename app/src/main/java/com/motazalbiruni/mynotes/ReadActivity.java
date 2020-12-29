package com.motazalbiruni.mynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ReadActivity extends AppCompatActivity {

    private MainViewModel viewModel;
    private EditText txt_1, txt_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("  Note");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_read);

        txt_1 = findViewById(R.id.txtTitle_Read);
        txt_2 = findViewById(R.id.txtNote_Read);

        Bundle extrasBundle = getIntent().getExtras();
        int id = extrasBundle.getInt("id");

        viewModel = ViewModelProviders.of(this, ViewModelProvider.AndroidViewModelFactory.
                getInstance(this.getApplication())).get(MainViewModel.class);

        viewModel.getNoteById(id).observe(this, new Observer<NoteEntity>() {
            @Override
            public void onChanged(NoteEntity noteEntity) {
                txt_1.setText(noteEntity.getTitle());
                txt_2.setText(noteEntity.getBody());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }//end onCreateOptionsMenu()

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.edit_item:
                Toast.makeText(this, "Edit", Toast.LENGTH_SHORT).show();
                txt_1.setEnabled(true);
                txt_2.setEnabled(true);
                return true;
            case R.id.save_item:
                Toast.makeText(this, "Save", Toast.LENGTH_SHORT).show();
                String txt_title_editing = txt_1.getText().toString();
                String txt_body_editing = txt_2.getText().toString();

                return true;
            case R.id.add_item:
                Toast.makeText(this, "Add", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.delete_item:
                Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }//end onOptionsItemSelected()
}//end Class