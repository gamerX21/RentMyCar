package tn.krh.rentmycar.Fragments.Client;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import tn.krh.rentmycar.R;
import tn.krh.rentmycar.homeApp.Client.ProfileActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment {
  private ImageView profileImg;
  private BottomNavigationView bottomNavigationView;
  private Button ViewCarsRent;
  private FrameLayout FrameCars;
  private  BlankFragment blankFragment;
  private  AvailableCarsFragment availableCarsFragment;
    public InfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setImageProfile();
        ViewCarsRent = view.findViewById(R.id.ViewCarsRent);
        availableCarsFragment = new AvailableCarsFragment();
        bottomNavigationView = getView().findViewById(R.id.main_nav);
        blankFragment = new BlankFragment();
            ViewCarsRent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(ViewCarsRent.getText().equals("show less"))
                    {
                        ViewCarsRent.setText("view available cars");
                        getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.FrameAvCars, blankFragment).commit();

                    }
                    else
                    {
                        ViewCarsRent.setText("show less");
                        getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.FrameAvCars, availableCarsFragment).commit();
                    }

                }
            });
    }
    private void setImageProfile(){
        profileImg = getView().findViewById(R.id.profileImg);
        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(getActivity(), ProfileActivity.class);
                startActivity(mainIntent);
            }
        });
    }

}
