package moin.wunder.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.Arrays;
import java.util.List;
import moin.wunder.R;
import moin.wunder.models.Placemarks;

public class PlacemarksAdapter extends RecyclerView.Adapter<PlacemarksAdapter.PlacemarksHolder> {

    List<Placemarks> carDetailsList;
    Context context;

    public PlacemarksAdapter(Placemarks[] carDetailsList, Context context) {
        this.carDetailsList = Arrays.asList(carDetailsList);
        this.context = context;
    }

    @Override
    public PlacemarksHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.placemarks_list_item,parent,false);
        PlacemarksHolder mh = new PlacemarksHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(PlacemarksHolder holder, int position) {

        holder.address.setText(context.getString(R.string.address)+" "+ carDetailsList.get(position).getAddress());
        holder.engineType.setText(context.getString(R.string.engine_type)+" "+ carDetailsList.get(position).getEngineType());
        holder.exterior.setText(context.getString(R.string.exterior)+" "+ carDetailsList.get(position).getExterior());
        holder.fuel.setText(context.getString(R.string.fuel)+" "+ carDetailsList.get(position).getFuel());
        holder.interior.setText(context.getString(R.string.interior)+" "+ carDetailsList.get(position).getInterior());
        holder.name.setText(context.getString(R.string.name)+" "+ carDetailsList.get(position).getName());
        holder.vin.setText(context.getString(R.string.vin)+" "+ carDetailsList.get(position).getVin());
    }

    @Override
    public int getItemCount() {
        return carDetailsList.size();
    }

    public class PlacemarksHolder extends RecyclerView.ViewHolder {

        private TextView address;
        private TextView exterior;
        private TextView engineType;
        private TextView fuel;
        private TextView interior;
        private TextView name;
        private TextView vin;

        public PlacemarksHolder(View v) {
            super(v);
            address = (TextView) v.findViewById(R.id.address);
            exterior = (TextView) v.findViewById(R.id.exterior);
            engineType = (TextView) v.findViewById(R.id.engineType);
            fuel = (TextView) v.findViewById(R.id.fuel);
            interior = (TextView) v.findViewById(R.id.interior);
            name = (TextView) v.findViewById(R.id.name);
            vin = (TextView) v.findViewById(R.id.vin);

        }
    }
}
