package tn.krh.rentmycar.Adapters.Client;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tn.krh.rentmycar.R;
import tn.krh.rentmycar.entity.Car;

public class AgenceAVCARSRcAdapter extends RecyclerView.Adapter<AgenceAVCARSRcAdapter.ViewHolder>  {
    private List<Car> cars;
    Context mContext;
    public AgenceAVCARSRcAdapter(Context context, List<Car> carss) {
        this.cars = carss;
        mContext = context;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView type;
        public TextView model;
        public TextView Dispo;
        public  TextView date_db;
        public  TextView date_fn;
        public  TextView prix;
        public ViewHolder(final View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.Type);
            model = itemView.findViewById(R.id.Adresse);
            Dispo = itemView.findViewById(R.id.dispo);
            date_db = itemView.findViewById(R.id.date_db);
            date_fn = itemView.findViewById(R.id.date_fn);
            prix = itemView.findViewById(R.id.Prix);
        }
    }
    @NonNull
    @Override
    public AgenceAVCARSRcAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View matchView = inflater.inflate(R.layout.car_item, parent, false);
        AgenceAVCARSRcAdapter.ViewHolder viewHolder = new AgenceAVCARSRcAdapter.ViewHolder(matchView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Car car = cars.get(position);
        TextView type = holder.type;
        TextView model = holder.model;
        TextView Dispo = holder.Dispo;
        TextView date_db = holder.date_db;
        TextView date_fn = holder.date_fn;
        TextView prix = holder.prix;
        type.setText(car.getType());
        model.setText(car.getModele());
        Dispo.setText(car.getDisponibilite());
        date_db.setText(car.getDate_debut());
        date_fn.setText(car.getDate_fin());
        prix.setText(Integer.toString(car.getPrix()) + " DT");

    }

    @Override
    public int getItemCount() {
        return cars.size();
    }
}
