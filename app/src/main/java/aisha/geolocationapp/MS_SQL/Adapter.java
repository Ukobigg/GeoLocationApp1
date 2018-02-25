package aisha.geolocationapp.MS_SQL;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import aisha.geolocationapp.R;
import aisha.geolocationapp.MS_SQL.EmergencyModel;

import static android.content.ContentValues.TAG;

/**
 * Created by UkoDavid on 08/11/2017.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.RecyclerViewHolder>{
ArrayList<EmergencyModel> emergencyModelArrayList = new ArrayList<>();
    public Context context;
    public Adapter(ArrayList<EmergencyModel> emergencyModelArrayList)
    {
        this.emergencyModelArrayList=emergencyModelArrayList;
        this.context=context;
    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int ViewType)
    {
        ViewGroup view = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item_list,parent,false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position){


        final EmergencyModel emergencyModel = emergencyModelArrayList.get(position);
        holder.addresstag.setText("Location: "+emergencyModel.getEmergencylocation());
        holder.emergencytag.setText("Emergency: "+emergencyModel.getEmergencytype());
        holder.datetimetag.setText(emergencyModelArrayList.get(position).getDatetime());
        holder.emergencyidtag.setText((String.valueOf(( emergencyModelArrayList.get(position).getEmergencyid()))));
        holder.emergencystatustag.setText(emergencyModelArrayList.get(position).getEmergencystatus());
        holder.longitudetag.setText(emergencyModelArrayList.get(position).getLongitude());
        holder.latitudetag.setText(emergencyModelArrayList.get(position).getLatitude());
        holder.useridtag.setText(emergencyModelArrayList.get(position).getUserid());
        holder.icon.setImageResource(R.drawable.ic_local_pharmacy_black_36dp);

        Log.d(TAG, "onBindViewHolder");
    }

    public int getItemCount(){return emergencyModelArrayList.size();}

    class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView addresstag, emergencytag, emergencystatustag,emergencyidtag,useridtag,datetimetag,longitudetag,latitudetag;
        private ImageView icon;
        public RelativeLayout relativeLayout;
        View container;
        Context context;

        public RecyclerViewHolder(ViewGroup view)
        {
            super(view);
            container = view.findViewById(R.id.card_view);
            addresstag = (TextView) view.findViewById(R.id.AddressTag);
            emergencytag = (TextView) view.findViewById(R.id.EmergencyTag);
            datetimetag = (TextView) view.findViewById(R.id.DateTimeTag);
            emergencyidtag = (TextView) view.findViewById(R.id.EmergencyIdTag);
            emergencystatustag = (TextView) view.findViewById(R.id.EmergencystatusTag);
            longitudetag = (TextView) view.findViewById(R.id.LongitudeTag);
            latitudetag = (TextView) view.findViewById(R.id.LatitudeTag);
            useridtag = (TextView) view.findViewById(R.id.UserIDTag);
            icon=(ImageView) view.findViewById(R.id.Limage);
            relativeLayout=view.findViewById(R.id.container_item_root);
            context = view.getContext();


        }

    }
    }