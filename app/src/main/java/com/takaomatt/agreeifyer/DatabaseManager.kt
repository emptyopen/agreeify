package com.takaomatt.agreeifyer

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class DatabaseManager(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    @Throws(SQLiteConstraintException::class)
    fun insert(choice: Choice): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(DBContract.UserEntry.COLUMN_DESCRIPTION, choice.description)
        values.put(DBContract.UserEntry.COLUMN_TAGS, choice.tags)
        values.put(DBContract.UserEntry.COLUMN_TEAM1, choice.team1)
        values.put(DBContract.UserEntry.COLUMN_TEAM2, choice.team2)

        // Insert the new row
        db.insert(DBContract.UserEntry.TABLE_NAME, null, values)

        return true
    }

    @Throws(SQLiteConstraintException::class)
    fun delete(userid: String): Boolean {
        val db = writableDatabase
        val selection = DBContract.UserEntry.COLUMN_ID + " LIKE ?"
        val selectionArgs = arrayOf(userid)
        db.delete(DBContract.UserEntry.TABLE_NAME, selection, selectionArgs)

        return true
    }

    fun readAll(): ArrayList<Choice> {
        val choices = ArrayList<Choice>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.UserEntry.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                val description = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_DESCRIPTION))
                val tags = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_TAGS))
                val team1 = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_TEAM1))
                val team2 = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_TEAM2))

                choices.add(Choice(description, tags, team1, team2))
                cursor.moveToNext()
            }
        }
        return choices
    }

    fun readWithQuery(query: String): ArrayList<Choice> {
        val choices = ArrayList<Choice>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from ${DBContract.UserEntry.TABLE_NAME} $query;", null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                val description = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_DESCRIPTION))
                val tags = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_TAGS))
                val team1 = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_TEAM1))
                val team2 = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_TEAM2))

                choices.add(Choice(description, tags, team1, team2))
                cursor.moveToNext()
            }
        }
        return choices
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        val DATABASE_VERSION = 2
        val DATABASE_NAME = "FeedReader.db"

        private val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBContract.UserEntry.TABLE_NAME + " (" +
                    DBContract.UserEntry.COLUMN_DESCRIPTION + " TEXT PRIMARY KEY," +
                    DBContract.UserEntry.COLUMN_TAGS + " TEXT," +
                    DBContract.UserEntry.COLUMN_TEAM1 + " TEXT," +
                    DBContract.UserEntry.COLUMN_TEAM2 + " TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.UserEntry.TABLE_NAME
    }
}