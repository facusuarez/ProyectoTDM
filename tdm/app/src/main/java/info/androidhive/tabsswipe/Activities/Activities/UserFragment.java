package info.androidhive.tabsswipe.Activities.Activities;

import info.androidhive.tabsswipe.Activities.Activities.Ranking.DatosProfesorActivity;
import info.androidhive.tabsswipe.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class UserFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_user, container, false);

        Intent i = new Intent(getActivity(), LoginActivity.class);
        startActivity(i);


        return rootView;
    }

}
