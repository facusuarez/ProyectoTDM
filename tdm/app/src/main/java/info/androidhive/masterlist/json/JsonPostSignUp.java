package info.androidhive.masterlist.json;

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

import info.androidhive.masterlist.R;
import info.androidhive.masterlist.entities.UserInfo;

public class JsonPostSignUp extends AsyncTask<String, Void, Void> {

    private UserInfo newUser;
    private Context context;

    public JsonPostSignUp(UserInfo newUser, Context context) {
        this.newUser = newUser;
        this.context = context;
    }

    @Override
    protected Void doInBackground(String... strings) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = strings[0];

        Log.i("JsonPostSignUp","Trying to create an user");
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(context, R.string.error_generic, Toast.LENGTH_SHORT).show();
                        Log.d("Error.Response", error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", newUser.getName());
                params.put("email", newUser.getEmail());
                params.put("password", newUser.getPassword());

                return params;
            }
        };
        queue.add(postRequest);
        return null;
    }
}