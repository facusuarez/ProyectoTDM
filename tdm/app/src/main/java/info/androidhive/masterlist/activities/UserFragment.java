package info.androidhive.masterlist.activities;

import info.androidhive.masterlist.activities.authentication.LoginActivity;
import info.androidhive.masterlist.R;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class UserFragment extends Fragment {

    private Button btnLogin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    private void createBtnLoginClickListener() {
        btnLogin = getView().findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        super.onCreate(savedInstanceState);

        createBtnLoginClickListener();

        AccountManager am = AccountManager.get(getContext());

        Account[] accounts = am.getAccountsByType("com.google");

    }

    @Override
    public void onResume() {
        super.onResume();
        AccountManager am = AccountManager.get(getContext());


        Account[] accounts = am.getAccountsByType("com.google");

    }
}
