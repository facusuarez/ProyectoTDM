package info.androidhive.tabsswipe.Activities.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import info.androidhive.tabsswipe.Activities.Entities.Catedra;
import info.androidhive.tabsswipe.Activities.Entities.Comision;
import info.androidhive.tabsswipe.Activities.Entities.Profesor;

/**
 * Created by USUARIO on 12/11/2017.
 */

public class PCCDao {
    private DBHelper dbHelper;

    public PCCDao(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int insertPCC (Comision comision, Catedra catedra, Profesor profesor){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_profesor",profesor.getId_profesor());
        values.put("id_comision",comision.getId_comision());
        values.put("id_catedra",catedra.getId_catedra());
        int id = (int) db.insert("profe_catedra_comision", null, values);
        db.close();
        return id;
    }
}
