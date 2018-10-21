package info.androidhive.tabsswipe.Activities.JSON;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

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
import java.net.UnknownHostException;

import info.androidhive.tabsswipe.Activities.ConstantesGenerales;
import info.androidhive.tabsswipe.Activities.Dao.AppDatabase;
import info.androidhive.tabsswipe.Activities.Dao.ProfesorDao;
import info.androidhive.tabsswipe.Activities.Entities.Profesor;

/**
 * Created by USUARIO on 09/11/2017.
 */

public class JsonReaderGetProfesores extends AsyncTask<String, Void, JSONObject> {
    private Exception error;
    private Context context;

    public JsonReaderGetProfesores(Context context) {
        this.context = context;
    }

    @Override
    protected JSONObject doInBackground(String... params) {

        String outputData;
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
                    return new JSONObject(outputData);
                } catch (JSONException e) {
                    error = e;
                }
            } else {
                throw new Exception("Status code != 200: " + statusCode);
            }
        }catch (UnknownHostException e){
          //  Toast.makeText(context, "Asegúrese de tener conexión", Toast.LENGTH_LONG).show();
            error=new UnknownHostException("Asegúrese de tener conexión");
        }
        catch (Exception e) {
            error = e;
        }
        return null;
    }

    private void parseJSONProfesor(JSONArray jsonArray) throws JSONException {

        AppDatabase database = Room.databaseBuilder(context, AppDatabase.class, ConstantesGenerales.DB_NAME)
                .allowMainThreadQueries()   //Allows room to do operation on main thread
                .build();

        ProfesorDao profesorDao = database.getProfesorDao();

        long count = profesorDao.hayDatos();
        if (count == 0) {
            for (int i = 0; i < jsonArray.length(); i++) {
                Profesor p = new Profesor();
                p.setId_profesor(jsonArray.getJSONObject(i).getInt("id_profesor"));
                p.setNombre(jsonArray.getJSONObject(i).getString("nombre"));
                p.setApellido(jsonArray.getJSONObject(i).getString("apellido"));
                p.setPuntaje(jsonArray.getJSONObject(i).getDouble("puntaje"));
                profesorDao.insert(p);
            }
        } else {
            for (int i = 0; i < jsonArray.length(); i++) {
                Profesor p = profesorDao.getProfesorById(jsonArray.getJSONObject(i).getInt("id_profesor"));
                //TODO que pasa si no encuentra profesor by id??
                p.setPuntaje(jsonArray.getJSONObject(i).getDouble("puntaje"));
                profesorDao.update(p);
            }
        }
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        if (error == null) {
            try {
                parseJSONProfesor(result.getJSONArray("Table"));

            } catch (JSONException e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {

            Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


}
