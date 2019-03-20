package info.androidhive.masterlist.activities.authentication;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

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
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import info.androidhive.masterlist.Constants;
import info.androidhive.masterlist.R;
import info.androidhive.masterlist.authentication.AccountAdapter;

public class LoginActivity extends Activity {

    private TextView info, txtRegister;
    private LoginButton btnFacebookLogin;
    private Button btnLogin;
    private EditText txtPassword, txtUser;
    private ImageButton btnAll;

    private CallbackManager callbackManager;
    private AccessToken accessToken;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;

    private AccountManager accountManager;
    private AccountPopWindow popWindow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());

        setContentView(R.layout.activity_login);

        //btnFacebookLogin = (LoginButton) findViewById(R.id.login_button);
        info = (TextView) findViewById(R.id.info);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        txtPassword = findViewById(R.id.txtPassword);
        txtUser = findViewById(R.id.txtUser);
        txtRegister = (TextView) findViewById(R.id.text_register);

        //handleFacebookLogin();

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent, RegisterActivity.REQUEST_ACTIVITY_CODE);
            }
        });

        handleApplicationLogin();

        chooseAccount();

    }

    private void chooseAccount() {
        popWindow = new AccountPopWindow(this, new ArrayList<Account>());

        btnAll = (ImageButton) findViewById(R.id.button_all);
        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.GET_ACCOUNTS)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.GET_ACCOUNTS},
                            Constants.ACCOUNT_PERMISSION_CODE);
                } else {
                    showPop();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode==Constants.ACCOUNT_PERMISSION_CODE){
            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                showPop();
            }
        }
    }

    private void showPop() {
        Account[] accounts = accountManager.getAccountsByType(Constants.ACCOUNT_TYPE);
        popWindow.updateData(Arrays.asList(accounts));
        popWindow.showAsDropDown(btnAll);
    }


    private void handleApplicationLogin() {
        accountManager = AccountManager.get(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //登录
                String name = txtUser.getText().toString();
                String psw = txtPassword.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(v.getContext(), "NO SEAS BOLUDO", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(psw)) {
                    Toast.makeText(v.getContext(), "NO SEAS BOLUDO", Toast.LENGTH_LONG).show();
                    return;
                }
                //可以请求服务器获取token
                Account account = new Account(name, Constants.ACCOUNT_TYPE);
                AccountManagerCallback<Bundle> callback = new AccountManagerCallback<Bundle>() {
                    @Override
                    public void run(AccountManagerFuture<Bundle> future) {
                        try {
                            String token = future.getResult().getString(AccountManager.KEY_AUTHTOKEN);
                            Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
                            Account account1 = new Account(future.getResult().getString(AccountManager.KEY_ACCOUNT_NAME),
                                    future.getResult().getString(AccountManager.KEY_ACCOUNT_TYPE));
                            intent.putExtra(Constants.KEY_ACCOUNT, account1);
                            startActivity(intent);
                        } catch (OperationCanceledException | IOException | AuthenticatorException e) {
                            e.printStackTrace();
                        }
                    }
                };
                accountManager.getAuthToken(account, Constants.AUTH_TOKEN_TYPE, null, LoginActivity.this, callback, null);
            }
        });

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


    private void handleFacebookLogin() {

        callbackManager = CallbackManager.Factory.create();

        btnFacebookLogin.setReadPermissions(Arrays.asList("public_profile", "email"));
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


        btnFacebookLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        accessToken = loginResult.getAccessToken();

                        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.e("aa", "" + response.toString());

                                try {
                                    info.setText("id " + object.getString("id") + " name " + object.getString("name") +
                                            " email " + object.getString("email") + " intalled " + object.getString("installed")
                                            + "link " + object.getString("link"));
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

    public class AccountPopWindow extends PopupWindow {

        private AccountAdapter mAdapter;

        AccountPopWindow(Context context, List<Account> accounts) {
            super(context);
            //参数是MeasureSpec
            setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            ListView listView = (ListView) inflater.inflate(R.layout.pop_account, null);
            mAdapter = new AccountAdapter(LoginActivity.this, R.layout.item_account, accounts);
            listView.setAdapter(mAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Account account = ((AccountAdapter) parent.getAdapter()).getItem(position);
                    if (account != null) {
                        txtUser.setText(account.name);
                        txtPassword.setText(accountManager.getPassword(account));
                    }
                    dismiss();
                }
            });
            setContentView(listView);
            setTouchable(true);
            setOutsideTouchable(true);
            setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        }

        void updateData(List<Account> accounts) {
            mAdapter.getData().clear();
            mAdapter.getData().addAll(accounts);
        }
    }

}
