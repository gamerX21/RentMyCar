package tn.krh.rentmycar.Fragments.Agency;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import tn.krh.rentmycar.R;
import tn.krh.rentmycar.homeApp.Agency.AddNewActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarsManageFragment extends Fragment {
  ViewCarListFragment viewCarListFragment;
  Button AddNewCar,ViewCarsListBtn;
    public CarsManageFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cars_manage, container, false);
        AddNewCar = view.findViewById(R.id.AddNewCar);
        ViewCarsListBtn = view.findViewById(R.id.ViewCarsListBtn);
        viewCarListFragment = new ViewCarListFragment();
        AddNewCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showFragment(viewCarListFragment);
                startActivity(new Intent(getActivity(), AddNewActivity.class));
            }
        });
        ViewCarsListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(viewCarListFragment);

            }
        });
        return  view;
    }
    private void showFragment(Fragment fragment) {
        getFragmentManager().beginTransaction().replace(R.id.CarsManageFrag_frame_container, fragment).commit();
    }
}
