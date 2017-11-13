package info.androidhive.tabsswipe.Activities.JSON;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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
import java.util.HashMap;
import java.util.Map;

/**
 * Created by USUARIO on 12/11/2017.
 */

public class JsonPostUpdatePuntaje extends AsyncTask<String, Void, Void> {
    private Context _context;
    private int _idProfe;
    private float _puntaje;
    private Exception error;

    public JsonPostUpdatePuntaje(Context context, int idProfe) {
        _context = context;
        _idProfe = idProfe;
    }

    @Override
    protected Void doInBackground(String... strings) {

        String outputData;
        JSONObject jsonObject=null;
        try {
            HttpClient client = new DefaultHttpClient();
            HttpUriRequest request = new HttpGet(strings[1]+_idProfe);
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
                    jsonObject= new JSONObject(outputData);
                } catch (JSONException e) {
                    error = e;
                }
            } else {
                throw new Exception("Status code != 200: " + statusCode);
            }
        } catch (Exception e) {
            error = e;
        }

        try {
            JSONArray jsonArray= jsonObject.getJSONArray("Table");
            _puntaje = (float)jsonArray.getJSONObject(0).getDouble("Column1");
        } catch (JSONException e) {
            error=e;
        }

        RequestQueue queue;
        queue = Volley.newRequestQueue(_context);
        String url = strings[0];

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Toast.makeText(_context, response, Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(_context, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", _idProfe + "");
                params.put("puntaje", _puntaje + "");

                return params;
            }
        };
        queue.add(postRequest);
        return null;
    }
}
