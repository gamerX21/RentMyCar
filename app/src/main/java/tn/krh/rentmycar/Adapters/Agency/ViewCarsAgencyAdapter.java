package tn.krh.rentmycar.Adapters.Agency;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tn.krh.rentmycar.Adapters.Client.AgenceAVCARSRcAdapter;
import tn.krh.rentmycar.R;
import tn.krh.rentmycar.Retrofit.INodeJS;
import tn.krh.rentmycar.entity.Car;
import tn.krh.rentmycar.homeApp.Agency.EditCarActivity;

public class ViewCarsAgencyAdapter extends RecyclerView.Adapter<ViewCarsAgencyAdapter.ViewHolder> {
    private List<Car> cars;
    Context mContext;
    INodeJS myApi;
    private SharedPreferences mPreference;
    public static final String sharedPrefFile = "com.krh.app";
    public ViewCarsAgencyAdapter(Context context, List<Car> carss) {
        this.cars = carss;
        mContext = context;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView type;
        public TextView model;
        public Switch Dispo;
        public TextView createdAt;
        public TextView LastUpdate;
        public  TextView prix;
        public Button EditCar;
        public  Button DeleteCar;
        public  SharedPreferences  mPreference;
        public ViewHolder(final View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.TypeCarAgM);
            model = itemView.findViewById(R.id.ModelCarAgM);
            Dispo = itemView.findViewById(R.id.DispoAgM);
            prix = itemView.findViewById(R.id.PriceCarAgM);
            createdAt = itemView.findViewById(R.id.CreatedAtCarAgM);
            LastUpdate = itemView.findViewById(R.id.LastUpdateCarAgM);
            EditCar = itemView.findViewById(R.id.EditCarAgM);
            DeleteCar = itemView.findViewById(R.id.DeleteCarAgM);
         }
    }
    @NonNull
    @Override
    public ViewCarsAgencyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View matchView = inflater.inflate(R.layout.view_cars_agency_manag, parent, false);
        ViewCarsAgencyAdapter.ViewHolder viewHolder = new ViewCarsAgencyAdapter.ViewHolder(matchView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewCarsAgencyAdapter.ViewHolder holder, int position) {
        final Car car = cars.get(position);
        TextView type = holder.type;
        TextView model = holder.model;
        Switch Dispo = holder.Dispo;
        TextView prix = holder.prix;
        TextView createdAt = holder.createdAt;
        TextView LastUpdate = holder.LastUpdate;
        Button EditCar = holder.EditCar;
        Button DeleteCar = holder.DeleteCar;
        final SharedPreferences[] mPreference = {holder.mPreference};
        //SET DATA TO ITEMS
        type.setText(car.getType());
        model.setText(car.getModele());
        Dispo.setText(car.getDisponibilite());
        if(car.getDisponibilite().equals("oui")){
            Dispo.setChecked(true);
        }else Dispo.setChecked(false);
        prix.setText(Integer.toString(car.getPrix()) + " DT");
        createdAt.setText("28/11/2019");
        LastUpdate.setText("28/11/2019");
        EditCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPreference[0] = v.getContext().getSharedPreferences(sharedPrefFile,Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = mPreference[0].edit();
                editor.putInt("carIdAg",car.getId());
                editor.apply();
                Context context = v.getContext();
                context.startActivity(new Intent(context, EditCarActivity.class));
            }
        });
        DeleteCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                mPreference[0] = v.getContext().getSharedPreferences(sharedPrefFile,Context.MODE_PRIVATE);
                int car_id = mPreference[0].getInt("carIdAg",0);
                Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl("http://10.0.2.2:1337")
                        .addConverterFactory(GsonConverterFactory.create());
                Retrofit retrofit = builder.build();
                myApi = retrofit.create(INodeJS.class);
                Call<Car> call = myApi.deleteCar(car_id);
                call.enqueue(new Callback<Car>() {
                    @Override
                    public void onResponse(Call<Car> call, Response<Car> response) {
                        Toast.makeText(v.getContext(),response.message(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Car> call, Throwable t) {
                        Toast.makeText(v.getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                });
            }

        });

    }

    @Override
    public int getItemCount() {
        return cars.size();
    }
}
