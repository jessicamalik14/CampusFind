package com.example.campusfind;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "campusfind.db";
    private static final int DATABASE_VERSION = 4; // 🔥 updated version

    // ================= POSTS TABLE =================
    public static final String TABLE_POSTS = "posts";
    public static final String COL_ID = "id";
    public static final String COL_TYPE = "type";
    public static final String COL_ITEM_NAME = "itemName";
    public static final String COL_CATEGORY = "category";
    public static final String COL_LOCATION = "location";
    public static final String COL_DATE = "date";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_CONTACT = "contact";
    public static final String COL_IS_MINE = "isMine";

    // ================= REQUESTS TABLE =================
    public static final String TABLE_REQUESTS = "requests";
    public static final String COL_REQ_ID = "req_id";
    public static final String COL_POST_ID = "post_id";
    public static final String COL_MESSAGE = "message";
    public static final String COL_STATUS = "status";
    public static final String COL_SENDER = "sender"; // 🔥 NEW

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // ================= CREATE TABLES =================
    @Override
    public void onCreate(SQLiteDatabase db) {

        // POSTS TABLE
        String createPosts = "CREATE TABLE " + TABLE_POSTS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TYPE + " TEXT, " +
                COL_ITEM_NAME + " TEXT, " +
                COL_CATEGORY + " TEXT, " +
                COL_LOCATION + " TEXT, " +
                COL_DATE + " TEXT, " +
                COL_DESCRIPTION + " TEXT, " +
                COL_CONTACT + " TEXT, " +
                COL_IS_MINE + " INTEGER DEFAULT 1" +
                ")";
        db.execSQL(createPosts);

        // REQUESTS TABLE
        String createRequests = "CREATE TABLE " + TABLE_REQUESTS + " (" +
                COL_REQ_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_POST_ID + " INTEGER, " +
                COL_MESSAGE + " TEXT, " +
                COL_STATUS + " TEXT, " +
                COL_SENDER + " TEXT" + // 🔥 NEW
                ")";
        db.execSQL(createRequests);
    }

    // ================= UPGRADE =================
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_POSTS +
                    " ADD COLUMN " + COL_IS_MINE + " INTEGER DEFAULT 1");
        }

        if (oldVersion < 3) {
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_REQUESTS + " (" +
                    COL_REQ_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_POST_ID + " INTEGER, " +
                    COL_MESSAGE + " TEXT, " +
                    COL_STATUS + " TEXT" +
                    ")");
        }

        // 🔥 ADD SENDER COLUMN SAFELY
        if (oldVersion < 4) {
            db.execSQL("ALTER TABLE " + TABLE_REQUESTS +
                    " ADD COLUMN " + COL_SENDER + " TEXT");
        }
    }

    // ================= INSERT POST =================
    public boolean insertPost(String type, String itemName, String category,
                              String location, String date,
                              String description, String contact) {

        return insertPost(type, itemName, category, location, date,
                description, contact, 1);
    }

    public boolean insertPost(String type, String itemName, String category,
                              String location, String date,
                              String description, String contact,
                              int isMine) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_TYPE, type);
        values.put(COL_ITEM_NAME, itemName);
        values.put(COL_CATEGORY, category);
        values.put(COL_LOCATION, location);
        values.put(COL_DATE, date);
        values.put(COL_DESCRIPTION, description);
        values.put(COL_CONTACT, contact);
        values.put(COL_IS_MINE, isMine);

        long result = db.insert(TABLE_POSTS, null, values);
        db.close();

        return result != -1;
    }

    // ================= GET POSTS =================
    public ArrayList<Post> getAllPosts() {
        return getPosts("SELECT * FROM " + TABLE_POSTS +
                " ORDER BY " + COL_ID + " DESC");
    }

    public ArrayList<Post> getMyPosts() {
        return getPosts("SELECT * FROM " + TABLE_POSTS +
                " WHERE " + COL_IS_MINE + "=1 ORDER BY " + COL_ID + " DESC");
    }

    private ArrayList<Post> getPosts(String query) {

        ArrayList<Post> postList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

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
        db.close();

        return postList;
    }

    // ================= DELETE =================
    public void deletePost(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_POSTS, COL_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    // ================= UPDATE =================
    public void updatePost(Post post) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COL_TYPE, post.getType());
        values.put(COL_ITEM_NAME, post.getItemName());
        values.put(COL_CATEGORY, post.getCategory());
        values.put(COL_LOCATION, post.getLocation());
        values.put(COL_DATE, post.getDate());
        values.put(COL_DESCRIPTION, post.getDescription());
        values.put(COL_CONTACT, post.getContact());

        db.update(TABLE_POSTS, values,
                COL_ID + "=?",
                new String[]{String.valueOf(post.getId())});

        db.close();
    }

    // ================= REQUEST SYSTEM =================

    public void insertRequest(int postId, String message, String sender) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_POST_ID, postId);
        values.put(COL_MESSAGE, message);
        values.put(COL_STATUS, "pending");
        values.put(COL_SENDER, sender); // 🔥 FIXED

        db.insert(TABLE_REQUESTS, null, values);
        db.close();
    }

    public void updateRequestStatus(int reqId, String status) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_STATUS, status);

        db.update(TABLE_REQUESTS, values,
                COL_REQ_ID + "=?",
                new String[]{String.valueOf(reqId)});

        db.close();
    }

    public ArrayList<Request> getAllRequests() {

        ArrayList<Request> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_REQUESTS, null);

        if (cursor.moveToFirst()) {
            do {
                list.add(new Request(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_REQ_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_POST_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_MESSAGE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_STATUS)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_SENDER)) // 🔥 NEW
                ));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return list;
    }
}