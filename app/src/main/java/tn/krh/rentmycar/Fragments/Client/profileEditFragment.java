package tn.krh.rentmycar.Fragments.Client;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tn.krh.rentmycar.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class profileEditFragment extends Fragment {


    public profileEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_edit, container, false);
    }

}
