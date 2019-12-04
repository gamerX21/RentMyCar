package tn.krh.rentmycar.Fragments.Client;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import tn.krh.rentmycar.Adapters.Client.AvailableCarsAdapter;
import tn.krh.rentmycar.R;
import tn.krh.rentmycar.entity.Agence;
import tn.krh.rentmycar.entity.Car;

/**
 * A simple {@link Fragment} subclass.
 */
public class AvailableCarsFragment extends Fragment {
    public static List<Agence> AgenceList = new ArrayList<Agence>();
    public  static List<Car> CarList = new ArrayList<Car>();
    Context mContext;
    public AvailableCarsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        CarList.add(new Car("BMW","E92",120,1,"25-11-2019","27-11-2019","oui"));
        CarList.add(new Car("MERCEDES","CLA 180",150,1,"25-11-2019","27-11-2019","oui"));
        CarList.add(new Car("VOLKSWAGEN","GOLF 7",120,1,"25-11-2019","27-11-2019","oui"));
        AgenceList.add(new Agence("ELWAKIL","cité elghazela",CarList));
        View view = inflater.inflate(R.layout.fragment_available_cars, container, false);
        RecyclerView CarsListRV = (RecyclerView) view.findViewById(R.id.RecyclerViewCars);
        AvailableCarsAdapter CarsAdapter = new AvailableCarsAdapter(mContext,AgenceList,CarList);
        CarsListRV.setAdapter(CarsAdapter);
        CarsListRV.setLayoutManager(new LinearLayoutManager(mContext));
        return view;
    }

}
