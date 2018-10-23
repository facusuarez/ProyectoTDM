package info.androidhive.tabsswipe.Activities.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import info.androidhive.tabsswipe.R;

public class LoginActivity extends Activity {
    private TextView info;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private AccessToken accessToken;
    private FacebookCallback callback;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton) findViewById(R.id.login_button);
        info = (TextView) findViewById(R.id.info);

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            }
        };
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {

            }
        };


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        accessToken = loginResult.getAccessToken();
                        Profile profile = Profile.getCurrentProfile();

                        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.e("aa", "" + response.toString());

                                try {
                                    info.setText("id " + object.getString("id") + " name " + object.getString("name") +
                                            " email " + object.getString("email") + " intalled " + object.getString("installed"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields",
                                "id,name,link,email,installed,friends{id,name,picture}");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        System.out.print("Cancel");
                        info.setText("Login attempt canceled.");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        System.out.print("Error");
                        info.setText("Login attempt failed.");
                    }

                }
        );
    }



        /*  el usuario se loguea
            el sistema busca el id en la BD
                encuentra el id - trae su perfil
                no encuetnra el id - crea un usuario con un perfil nuevo

            ver si hacemos lo del nombre de usuario aleatorio, por ahora usare el nombre de facebook o el mail o algo asi
            despues tendriamos que poner otro tipo de inicio de sesion que no sea con facebook, podria ser solo usando el mail por ejemplo

         */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
