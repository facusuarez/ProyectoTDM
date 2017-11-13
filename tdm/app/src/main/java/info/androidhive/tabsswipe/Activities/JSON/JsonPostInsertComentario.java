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

import java.util.HashMap;
import java.util.Map;

import info.androidhive.tabsswipe.Activities.Entities.Comentario;

/**
 * Created by USUARIO on 13/11/2017.
 */

public class JsonPostInsertComentario extends AsyncTask<String, Void, Void>  {
    private Context _context;
    private Comentario _comentario;

    public JsonPostInsertComentario(Context context, Comentario comentario) {
        _context = context;
        _comentario=comentario;
    }


    @Override
    protected Void doInBackground(String... strings) {
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
                        Log.d("Error.Response", error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_profesor", _comentario.getId_profesor() + "");
                params.put("puntos", _comentario.getPuntaje() + "");
                params.put("id_usuario",_comentario.getId_usuario()+"");
                params.put("descripcion",_comentario.getDescripcion());

                return params;
            }
        };
        queue.add(postRequest);
        return null;
    }
}
