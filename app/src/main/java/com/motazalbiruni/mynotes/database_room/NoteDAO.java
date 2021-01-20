package com.motazalbiruni.mynotes.database_room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDAO {

    @Query("select * from notes_table")
    LiveData<List<NoteEntity>> getAllNotes();

    @Query("select * from notes_table where id= :id")
    LiveData<NoteEntity> getNoteById(int id);

    @Insert
    void insert(NoteEntity entity);

    @Update
    void update(NoteEntity entity);

    @Delete
    void delete(NoteEntity entity);

    @Query("delete from notes_table")
    void deleteAll();

    @Query("delete from notes_table where id= :id")
    void deleteById(int id);

}
