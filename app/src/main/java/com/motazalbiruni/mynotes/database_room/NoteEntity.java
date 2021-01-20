package com.motazalbiruni.mynotes.database_room;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes_table")
public class NoteEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String body;
    private int type;

    //constructor
    public NoteEntity(String title, String body,int type) {
        this.title = title;
        this.body = body;
        this.type = type;
    }

    //ignore
    @Ignore
    public NoteEntity(int id, String title, String body,int type) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.type = type;
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
}
