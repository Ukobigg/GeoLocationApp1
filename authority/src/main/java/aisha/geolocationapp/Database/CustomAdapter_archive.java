package aisha.geolocationapp.Database;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import aisha.geolocationapp.R;

/**
 * Created by UkoDavid on 08/11/2017.
 */
public class CustomAdapter_archive extends RecyclerView.Adapter<CustomAdapter_archive.RecyclerViewHolder>{

    private static ItemClickCallback itemClickCallback;
    public interface ItemClickCallback{ void onItemClick(int p);}

    public void setItemClickCallback(final ItemClickCallback itemClickCallback){
        this.itemClickCallback = itemClickCallback;
    }



    public ArrayList<EmergencyModel> emergencyModelArrayList = new ArrayList<>();
    public CustomAdapter_archive(ArrayList<EmergencyModel> emergencyModelArrayList)
    {
    this.emergencyModelArrayList=emergencyModelArrayList;
    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int ViewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_archive_row,parent,false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position){


        holder.addresstag.setText("Emergency: "+emergencyModelArrayList.get(position).getEmergencytype());
        holder.emergencytag.setText("Location: "+emergencyModelArrayList.get(position).getEmergencylocation());
        holder.datetimetag.setText(emergencyModelArrayList.get(position).getDatetime());
        holder.emergencyidtag.setText(emergencyModelArrayList.get(position).getEmergencyid());
        holder.emergencystatustag.setText(emergencyModelArrayList.get(position).getEmergencystatus());
        holder.longitudetag.setText(emergencyModelArrayList.get(position).getLongitude());
        holder.latitudetag.setText(emergencyModelArrayList.get(position).getLatitude());
        holder.useridtag.setText(emergencyModelArrayList.get(position).getUserid());
        holder.icon.setImageResource(R.drawable.ic_local_pharmacy_black_36dp);

    }

    public int getItemCount(){return emergencyModelArrayList.size();}

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView addresstag, emergencytag, emergencystatustag,emergencyidtag,useridtag,datetimetag, longitudetag,latitudetag;
        private ImageView icon;
        private View container;

        RecyclerViewHolder(View view)
        {
            super(view);
            addresstag = (TextView) view.findViewById(R.id.AddressTag);
            emergencytag = (TextView) view.findViewById(R.id.EmergencyTag);
            datetimetag = (TextView) view.findViewById(R.id.DateTimeTag);
            emergencyidtag = (TextView) view.findViewById(R.id.EmergencyIdTag);
            emergencystatustag = (TextView) view.findViewById(R.id.EmergencystatusTag);
            longitudetag = (TextView) view.findViewById(R.id.LongitudeTag);
            latitudetag = (TextView) view.findViewById(R.id.LatitudeTag);
            useridtag = (TextView) view.findViewById(R.id.UserIDTag);
            icon=(ImageView) view.findViewById(R.id.Limage);
            container=view.findViewById(R.id.container_item_root);
            container.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.container_item_root){
//v,
                itemClickCallback.onItemClick(getAdapterPosition());
            }
            else {

            }
        }
    }

}

