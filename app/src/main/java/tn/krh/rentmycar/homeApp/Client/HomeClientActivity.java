package tn.krh.rentmycar.homeApp.Client;
import android.graphics.BitmapFactory;

import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import android.widget.Toast;

import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.krh.rentmycar.Fragments.Client.InfoFragment;
import tn.krh.rentmycar.Fragments.Client.ShareFragment;
import tn.krh.rentmycar.R;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;

/**
 * The most basic example of adding a map to an activity.
 */
public class HomeClientActivity extends AppCompatActivity implements OnMapReadyCallback, PermissionsListener, MapboxMap.OnMapClickListener{
    private BottomNavigationView MainNav;
    private FrameLayout infoFrame;
    private InfoFragment infoFragment;
    private ShareFragment shareFragment;
    private MapView mapView;
    private FrameLayout AvailableCars;
    private PermissionsManager permissionsManager;
    private MapboxMap mapboxMap;
    private  Button StartNav;
    private DirectionsRoute currentRoute;
    private LocationComponent locationComponent;
    private NavigationMapRoute navigationMapRoute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, "pk.eyJ1Ijoic3Jpbml2YXM1NjhnaiIsImEiOiJjazM0aGxlMWQxMWM1M3FxdTE5dWs0eXkxIn0.xkgTQTDdetiUscmbN8_Tqg");
        setContentView(R.layout.activity_home);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        //MAIN NAVIGATION
        MainNav = findViewById(R.id.main_nav);
        infoFrame = findViewById(R.id.info_frame);
        StartNav = findViewById(R.id.StartNav);
        infoFragment = new InfoFragment();
        shareFragment = new ShareFragment();
        setFragment(infoFragment);
        MainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.rent :
                        setFragment(infoFragment);
                        return true;
                    case R.id.share :
                        setFragment(shareFragment);
                        return true;
                    default:
                        return  false;

                }
            }
        });
        //start navigation
        StartNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean simulateRoute = true;
                NavigationLauncherOptions options = NavigationLauncherOptions.builder()
                        .directionsRoute(currentRoute)
                        .shouldSimulateRoute(true)
                        .build();
                NavigationLauncher.startNavigation(HomeClientActivity.this,options);

            }
        });
        //FRAME LAYOUT
        AvailableCars = findViewById(R.id.FrameAvCars);
    }


    public void setFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.info_frame, fragment).commit();
    }

    //life cycle
    @Override
    @SuppressWarnings( {"MissingPermission"})
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
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
            finish();
        }
    }


    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        HomeClientActivity.this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                enableLocationComponent(style);
                MarkerOptions options = new MarkerOptions();
                options.setTitle("agence ELWAKIL");
                options.position(new LatLng(36.8682605,10.183972));
                mapboxMap.addMarker(options);
                options.setTitle("agence ELAMEN");
                options.position(new LatLng(36.813361,10.168917));
                mapboxMap.addMarker(options);
                addDestinationIconLayer(style);
                mapboxMap.addOnMapClickListener(HomeClientActivity.this);
            }
        });
    }
    private void addDestinationIconLayer(Style style){
       style.addImage("destination-icon-id", BitmapFactory.decodeResource(this.getResources(),R.drawable.mapbox_marker_icon_default));
       GeoJsonSource geoJsonSource = new GeoJsonSource("destination-source-id");
       style.addSource(geoJsonSource);
       SymbolLayer destinationSymbolLayer = new SymbolLayer("destination-symbol-layer-id","destination-source-id");
       destinationSymbolLayer.withProperties(iconImage("destination-icon-id"),iconAllowOverlap(true),iconIgnorePlacement(true));
       style.addLayer(destinationSymbolLayer);
    }

    @Override
    public boolean onMapClick(@NonNull LatLng point) {
        Point destinationPoint = Point.fromLngLat(point.getLongitude(),point.getLatitude());
        Point originPoint = Point.fromLngLat(locationComponent.getLastKnownLocation().getLongitude(),locationComponent.getLastKnownLocation().getLatitude());
        GeoJsonSource source = mapboxMap.getStyle().getSourceAs("destination-source-id");
        if(source != null){
            source.setGeoJson(Feature.fromGeometry(destinationPoint));
        }
        getRoute(originPoint,destinationPoint);
        return  true;
    }

    private void getRoute(Point origin, Point destination) {
        NavigationRoute.builder(this)
                .accessToken(Mapbox.getAccessToken())
                .origin(origin)
                .destination(destination)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                        currentRoute = response.body().routes().get(0);

                        if (navigationMapRoute != null) {
                            navigationMapRoute.removeRoute();
                        } else {
                            navigationMapRoute = new NavigationMapRoute(null, mapView, mapboxMap, R.style.NavigationMapRoute);
                        }
                        navigationMapRoute.addRoute(currentRoute);
                    }

                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {

                    }
                });
    }

}
