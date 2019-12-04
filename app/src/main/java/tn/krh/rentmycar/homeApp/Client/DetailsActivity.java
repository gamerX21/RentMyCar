package tn.krh.rentmycar.homeApp.Client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import tn.krh.rentmycar.R;
import tn.krh.rentmycar.entity.Car;

public class DetailsActivity extends AppCompatActivity {
    private SharedPreferences mPreference;
    public static final String sharedPrefFile = "com.krh.app";
    private  List<Car> get_Car = new ArrayList<Car>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        DetailsActivity obj = new DetailsActivity();
        get_Car = getData();
    }
    public List<Car> getData(){
        List<Car> load = new ArrayList<Car>();
        mPreference = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Car>>(){}.getType();
        load = gson.fromJson(mPreference.getString("car",""),type);
        return load;
    }
}
