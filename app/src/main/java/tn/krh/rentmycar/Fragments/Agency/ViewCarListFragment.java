package tn.krh.rentmycar.Fragments.Agency;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tn.krh.rentmycar.Adapters.Agency.ViewCarsAgencyAdapter;
import tn.krh.rentmycar.Adapters.Client.AvailableCarsAdapter;
import tn.krh.rentmycar.R;
import tn.krh.rentmycar.Retrofit.INodeJS;
import tn.krh.rentmycar.entity.Car;
import tn.krh.rentmycar.model.Client;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewCarListFragment extends Fragment {

    Context mContext;
    INodeJS myApi;
    List<Car> carsList = new ArrayList<>();
    RecyclerView CarsListAgency;
    private SharedPreferences mPreference;
    public static final String sharedPrefFile = "com.krh.app";
    public ViewCarListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_car_list, container, false);
        mPreference = getContext().getSharedPreferences(sharedPrefFile,Context.MODE_PRIVATE);
        loadClientData(view,mPreference.getInt("idAg",0));
        return view;
    }
    public void loadClientData(View view,int  id){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:1337")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        myApi = retrofit.create(INodeJS.class);
        Call<List<Car>> call = myApi.getCarsAgencyByIdList(id);
        call.enqueue(new Callback<List<Car>>() {
            @Override
            public void onResponse(Call<List<Car>> call, Response<List<Car>> response) {
                carsList = response.body();
                CarsListAgency = (RecyclerView) view.findViewById(R.id.CarListAgencyRC);
                ViewCarsAgencyAdapter CarsAdapter = new ViewCarsAgencyAdapter(mContext,carsList);
                CarsListAgency.setAdapter(CarsAdapter);
                CarsListAgency.setLayoutManager(new LinearLayoutManager(mContext));
                /*if(response.message().equals("nothing was found")){
                    Toast.makeText(getActivity(),"you have 0 car(s)",Toast.LENGTH_SHORT).show();
                }*/
            }

            @Override
            public void onFailure(Call<List<Car>> call, Throwable t) {

            }
        });
    }

}
