package com.svgptechnologies.ltg.User.SeachDriver;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.svgptechnologies.ltg.Json.UserJson.SearchDriver.SearchDriverData;
import com.svgptechnologies.ltg.R;

import java.util.List;


public class SearchDriverRecyclerAdapter extends RecyclerView.Adapter<SearchDriverRecyclerAdapter.searchDriver_VH> {



    SearchDriverActivity searchDriverActivity;
    List<SearchDriverData> driverData;

    public SearchDriverRecyclerAdapter(SearchDriverActivity searchDriverActivity, List<SearchDriverData> driverData) {
        this.searchDriverActivity = searchDriverActivity;
        this.driverData = driverData;
    }

    @NonNull
    @Override
    public searchDriver_VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_driver_model, parent, false);

        return new searchDriver_VH(view, searchDriverActivity);

    }

    @Override
    public void onBindViewHolder(@NonNull searchDriver_VH holder, int position) {

        SearchDriverData driverDetail = driverData.get(position);

        holder.SearchDriverName.setText(driverDetail.getName());


        holder.SearchDriverRating.setRating(Float.parseFloat(driverDetail.getRatings()));

        holder.SerchDriverAverageRating.setText(driverDetail.getRatings());

        if (driverDetail.getDriver_image().isEmpty()) {

            holder.SearchDriverLogo.setImageResource(R.drawable.fb_profile_logo);
        } else {

            Picasso.with(holder.SearchDriverRating.getContext()).load(driverDetail.getDriver_image())
                    .into(holder.SearchDriverLogo);

        }

        if (searchDriverActivity.is_in_Action_mode == false) {

            holder.selectMultipleDriverCheckbox.setVisibility(View.GONE);
            holder.messageSearchDriver.setVisibility(View.VISIBLE);
            holder.CallSearchDriver.setVisibility(View.VISIBLE);

        } else {

            holder.selectMultipleDriverCheckbox.setVisibility(View.VISIBLE);
            holder.messageSearchDriver.setVisibility(View.GONE);
            holder.CallSearchDriver.setVisibility(View.GONE);
            holder.RequestSearchDriver.setVisibility(View.GONE);
            holder.RequestSentSucessfully.setVisibility(View.GONE);

            holder.selectMultipleDriverCheckbox.setChecked(false);
        }


    }

    @Override
    public int getItemCount() {

        if (driverData != null) {

            return driverData.size();
        }

        return 0;
    }


    //Define listner member
    private OnItemClickListner onItemClickListner;

    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListner(OnItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }

    class searchDriver_VH extends RecyclerView.ViewHolder {

        ImageView SearchDriverLogo, CallSearchDriver, RequestSearchDriver, RequestSentSucessfully, messageSearchDriver;
        TextView SearchDriverName, SearchDriverServiceName, SerchDriverAverageRating, SearchDriverTotalRating;
        RatingBar SearchDriverRating;
        CheckBox selectMultipleDriverCheckbox;
        CardView SearchDriverCardView;

        SearchDriverActivity searchDriverActivity;

        public searchDriver_VH(@NonNull final View itemView, final SearchDriverActivity searchDriverActivity) {
            super(itemView);


            this.searchDriverActivity = searchDriverActivity;

            SearchDriverLogo = (ImageView) itemView.findViewById(R.id.SearchDriverLogo);

            CallSearchDriver = (ImageView) itemView.findViewById(R.id.CallSearchDriver);

            SearchDriverCardView = (CardView) itemView.findViewById(R.id.SearchDriverCardView);

            RequestSearchDriver = (ImageView) itemView.findViewById(R.id.RequestSearchDriver);

            RequestSentSucessfully = (ImageView) itemView.findViewById(R.id.RequestSentSucessfully);

            messageSearchDriver = (ImageView) itemView.findViewById(R.id.messageSearchDriver);

            SearchDriverName = (TextView) itemView.findViewById(R.id.SearchDriverName);

            SearchDriverTotalRating = (TextView) itemView.findViewById(R.id.SearchDriverTotalRating);


            SearchDriverServiceName = (TextView) itemView.findViewById(R.id.SearchDriverServiceName);

            SerchDriverAverageRating = (TextView) itemView.findViewById(R.id.SerchDriverAverageRating);


            SearchDriverRating = (RatingBar) itemView.findViewById(R.id.SearchDriverRating);

            selectMultipleDriverCheckbox = (CheckBox) itemView.findViewById(R.id.selectMultipleDriverCheckbox);


            CallSearchDriver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //so here we are getting the position of driverSendRequestBtn on Clicking the button
                    //and we are send that position to the interface mate i.e onSendRequest

                    // Triggers click upwards to the adapter on click

                    if (onItemClickListner != null) {

                        int position = getAdapterPosition();
//                        CallSearchDriver.setVisibility(View.GONE);
//                        RequestSearchDriver.setVisibility(View.VISIBLE);

                        if (position != RecyclerView.NO_POSITION) {

                            onItemClickListner.onCallBtnClicked(itemView, position);
                        }
                    }
                }
            });


            messageSearchDriver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (onItemClickListner != null) {

                        int position = getAdapterPosition();

//                        RequestSearchDriver.setVisibility(View.GONE);
//                        RequestSentSucessfully.setVisibility(View.VISIBLE);

                        if (position != RecyclerView.NO_POSITION) {

                            onItemClickListner.onsendMessageClicked(itemView, position);
                        }
                    }

                }
            });


            RequestSearchDriver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (onItemClickListner != null) {

                        int position = getAdapterPosition();

                        RequestSearchDriver.setVisibility(View.GONE);
                        RequestSentSucessfully.setVisibility(View.VISIBLE);

                        if (position != RecyclerView.NO_POSITION) {

                            onItemClickListner.onSendRequestToDriver(itemView, position);
                        }
                    }
                }
            });


            // here we are passing this onLongClicklistner to SearchDriverActivity
            SearchDriverCardView.setOnLongClickListener(searchDriverActivity);

            selectMultipleDriverCheckbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int psotion = getAdapterPosition();

                    if (psotion != RecyclerView.NO_POSITION) {

                        onItemClickListner.onCheckboxSelected(itemView, psotion);
                    }
                }
            });

        }
    }


    // Define the listener interface
    public interface OnItemClickListner {

        void onCallBtnClicked(View itemview, int position);

        void onsendMessageClicked(View itemview, int position);

        void onSendRequestToDriver(View itemview, int position);

        void onCheckboxSelected(View itemview, int position);
    }
}




