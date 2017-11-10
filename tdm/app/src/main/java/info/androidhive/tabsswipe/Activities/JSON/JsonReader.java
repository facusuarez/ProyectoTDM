package info.androidhive.tabsswipe.Activities.JSON;

import android.content.Context;
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
import java.util.ArrayList;
import java.util.List;

import info.androidhive.tabsswipe.Activities.Dao.ProfesorDao;
import info.androidhive.tabsswipe.Activities.Entities.Profesor;

/**
 * Created by USUARIO on 09/11/2017.
 */

public class JsonReader extends AsyncTask<Void, Void, Void> {
    private String output;
    private String outputData;
    private Exception error;
    private JSONObject json;
    private Context context;


    public JsonReader(Context context) {
        this.output = output;
        this.context = context;
        json = null;
    }

    @Override
    protected Void doInBackground(Void... params) {

        try {
            HttpClient client = new DefaultHttpClient();
            HttpUriRequest request = new HttpGet("https://api.myjson.com/bins/1hjn9r");
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

    private void parseJSON(JSONArray jsonArray) throws JSONException {
        ProfesorDao profesorDao = new ProfesorDao(context);
        long count = profesorDao.HayDatos();
        if (count == 0) {
            for (int i = 0; i < jsonArray.length(); i++) {
                Profesor p = new Profesor();
                p.setId_profesor(jsonArray.getJSONObject(i).getInt("id"));
                p.setNombre(jsonArray.getJSONObject(i).getString("nombre"));
                p.setApellido(jsonArray.getJSONObject(i).getString("apellido"));
                p.setPuntaje(jsonArray.getJSONObject(i).getDouble("puntaje"));
                double d = jsonArray.getJSONObject(i).getDouble("puntaje");
                double b = p.getPuntaje();
                profesorDao.insertProfesor(p);
            }
        } else {
            List<Profesor> profesorList=new ArrayList<Profesor>();
            for (int i = 0; i < jsonArray.length(); i++) {
                Profesor p = new Profesor();
                p.setId_profesor(jsonArray.getJSONObject(i).getInt("id"));
                p.setPuntaje(jsonArray.getJSONObject(i).getDouble("puntaje"));
                profesorList.add(p);
            }
            profesorDao.updateProfesor(profesorList);
        }
    }

    @Override
    protected void onPostExecute(Void result) {
        if (error == null) {
            output = outputData;
            try {
                parseJSON(json.getJSONArray("Profesores"));
            } catch (Exception e) {
                String a =e.getMessage();
            }
            //  output.setText(outputData);
        } else {

        }
    }
}
