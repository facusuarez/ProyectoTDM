package info.androidhive.tabsswipe.Activities.JSON;

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
import java.util.ArrayList;
import java.util.List;

import info.androidhive.tabsswipe.Activities.Dao.ProfesorDao;
import info.androidhive.tabsswipe.Activities.Entities.Profesor;

/**
 * Created by USUARIO on 09/11/2017.
 */

public class JsonReader extends AsyncTask<String, Void, JSONObject> {
    private Exception error;
    private Context context;
    private int motivo;
    private final static String URL_MATERIA = "";
    private final static String URL_COMISION = "";
    private final static String URL_PCC = "";


    public JsonReader(Context context, int motivo) {
        this.context = context;
        this.motivo = motivo;
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
        } catch (Exception e) {
            error = e;
        }
        return null;
    }

    private void parseJSONProfesor(JSONArray jsonArray) throws JSONException {
        ProfesorDao profesorDao = new ProfesorDao(context);
        long count = profesorDao.HayDatos();
        if (count == 0) {
            for (int i = 0; i < jsonArray.length(); i++) {
                Profesor p = new Profesor();
                p.setId_profesor(jsonArray.getJSONObject(i).getInt("id_profesor"));
                p.setNombre(jsonArray.getJSONObject(i).getString("nombre"));
                p.setApellido(jsonArray.getJSONObject(i).getString("apellido"));
                p.setPuntaje(jsonArray.getJSONObject(i).getDouble("puntaje"));
                profesorDao.insertProfesor(p);
                JsonReaderMaterias readerMaterias = new JsonReaderMaterias(context);
                readerMaterias.execute(URL_MATERIA, URL_COMISION, URL_PCC);
            }
        } else {
            List<Profesor> profesorList = new ArrayList<Profesor>();
            for (int i = 0; i < jsonArray.length(); i++) {
                Profesor p = new Profesor();
                p.setId_profesor(jsonArray.getJSONObject(i).getInt("id_profesor"));
                p.setPuntaje(jsonArray.getJSONObject(i).getDouble("puntaje"));
                profesorList.add(p);
            }
            profesorDao.updateProfesor(profesorList);
        }
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        if (error == null) {
            try {
                switch (motivo) {
                    case 1: //profesor
                        parseJSONProfesor(result.getJSONArray("Table"));
                        break;
                    case 2: //comentarios
                        parseJSONComentario(result.getJSONArray(""));
                        break;
                }

            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            }
            //  output.setText(outputData);
        } else {
            Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void parseJSONComentario(JSONArray jsonArray) throws JSONException {
        ProfesorDao profesorDao = new ProfesorDao(context);
        long count = profesorDao.HayDatos();
        if (count == 0) {
            for (int i = 0; i < jsonArray.length(); i++) {
                Profesor p = new Profesor();
                p.setId_profesor(jsonArray.getJSONObject(i).getInt("id_profesor"));
                p.setNombre(jsonArray.getJSONObject(i).getString("nombre"));
                p.setApellido(jsonArray.getJSONObject(i).getString("apellido"));
                p.setPuntaje(jsonArray.getJSONObject(i).getDouble("puntaje"));
                profesorDao.insertProfesor(p);
                JsonReaderMaterias readerMaterias = new JsonReaderMaterias(context);
                readerMaterias.execute(URL_MATERIA, URL_COMISION, URL_PCC);
            }
        } else {
            List<Profesor> profesorList = new ArrayList<Profesor>();
            for (int i = 0; i < jsonArray.length(); i++) {
                Profesor p = new Profesor();
                p.setId_profesor(jsonArray.getJSONObject(i).getInt("id_profesor"));
                p.setPuntaje(jsonArray.getJSONObject(i).getDouble("puntaje"));
                profesorList.add(p);
            }
            profesorDao.updateProfesor(profesorList);
        }
    }
}
