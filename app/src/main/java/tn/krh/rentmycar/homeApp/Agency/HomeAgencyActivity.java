package tn.krh.rentmycar.homeApp.Agency;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tn.krh.rentmycar.Fragments.Agency.CarsManageFragment;
import tn.krh.rentmycar.Fragments.Agency.profile.ProfileFragment;
import tn.krh.rentmycar.Fragments.Client.BlankFragment;
import tn.krh.rentmycar.MainActivity;
import tn.krh.rentmycar.R;
import tn.krh.rentmycar.Retrofit.INodeJS;
import tn.krh.rentmycar.Retrofit.RetroClient;
import tn.krh.rentmycar.entity.Agence;
import tn.krh.rentmycar.model.Client;

public class HomeAgencyActivity extends AppCompatActivity  implements OnMapReadyCallback, PermissionsListener {
    public SharedPreferences mPreference;
    public static final String sharedPrefFile = "com.krh.app";
    private BottomNavigationView MainNav;
    LinearLayout TopSectionDashboardLinear;
    Button CarsMangBtn,PositionMangBtn;
    ProfileFragment profileFragment;
    CarsManageFragment carsManageFragment;
    BlankFragment blankFragment;
    private MapView mapView;
    private MapboxMap mapboxMap;
    private PermissionsManager permissionsManager;
    private LocationComponent locationComponent;
    private Client client;
    TextView UserIntriTxtMain;
    INodeJS myApi;
    RetroClient retroClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, "sk.eyJ1Ijoic3Jpbml2YXM1NjhnaiIsImEiOiJjazNodTA3bzIwMWEyM25saHB5dXpycnlrIn0.VSEa_2iU-vJtW0kTMZ0w-w");
        setContentView(R.layout.activity_home_agency);
        mapView = findViewById(R.id.mapViewAgencyMain);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        CarsMangBtn = findViewById(R.id.CarsManagementBtn);
        PositionMangBtn = findViewById(R.id.PositionManagementBtn);
        carsManageFragment = new CarsManageFragment();
        blankFragment = new BlankFragment();
        profileFragment = new ProfileFragment();
        UserIntriTxtMain = findViewById(R.id.UserTxtMainAgency);
        MainNav = findViewById(R.id.main_nav_agency);
        TopSectionDashboardLinear = findViewById(R.id.TopSectionDashboardLinear);
        MainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.dashboard:
                        TopSectionDashboardLinear.setVisibility(View.VISIBLE);
                        showFragment(blankFragment);
                        return true;
                    case R.id.logout :
                        mPreference = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
                        mPreference.edit().clear().commit();
                        startActivity(new Intent(HomeAgencyActivity.this, MainActivity.class));
                        return true;
                    case R.id.profile :
                        TopSectionDashboardLinear.setVisibility(View.GONE);
                        showFragment(profileFragment);
                        return true;
                    default:
                        return  false;

                }
            }
        });
        CarsMangBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CarsMangBtn.getText().equals("show less"))
                {
                    CarsMangBtn.setText("cars management");
                    showFragment(blankFragment);
                }
                else
                {
                    CarsMangBtn.setText("show less");
                    showFragment(carsManageFragment);
                }

            }
        });
        loadClientData();
    }
    public void loadClientData(){
        mPreference = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        String email = mPreference.getString("emailAg","");
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:1337")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        myApi = retrofit.create(INodeJS.class);
        Call<Agence> call = myApi.getAgency(email);
        call.enqueue(new Callback<Agence>() {
            @Override
            public void onResponse(Call<Agence> call, Response<Agence> response) {
                Agence agence = response.body();
                UserIntriTxtMain.setText("Welcome back "+agence.getName());
                SharedPreferences.Editor editor = mPreference.edit();
                editor.putInt("idAg",agence.getId());
                editor.apply();
            }

            @Override
            public void onFailure(Call<Agence> call, Throwable t) {
               Toast.makeText(getApplicationContext(),"request failer",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.agency_frame_container, fragment).commit();
    }
    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        HomeAgencyActivity.this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                enableLocationComponent(style);
            }
        });
    }
    //life cycle
    @Override
    @SuppressWarnings( {"MissingPermission"})
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    //CURRENT USER LOCATION
    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            locationComponent = mapboxMap.getLocationComponent();
            locationComponent.activateLocationComponent(LocationComponentActivationOptions.builder(this, loadedMapStyle).build());
            locationComponent.setLocationComponentEnabled(true);
            locationComponent.setCameraMode(CameraMode.TRACKING);
            locationComponent.setRenderMode(RenderMode.COMPASS);
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this,"user_location_permission_explanation", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            mapboxMap.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    enableLocationComponent(style);
                }
            });
        } else {
            Toast.makeText(this,"user_location_permission_not_granted", Toast.LENGTH_LONG).show();
        }
        finish();
    }
}
