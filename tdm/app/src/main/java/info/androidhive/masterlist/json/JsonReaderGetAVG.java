package info.androidhive.masterlist.json;

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

import info.androidhive.masterlist.Constants;

/**
 * Created by USUARIO on 14/11/2017.
 */

public class JsonReaderGetAVG extends AsyncTask<String, Void, JSONObject> {
    private Context _context;
    private int _idProfe;
    private float _puntaje;
    private Exception error;

    public JsonReaderGetAVG(Context context, int idProfe) {
        _context = context;
        _idProfe = idProfe;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        String outputData;
        JSONObject jsonObject = null;
        try {
            HttpClient client = new DefaultHttpClient();
            HttpUriRequest request = new HttpGet(strings[0] + _idProfe);
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
                    jsonObject = new JSONObject(outputData);
                } catch (JSONException e) {
                    error = e;
                }
            } else {
                throw new Exception("Status code != 200: " + statusCode);
            }

        } catch (UnknownHostException e) {
            //  Toast.makeText(context, "Asegúrese de tener conexión", Toast.LENGTH_SHORT).show();
            error = new UnknownHostException("Asegúrese de tener conexión");
        } catch (Exception e) {
            error = e;
        }
        return jsonObject;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("Table");
            _puntaje = (float) jsonArray.getJSONObject(0).getDouble("Column1");
            JsonPostUpdatePuntaje post = new JsonPostUpdatePuntaje(_context, _idProfe, _puntaje);
            post.execute(Constants.URL_UPDATE_PUNTAJE);
        } catch (JSONException e) {
            error = e;
            Toast.makeText(_context,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}
