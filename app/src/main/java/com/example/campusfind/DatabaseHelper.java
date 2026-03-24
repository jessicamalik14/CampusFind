package com.example.campusfind;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "campusfind.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_POSTS = "posts";
    public static final String COL_ID = "id";
    public static final String COL_TYPE = "type";
    public static final String COL_ITEM_NAME = "itemName";
    public static final String COL_CATEGORY = "category";
    public static final String COL_LOCATION = "location";
    public static final String COL_DATE = "date";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_CONTACT = "contact";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_POSTS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TYPE + " TEXT, " +
                COL_ITEM_NAME + " TEXT, " +
                COL_CATEGORY + " TEXT, " +
                COL_LOCATION + " TEXT, " +
                COL_DATE + " TEXT, " +
                COL_DESCRIPTION + " TEXT, " +
                COL_CONTACT + " TEXT" +
                ")";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POSTS);
        onCreate(db);
    }

    public boolean insertPost(String type, String itemName, String category,
                              String location, String date, String description, String contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_TYPE, type);
        values.put(COL_ITEM_NAME, itemName);
        values.put(COL_CATEGORY, category);
        values.put(COL_LOCATION, location);
        values.put(COL_DATE, date);
        values.put(COL_DESCRIPTION, description);
        values.put(COL_CONTACT, contact);

        long result = db.insert(TABLE_POSTS, null, values);
        return result != -1;
    }

    public ArrayList<Post> getAllPosts() {
        ArrayList<Post> postList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_POSTS + " ORDER BY " + COL_ID + " DESC", null);

        if (cursor.moveToFirst()) {
            do {
                Post post = new Post(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_TYPE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_ITEM_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_CATEGORY)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_LOCATION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_DATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_CONTACT))
                );
                postList.add(post);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return postList;
    }
}