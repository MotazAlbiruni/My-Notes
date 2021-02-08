package com.motazalbiruni.mynotes.database_room;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes_table")
public class NoteEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private final String title;
    private final String body;
    private final int type;
    private final String date;

    //constructor
    public NoteEntity(String title, String body,int type, String date) {
        this.title = title;
        this.body = body;
        this.type = type;
        this.date = date;
    }

    //ignore
    @Ignore
    public NoteEntity(int id, String title, String body,int type, String date) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.type = type;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public int getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

}//end class
