package info.androidhive.masterlist.activities;

import info.androidhive.masterlist.Constants;
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
import android.widget.TextView;

public class UserFragment extends Fragment {

    private Button btnLogin, btnLogout;
    private TextView txtUserLogged;

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

        btnLogout = getView().findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    private void logout() {
        //TODO cerrar sesion
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        super.onCreate(savedInstanceState);

        txtUserLogged = getView().findViewById(R.id.txtUserLogged);

        createBtnLoginClickListener();
        populateElements();
    }

    private void populateElements() {
        AccountManager am = AccountManager.get(getContext());
        Account[] accounts = am.getAccountsByType(Constants.ACCOUNT_TYPE);
btnLogout.setVisibility(View.INVISIBLE);
        if (accounts.length == 0) {
            btnLogin.setVisibility(View.VISIBLE);
            txtUserLogged.setText("");
        } else {
        //    btnLogin.setVisibility(View.INVISIBLE);
            txtUserLogged.setText(accounts[0].name); //TODO que pasa cuando hay mas de una?
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
