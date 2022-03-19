package com.example.checked.infra;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CheckedDatabase extends SQLiteOpenHelper {
    private static final String DATABASE = "checked.db";
    private static final int VERSION = 1;

    public static final String TABLE_CHECKLIST = "checklist";
    public static final String CHECKLIST_ID = "id_checklist";
    public static final String CHECKLIST_TITLE = "title_checklist";
    public static final String CHECKLIST_DEFAULT = "default_checklist";

    public static final String TABLE_ITEMLIST = "itemlist";
    public static final String ITEM_ID = "id_item";
    public static final String ITEM_TITLE = "title_item";
    public static final String ITEM_DONE = "done_item";
    public static final String ITEM_ID_CHECK = "id_list_item";

    public CheckedDatabase(Context context){
        super(context, DATABASE, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlNewChecklist = "CREATE TABLE "+TABLE_CHECKLIST+"("
                +CHECKLIST_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +CHECKLIST_TITLE+" TEXT,"
                +CHECKLIST_DEFAULT+" INTEGER"
                +")";
        String sqlNewItemList = "CREATE TABLE "+TABLE_ITEMLIST+"("
                +ITEM_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +ITEM_TITLE+" TEXT,"
                +ITEM_DONE+" INTEGER,"
                +ITEM_ID_CHECK+" INTEGER"
                +")";

        sqLiteDatabase.execSQL(sqlNewChecklist);
        sqLiteDatabase.execSQL(sqlNewItemList);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMLIST);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CHECKLIST);
        onCreate(sqLiteDatabase);
    }
}
