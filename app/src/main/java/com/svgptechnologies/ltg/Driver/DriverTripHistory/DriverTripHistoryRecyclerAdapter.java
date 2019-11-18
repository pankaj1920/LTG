package com.svgptechnologies.ltg.Driver.DriverTripHistory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.svgptechnologies.ltg.Json.DriverJson.DriverTripHistory.DriverTripHistoryData;
import com.svgptechnologies.ltg.R;

import java.util.List;

public class DriverTripHistoryRecyclerAdapter extends RecyclerView.Adapter<DriverTripHistoryRecyclerAdapter.DTH_VH> {

    List<DriverTripHistoryData> tripHistoryData;
    DriverTipHistoryActivity driverTipHistoryActivity;

    public DriverTripHistoryRecyclerAdapter ( List<DriverTripHistoryData> tripHistoryData, DriverTipHistoryActivity driverTipHistoryActivity ) {
        this.tripHistoryData = tripHistoryData;
        this.driverTipHistoryActivity = driverTipHistoryActivity;
    }

    @NonNull
    @Override
    public DTH_VH onCreateViewHolder ( @NonNull ViewGroup parent, int viewType ) {

        View view = LayoutInflater.from ( parent.getContext ( ) ).inflate ( R.layout.driver_trip_history_list, parent, false );
        return new DTH_VH ( view );
    }

    @Override
    public void onBindViewHolder ( @NonNull DTH_VH holder, int position ) {

        DriverTripHistoryData historyData = tripHistoryData.get ( position );
        holder.DTdriverAddress.setText ( historyData.getDriver_address ( ) );
        holder.DTuserAddress.setText ( historyData.getUser_address ( ) );
        holder.DTuserName.setText ( historyData.getUser_name ( ) );

        if ( historyData.getUser_image ( ).isEmpty ( ) ) {

            holder.driverTripImage.setImageResource ( R.drawable.fb_profile_logo );
        } else {

            Picasso.with ( holder.DTdriverAddress.getContext ( ) ).load ( historyData.getDriver_image ( ) )
                    .into ( holder.driverTripImage );

        }
    }

    @Override
    public int getItemCount ( ) {
        if ( tripHistoryData != null ) {

            return tripHistoryData.size ( );
        } else {
            return getItemCount ();
        }

    }


    class DTH_VH extends RecyclerView.ViewHolder {

        ImageView driverTripImage;
        TextView DTdriverAddress, DTuserAddress, DTuserName;

        public DTH_VH ( @NonNull View itemView ) {
            super ( itemView );

            driverTripImage = itemView.findViewById ( R.id.driverTripImage );

            DTdriverAddress = itemView.findViewById ( R.id.DTdriverAddress );

            DTuserAddress = itemView.findViewById ( R.id.DTuserAddress );

            DTuserName = itemView.findViewById ( R.id.DTuserName );

        }
    }
}
