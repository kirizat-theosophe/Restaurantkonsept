package com.example.uke_3_4_oppgave.login

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDBhelper(context: Context): SQLiteOpenHelper(context, "USERDB", null, 1 ) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE USERS (USERID INTEGER PRIMARY KEY AUTOINCREMENT, UNAME TEXT, PWD TEXT)")
        db?.execSQL("INSERT INTO USERS (UNAME, PWD) VALUES('kirizat@hotmail.com', '123')")
        db?.execSQL("INSERT INTO USERS (UNAME, PWD) VALUES('kirizatheosophejobb@gmail.com', '1234')")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}