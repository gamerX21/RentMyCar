package tn.krh.rentmycar.homeApp.Agency;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tn.krh.rentmycar.Adapters.Agency.ViewCarsAgencyAdapter;
import tn.krh.rentmycar.R;
import tn.krh.rentmycar.Retrofit.INodeJS;
import tn.krh.rentmycar.entity.Agence;
import tn.krh.rentmycar.entity.Car;

public class EditCarActivity extends AppCompatActivity {
    EditText type,model,price;
    Switch DispoAg;
    Button updateCarAgBtn;
    String dispTxt;
    INodeJS myApi;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private SharedPreferences mPreference;
    public static final String sharedPrefFile = "com.krh.app";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_car);
        mPreference = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        type = findViewById(R.id.CarTypeAgUp);
        model = findViewById(R.id.ModeAgUp);
        price = findViewById(R.id.CarPriceAgUp);
        DispoAg = findViewById(R.id.DispoAgUp);
        updateCarAgBtn = findViewById(R.id.UpdateCarAgBtn);
        int car_id = mPreference.getInt("carIdAg",0);
        setDate(car_id);
    }

    private void setDate(int car_id) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:1337")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        myApi = retrofit.create(INodeJS.class);
        Call<Car> call = myApi.getCarsAgencyById(car_id);
        call.enqueue(new Callback<Car>() {
            @Override
            public void onResponse(Call<Car> call, Response<Car> response) {
                Car car = response.body();
                type.setText(car.getType());
                model.setText(car.getModele());
                //int agence_id = mPreference.getInt("idAg",0);
                int car_id = mPreference.getInt("carIdAg",0);
                price.setText(Integer.toString(car.getPrix()));
                if(car.getDisponibilite().equals("oui")) DispoAg.setChecked(true);
                else DispoAg.setChecked(false);
               updateCarAgBtn.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       if(type.getText().equals("") || model.getText().equals("") || price.getText().equals("")){
                           Toast.makeText(getApplicationContext(),"please fill car information to update",Toast.LENGTH_SHORT).show();
                       }else {
                           String dispoC= "";
                           if(DispoAg.isChecked() == true) dispoC="oui";
                           else dispoC="non";
                           int priceCv = Integer.parseInt(price.getText().toString());
                           Retrofit.Builder builder = new Retrofit.Builder()
                                   .baseUrl("http://10.0.2.2:1337")
                                   .addConverterFactory(GsonConverterFactory.create());
                           Retrofit retrofit = builder.build();
                           myApi = retrofit.create(INodeJS.class);
                           Call<Car> call = myApi.UpdateCar(car_id,type.getText().toString(),model.getText().toString(),priceCv,dispoC);
                          call.enqueue(new Callback<Car>() {
                              @Override
                              public void onResponse(Call<Car> call, Response<Car> response) {
                                  Toast.makeText(getApplicationContext(),response.message(),Toast.LENGTH_SHORT).show();
                              }

                              @Override
                              public void onFailure(Call<Car> call, Throwable t) {
                                  Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                              }
                          });

                       }
                   }
               });

            }

            @Override
            public void onFailure(Call<Car> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"update request failed",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
