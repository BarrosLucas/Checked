package org.barros.checked.infra;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.barros.checked.model.ItemTask;

import java.util.ArrayList;

public class ItemListPersistence {
    private SQLiteDatabase db;
    private CheckedDatabase database;

    public ItemListPersistence(Context context){
        database = new CheckedDatabase(context);
    }

    public boolean createItemlist(String title, long idChecklist, boolean done){
        ContentValues values;
        long result;

        db = database.getWritableDatabase();
        values = new ContentValues();

        values.put(CheckedDatabase.ITEM_TITLE, title);
        values.put(CheckedDatabase.ITEM_DONE, (done)? 1: 0);
        values.put(CheckedDatabase.ITEM_ID_CHECK, idChecklist);

        result = db.insert(CheckedDatabase.TABLE_ITEMLIST, null, values);
        db.close();

        if(result == -1){
            return false;
        }

        return true;
    }

    public boolean updateChecklist(long id, String title, boolean done){
        ContentValues values;
        String where;
        int result;

        db = database.getWritableDatabase();

        where = CheckedDatabase.ITEM_ID + " = "+id;

        values = new ContentValues();
        values.put(CheckedDatabase.ITEM_TITLE, title);
        values.put(CheckedDatabase.ITEM_DONE, ((done)? 1: 0));

        result = db.update(CheckedDatabase.TABLE_ITEMLIST, values, where, null);

        if(result == -1){
            return false;
        }

        return true;
    }

    public boolean deleteChecklist(long id){
        int result;

        String where = CheckedDatabase.ITEM_ID + " = " +id;
        db = database.getReadableDatabase();
        result = db.delete(CheckedDatabase.TABLE_ITEMLIST, where, null);

        if(result == -1){
            return false;
        }

        return true;
    }

    public boolean deleteItensByChecklist(long idChecklist){
        int result;

        String where = CheckedDatabase.ITEM_ID_CHECK + " = " +idChecklist;
        db = database.getReadableDatabase();
        result = db.delete(CheckedDatabase.TABLE_ITEMLIST, where, null);

        if(result == -1){
            return false;
        }

        return true;
    }

    public ItemTask selectByID(long id){
        ItemTask item = null;
        Cursor cursor;

        String[] columns = {CheckedDatabase.ITEM_ID,
                CheckedDatabase.ITEM_TITLE,
                CheckedDatabase.ITEM_DONE,
                CheckedDatabase.ITEM_ID_CHECK};

        String where = CheckedDatabase.ITEM_ID + "=" + id;

        db = database.getReadableDatabase();

        cursor = db.query(CheckedDatabase.TABLE_ITEMLIST,
                columns,
                where,
                null,
                null,
                null,
                null,
                null);

        if(cursor!=null){
            cursor.moveToFirst();

            item = new ItemTask();
            item.setId(cursor.getInt(cursor.getColumnIndexOrThrow(CheckedDatabase.ITEM_ID)));
            item.setDone((cursor.getInt(cursor.getColumnIndexOrThrow(CheckedDatabase.ITEM_DONE))) == 1? true:false);
            item.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(CheckedDatabase.ITEM_TITLE)));
            item.setIdList(cursor.getInt(cursor.getColumnIndexOrThrow(CheckedDatabase.ITEM_ID_CHECK)));
        }

        return item;
    }

    public ArrayList<ItemTask> selectByChecklist(long idChecklist){
        ArrayList<ItemTask> tasks = new ArrayList<>();
        Cursor cursor;

        String[] columns = {CheckedDatabase.ITEM_ID,
                CheckedDatabase.ITEM_TITLE,
                CheckedDatabase.ITEM_DONE,
                CheckedDatabase.ITEM_ID_CHECK};

        String where = CheckedDatabase.ITEM_ID_CHECK + "=" + idChecklist;

        db = database.getReadableDatabase();

        cursor = db.query(CheckedDatabase.TABLE_ITEMLIST,
                columns,
                where,
                null,
                null,
                null,
                null,
                null);

        if(cursor.moveToFirst()){
            ItemTask item = new ItemTask();
            item.setId(cursor.getInt(cursor.getColumnIndexOrThrow(CheckedDatabase.ITEM_ID)));
            item.setDone((cursor.getInt(cursor.getColumnIndexOrThrow(CheckedDatabase.ITEM_DONE))) == 1? true:false);
            item.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(CheckedDatabase.ITEM_TITLE)));
            item.setIdList(cursor.getInt(cursor.getColumnIndexOrThrow(CheckedDatabase.ITEM_ID_CHECK)));

            tasks.add(item);

            while(cursor.moveToNext()){
                item = new ItemTask();
                item.setId(cursor.getInt(cursor.getColumnIndexOrThrow(CheckedDatabase.ITEM_ID)));
                item.setDone((cursor.getInt(cursor.getColumnIndexOrThrow(CheckedDatabase.ITEM_DONE))) == 1? true:false);
                item.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(CheckedDatabase.ITEM_TITLE)));
                item.setIdList(cursor.getInt(cursor.getColumnIndexOrThrow(CheckedDatabase.ITEM_ID_CHECK)));

                tasks.add(item);
            }
        }

        return tasks;
    }
}
