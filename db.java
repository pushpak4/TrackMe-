package com.example.trackme.tm.trackme;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class db extends SQLiteOpenHelper {
    public db(Context context) {
        super(context, "db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       String register="Create TABLE User(userid INTEGER primary key AUTOINCREMENT, name VARCHAR, code VARCHAR, email VARCHAR, password VARCHAR, phone VARCHAR);";
       db.execSQL(register);

       String req="Create TABLE Requests(request_id VARCHAR primary key, request_type VARCHAR, sender_id INTEGER, reciever_id INTEGER, " +
               "FOREIGN KEY(sender_id)REFERENCES User(userid), " +
               "FOREIGN KEY(reciever_id)REFERENCES User(userid));";
       db.execSQL(req);

        String friend="Create TABLE Friendships(relationship_id VARCHAR primary key, Confidant_of_1 BOOLEAN NOT NULL, Confidant_of_2 BOOLEAN NOT NULL, user_id_1 INTEGER, user_id_2 INTEGER, " +
                "FOREIGN KEY(user_id_1)REFERENCES User(userid), " +
                "FOREIGN KEY(user_id_2)REFERENCES User(userid));";
        db.execSQL(friend);

        String map="Create TABLE Cordinates(userid INTEGER primary key AUTOINCREMENT, lati VARCHAR, longit VARCHAR);";
        db.execSQL(map);

        String acc="Create TABLE Readings(userid INTEGER primary key AUTOINCREMENT, x VARCHAR, y VARCHAR, z VARCHAR);";
        db.execSQL(acc);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
