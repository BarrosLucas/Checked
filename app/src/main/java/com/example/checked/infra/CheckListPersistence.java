package com.example.checked.infra;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.checked.R;
import com.example.checked.model.ItemChecklist;

import java.util.ArrayList;

public class CheckListPersistence {
    private SQLiteDatabase db;
    private CheckedDatabase database;
    private Context context;

    public CheckListPersistence(Context context){
        this.context = context;
        database = new CheckedDatabase(context);
    }

    public boolean createChecklist(String title, boolean isDefault){
        ContentValues values;
        long result;

        db = database.getWritableDatabase();
        values = new ContentValues();

        values.put(CheckedDatabase.CHECKLIST_TITLE, title);
        values.put(CheckedDatabase.CHECKLIST_DEFAULT, (isDefault)? 1: 0);

        result = db.insert(CheckedDatabase.TABLE_CHECKLIST, null, values);
        db.close();

        if(result == -1){
            return false;
        }

        return true;
    }

    public boolean updateChecklist(long id, String title, boolean isDefault){
        ContentValues values;
        String where;
        int result;

        db = database.getWritableDatabase();

        where = CheckedDatabase.CHECKLIST_ID + " = "+id;

        values = new ContentValues();
        values.put(CheckedDatabase.CHECKLIST_TITLE, title);
        values.put(CheckedDatabase.CHECKLIST_DEFAULT, (isDefault)? 1: 0);

        result = db.update(CheckedDatabase.TABLE_CHECKLIST, values, where, null);

        if(result == -1){
            return false;
        }

        return true;
    }

    public boolean deleteChecklist(long id){
        int result;

        (new ItemListPersistence(context)).deleteItensByChecklist(id);

        String where = CheckedDatabase.CHECKLIST_ID + " = " +id;
        db = database.getReadableDatabase();
        result = db.delete(CheckedDatabase.TABLE_CHECKLIST, where, null);

        if(result == -1){
            return false;
        }

        return true;
    }

    public ItemChecklist selectByID(long id){
        ItemChecklist item = null;
        Cursor cursor;

        String[] columns = {CheckedDatabase.CHECKLIST_ID,
                CheckedDatabase.CHECKLIST_TITLE,
                CheckedDatabase.CHECKLIST_DEFAULT};

        String where = CheckedDatabase.CHECKLIST_ID + "=" + id;

        db = database.getReadableDatabase();

        cursor = db.query(CheckedDatabase.TABLE_CHECKLIST,
                columns,
                where,
                null,
                null,
                null,
                null,
                null);

        if(cursor!=null){
            cursor.moveToFirst();

            item = new ItemChecklist();
            item.setId(cursor.getInt(cursor.getColumnIndexOrThrow(CheckedDatabase.CHECKLIST_ID)));
            item.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(CheckedDatabase.CHECKLIST_TITLE)));
            item.setTaskList((new ItemListPersistence(context)).selectByChecklist(item.getId()));
            item.setDefault(((cursor.getInt(cursor.getColumnIndexOrThrow(CheckedDatabase.CHECKLIST_ID)))==1)? true: false);
        }

        return item;
    }

    public ArrayList<ItemChecklist> selectAll(){
        ArrayList<ItemChecklist> checklists = new ArrayList<>();
        Cursor cursor;

        String[] columns = {CheckedDatabase.CHECKLIST_ID,
                CheckedDatabase.CHECKLIST_TITLE,
                CheckedDatabase.CHECKLIST_DEFAULT};

        db = database.getReadableDatabase();

        cursor = db.query(CheckedDatabase.TABLE_CHECKLIST,
                columns,
                null,
                null,
                null,
                null,
                null,
                null);

        if(cursor!=null){
            if(cursor.moveToFirst()) {
                ItemChecklist item = new ItemChecklist();
                item.setId(cursor.getInt(cursor.getColumnIndexOrThrow(CheckedDatabase.CHECKLIST_ID)));
                item.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(CheckedDatabase.CHECKLIST_TITLE)));
                item.setTaskList((new ItemListPersistence(context)).selectByChecklist(item.getId()));
                item.setDefault(((cursor.getInt(cursor.getColumnIndexOrThrow(CheckedDatabase.CHECKLIST_ID)))==1)? true: false);

                checklists.add(item);
                while(cursor.moveToNext()) {
                    item = new ItemChecklist();
                    item.setId(cursor.getInt(cursor.getColumnIndexOrThrow(CheckedDatabase.CHECKLIST_ID)));
                    item.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(CheckedDatabase.CHECKLIST_TITLE)));
                    item.setTaskList((new ItemListPersistence(context)).selectByChecklist(item.getId()));
                    item.setDefault(((cursor.getInt(cursor.getColumnIndexOrThrow(CheckedDatabase.CHECKLIST_ID)))==1)? true: false);

                    checklists.add(item);
                }
            }
        }

        return checklists;
    }

    public ItemChecklist selectDefault(){
        ItemChecklist item = null;
        Cursor cursor;

        String[] columns = {CheckedDatabase.CHECKLIST_ID,
                CheckedDatabase.CHECKLIST_TITLE,
                CheckedDatabase.CHECKLIST_DEFAULT};

        String where = CheckedDatabase.CHECKLIST_DEFAULT + "=" + 1;

        db = database.getReadableDatabase();

        cursor = db.query(CheckedDatabase.TABLE_CHECKLIST,
                columns,
                where,
                null,
                null,
                null,
                null,
                null);

        if(cursor!=null) {
            if (cursor.moveToFirst()) {

                item = new ItemChecklist();
                item.setId(cursor.getInt(cursor.getColumnIndexOrThrow(CheckedDatabase.CHECKLIST_ID)));
                item.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(CheckedDatabase.CHECKLIST_TITLE)));
                item.setTaskList((new ItemListPersistence(context)).selectByChecklist(item.getId()));
                item.setDefault(((cursor.getInt(cursor.getColumnIndexOrThrow(CheckedDatabase.CHECKLIST_ID))) == 1) ? true : false);

            }
        }

        if(item == null){
            createChecklist(context.getString(R.string.app_name),true);
            item = selectDefault();
        }

        return item;
    }
}
