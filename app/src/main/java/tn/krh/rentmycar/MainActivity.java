package tn.krh.rentmycar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import tn.krh.rentmycar.Retrofit.INodeJS;
import tn.krh.rentmycar.Retrofit.RetroClient;
import tn.krh.rentmycar.homeApp.Agency.HomeAgencyActivity;
import tn.krh.rentmycar.homeApp.Client.HomeClientActivity;
import tn.krh.rentmycar.main.SignUp.SpinnerSignUpActivity;
import tn.krh.rentmycar.model.Client;

public class MainActivity extends AppCompatActivity {
    INodeJS myApi;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private SharedPreferences mPreference;
    public static final String sharedPrefFile = "com.krh.app";
    EditText email,password;
    Button SignInBtn;
    Gson gson = new Gson();
    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //GET SHARED DATA ACCESS HOME MAIN --->
        mPreference = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        if(mPreference.getString("emailAg","") != ""){
            Toast.makeText(getApplicationContext(),"you have just sign in as "+mPreference.getString("emailAg",""),Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this,HomeAgencyActivity.class));
        }
        else
        {
            //init API
            Retrofit retrofit = RetroClient.getInstance();
            myApi = retrofit.create(INodeJS.class);
            Button signUpBtn = findViewById(R.id.signUpBtnAg);
            Button signInBtn = findViewById(R.id.SignInBtn);
            email = findViewById(R.id.AdrAg);
            password = findViewById(R.id.pwdAg);
            signUpBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent startSignup = new Intent(MainActivity.this, SpinnerSignUpActivity.class);
                    startActivity(startSignup);
                }
            });
            signInBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(email.getText().toString().equals("") || password.getText().toString().equals("")){
                        Toast.makeText(MainActivity.this,"please fill your informations",Toast.LENGTH_SHORT).show();
                    }
                    else
                        loginUser(email.getText().toString(),password.getText().toString());
                }
            });
        }

    }

    private void loginUser(String email, String password) {
        compositeDisposable.add(myApi.loginUser(email,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if(s.contains("password")){
                                Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
                                if(s.contains("username")){
                                    mPreference = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
                                    SharedPreferences.Editor editor = mPreference.edit();
                                    editor.putString("emailCl",email);
                                    editor.apply();
                                   startActivity(new Intent(MainActivity.this, HomeClientActivity.class));
                                }
                                else if(s.contains("name")){
                                    mPreference = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
                                    SharedPreferences.Editor editor = mPreference.edit();
                                    editor.putString("emailAg",email);
                                    editor.apply();
                                    startActivity(new Intent(MainActivity.this, HomeAgencyActivity.class));
                                }

                        }
                        else Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
                    }
                })
        );
    }
}
