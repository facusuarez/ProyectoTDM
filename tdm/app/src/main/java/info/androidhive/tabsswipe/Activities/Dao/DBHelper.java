package info.androidhive.tabsswipe.Activities.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by USUARIO on 09/10/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "TDM_MasterList";
    static final int DATABASE_VERSION = 1;
    static final String URL_PROFESORES = "http://www.masterlist.somee.com/WebService.asmx/getProfesores";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE \"profesores\" (\"id_profesor\" INTEGER PRIMARY KEY AUTOINCREMENT,\"nombre\" TEXT NOT NULL,\"apellido\" TEXT NOT NULL,\"puntaje\" DOUBLE)");
        db.execSQL("CREATE TABLE \"comisiones\" (\"id_comision\" INTEGER PRIMARY KEY AUTOINCREMENT,\"nombre\" TEXT NOT NULL)");
        db.execSQL("CREATE TABLE \"catedras\" (\"id_catedra\" INTEGER PRIMARY KEY AUTOINCREMENT,\"nombre\" TEXT NOT NULL)");
        db.execSQL("CREATE TABLE \"usuarios\" (\"id_usuario\" INTEGER PRIMARY KEY AUTOINCREMENT,\"nombre\" TEXT NOT NULL,\"password\" TEXT NOT NULL)");
        db.execSQL("CREATE TABLE \"profe_catedra_comision\" (\"id\" INTEGER PRIMARY KEY AUTOINCREMENT,\"id_profesor\" INTEGER NOT NULL,\"id_catedra\" INTEGER NOT NULL,\"id_comision\" INTEGER NOT NULL, FOREIGN KEY (id_profesor) REFERENCES profesores(id_profesor),FOREIGN KEY (id_comision) REFERENCES comisiones(id_comision),FOREIGN KEY (id_catedra) REFERENCES catedras(id_catedra))");
        db.execSQL("CREATE TABLE \"comentario\" (\"id_comentario\" INTEGER PRIMARY KEY AUTOINCREMENT,\"id_profesor\" INTEGER NOT NULL,\"descripcion\" TEXT NOT NULL,\"puntos\" FLOAT NOT NULL,\"fecha_hora\" TEXT NOT NULL,\"id_usuario\" INTEGER NOT NULL, FOREIGN KEY (id_profesor) REFERENCES profesores(id_profesor),FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario))");
        //db.execSQL("CREATE TABLE \"puntuacion\" (\"id_puntuacion\" INTEGER PRIMARY KEY AUTOINCREMENT,\"id_profesor\" INTEGER NOT NULL,\"puntos\" INTEGER NOT NULL,\"fecha_hora\" DATETIME NOT NULL,\"id_usuario\" INTEGER NOT NULL, FOREIGN KEY (id_profesor) REFERENCES profesores(id_profesor),FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario))");
        db.execSQL("CREATE TABLE \"profesores_favoritos\" (\"id_favorito\" INTEGER PRIMARY KEY AUTOINCREMENT,\"id_profesor\" INTEGER NOT NULL,\"id_usuario\" INTEGER NOT NULL, FOREIGN KEY (id_profesor) REFERENCES profesores(id_profesor),FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario))");
        //cargarDatosIniciales(db);
        //getWB(db);
    }

    private void getWB(SQLiteDatabase db) {
        JsonReader reader = new JsonReader(db);
        reader.execute(URL_PROFESORES);
    }

    private void cargarDatosIniciales(SQLiteDatabase db) {
        //profesores
        ContentValues values = new ContentValues();
        values.put("nombre", "Cecilia");//id 1
        values.put("apellido", "Sanchez");
        db.insert("profesores", null, values);
        values.clear();
        values.put("nombre", "Valerio");//id 2
        values.put("apellido", "Fritelli");
        db.insert("profesores", null, values);
        values.clear();
        values.put("nombre", "Jose Luis");//id 3
        values.put("apellido", "Galoppo");
        db.insert("profesores", null, values);
        values.clear();

        values.put("nombre", "4K1");//id 1
        db.insert("comisiones", null, values);
        values.clear();
        values.put("nombre", "4K4");//id 2
        db.insert("comisiones", null, values);
        values.clear();
        values.put("nombre", "1K1");//id 3
        db.insert("comisiones", null, values);
        values.clear();
        values.put("nombre", "2K2");//id 4
        db.insert("comisiones", null, values);
        values.clear();

        values.put("nombre", "Redes de Información");//id 1
        db.insert("catedras", null, values);
        values.clear();
        values.put("nombre", "Algoritmos y Estructuras de Datos");//id 2
        db.insert("catedras", null, values);
        values.clear();
        values.put("nombre", "Sistemas Operativos");//id 3
        db.insert("catedras", null, values);
        values.clear();

        values.put("id_profesor", "1");//id 1
        values.put("id_comision", "1");
        values.put("id_catedra", "1");
        db.insert("profe_catedra_comision", null, values);
        values.clear();
        values.put("id_profesor", "2");//id 2
        values.put("id_comision", "3");
        values.put("id_catedra", "2");
        db.insert("profe_catedra_comision", null, values);
        values.clear();
        values.put("id_profesor", "3");//id 3
        values.put("id_comision", "2");
        values.put("id_catedra", "1");
        db.insert("profe_catedra_comision", null, values);
        values.clear();
        values.put("id_profesor", "1");//id 4
        values.put("id_comision", "4");
        values.put("id_catedra", "3");
        db.insert("profe_catedra_comision", null, values);
        values.clear();

        values.put("nombre", "admin");
        values.put("password", "Admin00");
        db.insert("usuarios", null, values);
        values.clear();
    }


    public class JsonReader extends AsyncTask<String, Void, String> {
        private String output;
        private String outputData;
        private Exception error;
        private JSONObject json;
        private SQLiteDatabase _db;


        public JsonReader(SQLiteDatabase db) {
            this.output = output;
            json = null;
            _db = db;
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                HttpClient client = new DefaultHttpClient();
                HttpUriRequest request = new HttpGet(params[0]);
                HttpResponse response = client.execute(request);
                StatusLine statusLine = response.getStatusLine();
                int statusCode = statusLine.getStatusCode();
                if (statusCode == 200) {
                    StringBuilder builder = new StringBuilder();
                    HttpEntity entity = response.getEntity();
                    InputStream content = entity.getContent();
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(content));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }
                    outputData = builder.toString();
                    try {
                        json = new JSONObject(outputData);


                    } catch (JSONException e) {
                    }
                } else {
                    throw new Exception("Status code != 200: " + statusCode);
                }
            } catch (Exception e) {
                error = e;
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (error == null) {
                output = outputData;
                try {
                    parseJSON(json.getJSONArray("Table"));
                } catch (Exception e) {
                    String a = e.getMessage();
                }
                //  output.setText(outputData);
            } else {

            }
        }

        private void parseJSON(JSONArray jsonArray) throws JSONException {
            ContentValues values = new ContentValues();

            for (int i = 0; i < jsonArray.length(); i++) {
                values.put("nombre", jsonArray.getJSONObject(i).getString("nombre"));
                values.put("apellido", jsonArray.getJSONObject(i).getString("apellido"));
                values.put("puntaje", jsonArray.getJSONObject(i).getString("puntaje"));
                //values.put("id_profesor", jsonArray.getJSONObject(i).getString("id_profesor"));
                long fila = _db.insert("profesores",null,values);
                values.clear();
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}

