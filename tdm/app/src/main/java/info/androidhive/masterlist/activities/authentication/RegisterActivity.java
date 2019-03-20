package info.androidhive.masterlist.activities.authentication;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import info.androidhive.masterlist.Constants;
import info.androidhive.masterlist.R;

public class RegisterActivity extends AppCompatActivity {
    public static final int REQUEST_ACTIVITY_CODE = 0;

    private EditText mEditName, mEditPsw;
    private Button mBtnRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEditName = (EditText) findViewById(R.id.edit_name);
        mEditPsw = (EditText) findViewById(R.id.edit_psw);
        mBtnRegister = (Button) findViewById(R.id.button_register);

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mEditName.getText().toString();
                String psw = mEditPsw.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(v.getContext(), "NO SEAS BOLUDO", Toast.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(psw)) {
                    Toast.makeText(v.getContext(), "NO SEAS BOLUDO", Toast.LENGTH_LONG).show();
                    return;
                }

                Account account = new Account(name, Constants.ACCOUNT_TYPE);
                AccountManager am = AccountManager.get(v.getContext());
                am.addAccountExplicitly(account, psw, null);
                finish();
            }
        });
    }
}
