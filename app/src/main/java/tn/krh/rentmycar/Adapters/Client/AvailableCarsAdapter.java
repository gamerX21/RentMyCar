package tn.krh.rentmycar.Adapters.Client;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.List;

import tn.krh.rentmycar.R;
import tn.krh.rentmycar.entity.Agence;
import tn.krh.rentmycar.entity.Car;
import tn.krh.rentmycar.homeApp.Client.AvailableAgenceCarsActivity;

public class AvailableCarsAdapter extends RecyclerView.Adapter<AvailableCarsAdapter.ViewHolder> {
    private SharedPreferences mPreference;
    public static final String sharedPrefFile = "com.krh.app";
private List<Agence> agences;
private List<Car> cars;
private Context mContext;
    public AvailableCarsAdapter(Context context, List<Agence> agencess,List<Car> cars) {
        this.cars = cars;
        this.agences = agencess;
        mContext = context;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView AgenceN;
        public TextView AgenceADR;
        public Button ShowCarsAgence;
        public Button startNvButton;
        public ViewHolder(final View itemView) {
            super(itemView);
            AgenceN = itemView.findViewById(R.id.AgenceNom);
            AgenceADR = itemView.findViewById(R.id.Adresse);
            ShowCarsAgence = itemView.findViewById(R.id.ViewAgenceAvalaibleCars);
            startNvButton = itemView.findViewById(R.id.StartNavBtn);
        }
    }
    //OVERRIDE
    @NonNull
    @Override
    public AvailableCarsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View matchView = inflater.inflate(R.layout.agence_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(matchView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AvailableCarsAdapter.ViewHolder holder, int position) {
        final Agence agence = agences.get(position);
        TextView AgenceN = holder.AgenceN;
        TextView AgenceADR = holder.AgenceADR;
        Button ShowCarsAgence = holder.ShowCarsAgence;
        Button startNvButton = holder.startNvButton;

       /* holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPreference = v.getContext().getSharedPreferences(sharedPrefFile,v.getContext().MODE_PRIVATE);
                SharedPreferences.Editor Editor = mPreference.edit();
                Gson gson = new Gson();
                String carInfo = gson.toJson(cars_load);
                Editor.putString("car",carInfo);
                Editor.apply();
                Context context = v.getContext();
                Intent intent = new Intent(context, DetailsActivity.class);
                context.startActivity(intent);
            }
        });*/
        AgenceN.setText(agence.getName());
        AgenceADR.setText(agence.getAdresse());
        ShowCarsAgence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String carsJson = gson.toJson(cars);
                mPreference = v.getContext().getSharedPreferences(sharedPrefFile,v.getContext().MODE_PRIVATE);
                SharedPreferences.Editor Editor = mPreference.edit();
                Editor.putString("agence",agence.getName());
                Editor.putString("cars",carsJson);
                Editor.apply();
                Context context = v.getContext();
                Intent intent = new Intent(context, AvailableAgenceCarsActivity.class);
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return agences.size();
    }
}
