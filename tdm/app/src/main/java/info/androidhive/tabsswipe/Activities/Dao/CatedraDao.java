package info.androidhive.tabsswipe.Activities.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import info.androidhive.tabsswipe.Activities.Entities.Catedra;

/**
 * Created by USUARIO on 12/11/2017.
 */

public class CatedraDao {
    private DBHelper dbHelper;

    public CatedraDao(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int insertCatedra(Catedra catedra){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre",catedra.getNombre());
        values.put("id_catedra",catedra.getId_catedra());
        int id = (int) db.insert("catedras", null, values);
        db.close();
        return id;
    }

}
