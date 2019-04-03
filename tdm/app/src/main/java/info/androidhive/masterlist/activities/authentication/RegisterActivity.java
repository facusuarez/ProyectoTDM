package info.androidhive.masterlist.activities.authentication;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import info.androidhive.masterlist.Constants;
import info.androidhive.masterlist.R;
import info.androidhive.masterlist.entities.UserInfo;
import info.androidhive.masterlist.json.JsonPostSignUp;

public class RegisterActivity extends Activity {
    public static final int REQUEST_ACTIVITY_CODE = 0;

    private EditText mEditName, mEditPsw, mEmail;
    private Button mBtnRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEditName = findViewById(R.id.edit_name);
        mEditPsw = findViewById(R.id.edit_psw);
        mBtnRegister = findViewById(R.id.button_register);
        mEmail = findViewById(R.id.edit_email);

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mEditName.getText().toString();
                String psw = mEditPsw.getText().toString();
                String email = mEmail.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(v.getContext(), "NO SEAS BOLUDO", Toast.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(psw)) {
                    Toast.makeText(v.getContext(), "NO SEAS BOLUDO", Toast.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(v.getContext(), "NO SEAS BOLUDO", Toast.LENGTH_LONG).show();
                    return;
                }

                createUser(new UserInfo(name, 0L, email, psw));
            }
        });
    }

    private void createUser(UserInfo newUser) {
        JsonPostSignUp post = new JsonPostSignUp(newUser, this);
        post.execute(Constants.URL_CREATE_USER);

        Account account = new Account(newUser.getName(), Constants.ACCOUNT_TYPE);
        AccountManager am = AccountManager.get(this);
        am.addAccountExplicitly(account, newUser.getPassword(), null);
        finish();
    }


}
