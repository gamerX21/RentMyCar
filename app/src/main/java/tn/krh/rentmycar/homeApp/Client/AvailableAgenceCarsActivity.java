package tn.krh.rentmycar.homeApp.Client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import tn.krh.rentmycar.Adapters.Client.AgenceAVCARSRcAdapter;
import tn.krh.rentmycar.R;
import tn.krh.rentmycar.entity.Car;

public class AvailableAgenceCarsActivity extends AppCompatActivity {
    private RecyclerView AgenceCars;
    private SharedPreferences mPreference;
    public static final String sharedPrefFile = "com.krh.app";
    private  List<Car> Cars = new ArrayList<>();
    private  List<Car> AC = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mPreference = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_agence_cars);
        AgenceCars = findViewById(R.id.AvCarsRC);
        Cars = getData();
        for (int i=0;i < Cars.size();i++){
           if(Integer.toString(Cars.get(i).getAgence_id()).equals(mPreference.getString("agence",""))){
               AC = Cars;
           }
        }
        AgenceAVCARSRcAdapter CarsAdapter = new AgenceAVCARSRcAdapter(getApplicationContext(),AC);
        AgenceCars.setAdapter(CarsAdapter);
        AgenceCars.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    }
    public List<Car> getData(){
        List<Car> load = new ArrayList<Car>();
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Car>>(){}.getType();
        load = gson.fromJson(mPreference.getString("cars",""),type);
        return load;
    }
}
