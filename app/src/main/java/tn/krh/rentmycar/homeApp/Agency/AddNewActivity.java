package tn.krh.rentmycar.homeApp.Agency;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import tn.krh.rentmycar.Adapters.Agency.Spinner.ModelSpinnerAdapter;
import tn.krh.rentmycar.Adapters.Agency.Spinner.TypeSpinnerAdapter;
import tn.krh.rentmycar.Adapters.SignUp.SpinnerSignUpAdapter;
import tn.krh.rentmycar.Fragments.SignUp.AgencyFragmentSignUp;
import tn.krh.rentmycar.Fragments.SignUp.ClientFragmentSignUp;
import tn.krh.rentmycar.R;
import tn.krh.rentmycar.Retrofit.INodeJS;
import tn.krh.rentmycar.Retrofit.RetroClient;
import tn.krh.rentmycar.homeApp.Agency.Data.ListsData;
import tn.krh.rentmycar.model.SpinnerItem;

public class AddNewActivity extends AppCompatActivity {
    EditText type,model,price;
    Switch DispoAg;
    Button AddCarAgBtn;
    String dispTxt;
    Spinner typeAg,modelAg;
    private SharedPreferences mPreference;
    public static final String sharedPrefFile = "com.krh.app";
    ArrayList<SpinnerItem> TypeList = new ArrayList<>();
    ArrayList<SpinnerItem> ModelList = new ArrayList<>();
    INodeJS myApi;
    Context mContext;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);
        //SPINNER MODEL AND TYPE:BEGIN

        TypeList.add(new SpinnerItem("Select",R.drawable.car));
        TypeList.add(new SpinnerItem("volkswagen",R.drawable.volkswagen));
        TypeList.add(new SpinnerItem("bmw",R.drawable.bmw));
        TypeList.add(new SpinnerItem("mercedes",R.drawable.mercedes));
        TypeList.add(new SpinnerItem("fiat",R.drawable.fiat));
        TypeList.add(new SpinnerItem("hyundai",R.drawable.hyundai));
        TypeList.add(new SpinnerItem("audi",R.drawable.audi));
        TypeList.add(new SpinnerItem("kia",R.drawable.kia));
        TypeList.add(new SpinnerItem("renault",R.drawable.renault));
        TypeSpinnerAdapter TypeAdapter = new TypeSpinnerAdapter(this,R.layout.spinner_layout,R.id.SpinnerTxt,TypeList);
        typeAg = findViewById(R.id.CarTypeAg);
        modelAg = findViewById(R.id.ModelAg);
        typeAg.setAdapter(TypeAdapter);
        ModelList = ListsData.initList();
        typeAg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerItem SelectedType = (SpinnerItem)  parent.getItemAtPosition(position);
                String type = SelectedType.getName();
                if(position > 0){
                    mPreference = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
                    SharedPreferences.Editor editor = mPreference.edit();
                    editor.putString("SelectedType",type);
                    editor.apply();
                    if(type.equals("volkswagen")){
                        ModelList.clear();
                        ModelList = ListsData.volkswagenList();
                    }
                    else if(type.equals("bmw")){
                        ModelList.clear();
                        ModelList = ListsData.bmwList();
                    }
                    else if(type.equals("mercedes")){
                        ModelList.clear();
                        ModelList = ListsData.mercedesList();
                    }
                    else if(type.equals("fiat")){
                        ModelList.clear();
                        ModelList = ListsData.fiatList();
                    }
                    else if(type.equals("hyundai")){
                        ModelList.clear();
                        ModelList = ListsData.hyundaiList();
                    }
                    else if(type.equals("audi")){
                        ModelList.clear();
                        ModelList = ListsData.audiList();
                    }
                    else if(type.equals("kia")){
                        ModelList.clear();
                        ModelList = ListsData.kiaList();
                    }
                    else if(type.equals("renault")){
                        ModelList.clear();
                        ModelList = ListsData.renaultList();
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ModelSpinnerAdapter ModelAdapter = new ModelSpinnerAdapter(this,R.layout.spinner_layout,R.id.SpinnerTxt,ModelList);
        modelAg.setAdapter(ModelAdapter);
        modelAg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerItem SelectedModel = (SpinnerItem)  parent.getItemAtPosition(position);
                String model = SelectedModel.getName();
                if(position > 0){
                    mPreference = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
                    SharedPreferences.Editor editor = mPreference.edit();
                    editor.putString("SelectedModel",model);
                    editor.apply();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //SPINNER MODEL AND TYPE:END
        //init API
        mPreference = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        Retrofit retrofit = RetroClient.getInstance();
        myApi = retrofit.create(INodeJS.class);
        price = findViewById(R.id.CarPriceAg);
        DispoAg = findViewById(R.id.DispoAg);
        AddCarAgBtn = findViewById(R.id.AddCarAgBtn);
        mPreference = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        int id = mPreference.getInt("idAg",0);
        dispTxt = "";
        if(DispoAg.isChecked() == true) dispTxt="oui";
        else dispTxt="non";
        AddCarAgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCarAgence(mPreference.getString("SelectedType",""),mPreference.getString("SelectedModel",""),price.getText().toString(),dispTxt,id);
            }
        });
    }

    private void AddCarAgence(String type, String model, String price, String dispo, int id) {
        int priceC = Integer.parseInt(price);
        compositeDisposable.add(myApi.AddCar(type,model,priceC,dispo,id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {

                        Toast.makeText(getApplicationContext(),""+s,Toast.LENGTH_SHORT).show();
                    }
                })
        );
    }
}
