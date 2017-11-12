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

import info.androidhive.tabsswipe.Activities.Dao.CatedraDao;
import info.androidhive.tabsswipe.Activities.Dao.ComisionDao;
import info.androidhive.tabsswipe.Activities.Dao.PCCDao;
import info.androidhive.tabsswipe.Activities.Entities.Catedra;
import info.androidhive.tabsswipe.Activities.Entities.Comision;
import info.androidhive.tabsswipe.Activities.Entities.Profesor;

/**
 * Created by USUARIO on 12/11/2017.
 */

public class JsonReaderMaterias extends AsyncTask<String, Void, JSONObject[]> {
    private Exception error;
    private Context context;

    public JsonReaderMaterias(Context context) {
        this.context = context;
    }

    @Override
    protected JSONObject[] doInBackground(String... strings) {
        String outputData;
        JSONObject[] jsons = new JSONObject[3];
        try {
            for (int i = 0; i < 3; i++) {
                HttpClient client = new DefaultHttpClient();
                HttpUriRequest request = new HttpGet(strings[i]);
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
                        JSONObject aux = new JSONObject(outputData);
                        jsons[i] = aux;
                        return jsons;
                    } catch (JSONException e) {
                        error = e;
                    }
                } else {
                    throw new Exception("Status code != 200: " + statusCode);
                }
            }


        } catch (Exception e) {
            error = e;
        }
        return new JSONObject[0];
    }

    @Override
    protected void onPostExecute(JSONObject[] result) {
        if (error == null) {
            try {
                parseJSONMaterias(result[0].getJSONArray(""));
                parseJSONComisiones(result[1].getJSONArray(""));
                parseJSONProfexMateriaxComision(result[2].getJSONArray(""));
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void parseJSONMaterias(JSONArray array) throws JSONException {
        CatedraDao catedraDao = new CatedraDao(context);

        for (int i = 0; i < array.length(); i++) {
            Catedra c = new Catedra();
            c.setNombre(array.getJSONObject(i).getString("nombre"));
            c.setId_catedra(array.getJSONObject(i).getInt("id_catedra"));

            catedraDao.insertCatedra(c);
        }

    }

    private void parseJSONComisiones(JSONArray array) throws JSONException {
        ComisionDao comisionDao = new ComisionDao(context);

        for (int i = 0; i < array.length(); i++) {
            Comision c = new Comision();
            c.setNombre(array.getJSONObject(i).getString("nombre"));
            c.setId_comision(array.getJSONObject(i).getInt("id_comision"));

            comisionDao.insertComision(c);
        }
    }

    private void parseJSONProfexMateriaxComision(JSONArray array) throws JSONException {
        PCCDao pccDao = new PCCDao(context);

        for (int i = 0; i < array.length(); i++) {
            Comision c = new Comision();
            c.setId_comision(array.getJSONObject(i).getInt("id_comision"));

            Catedra cat = new Catedra();
            cat.setId_catedra(array.getJSONObject(i).getInt("id_catedra"));

            Profesor p = new Profesor();
            p.setId_profesor(array.getJSONObject(i).getInt("id_profesor"));

            pccDao.insertPCC(c, cat, p);
        }
    }
}
