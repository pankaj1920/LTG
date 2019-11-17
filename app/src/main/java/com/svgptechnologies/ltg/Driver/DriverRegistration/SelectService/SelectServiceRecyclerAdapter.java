package com.svgptechnologies.ltg.Driver.DriverRegistration.SelectService;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.svgptechnologies.ltg.Json.DriverJson.SelectServiceType.SelectServiceTypeData;
import com.svgptechnologies.ltg.R;

import java.util.List;

public class SelectServiceRecyclerAdapter extends RecyclerView.Adapter<SelectServiceRecyclerAdapter.SelectService_VH> {

    List<SelectServiceTypeData> serviceTypList;
    SelectServiceTypeActivity selectServiceTypeActivity;


    public SelectServiceRecyclerAdapter(SelectServiceTypeActivity selectServiceTypeActivity, List<SelectServiceTypeData> serviceTypList) {
        this.serviceTypList = serviceTypList;
        this.selectServiceTypeActivity = selectServiceTypeActivity;
    }

    @NonNull
    @Override
    public SelectService_VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_type_list_items, parent, false);
        return new SelectService_VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectService_VH holder, int position) {

        SelectServiceTypeData serviceType = serviceTypList.get(position);
        holder.ServiceName.setText(serviceType.getServcie_name());

        if (serviceType.getService_image().isEmpty()) {

            holder.ServiceImage.setImageResource(R.drawable.service);
        } else {

            Picasso.with(holder.ServiceName.getContext()).load(serviceType.getService_image())
                    .into(holder.ServiceImage);
        }
    }

    @Override
    public int getItemCount() {
        return serviceTypList.size();
    }

    private OnItemClickListner onItemClickListner;

    public void OnItemClickListner(OnItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }


    class SelectService_VH extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView ServiceImage;
        TextView ServiceName;

        public SelectService_VH(@NonNull final View itemView) {
            super(itemView);

            ServiceImage = (ImageView) itemView.findViewById(R.id.ServiceImage);

            ServiceName = (TextView) itemView.findViewById(R.id.ServiceName);

            cardView = (CardView) itemView.findViewById(R.id.cardView);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (onItemClickListner != null) {

                        int postion = getAdapterPosition();

                        if (postion != RecyclerView.NO_POSITION) {

                            onItemClickListner.onSerViceTypeClicked(itemView, postion);
                        }
                    }
                }
            });
        }
    }

    public interface OnItemClickListner {

        void onSerViceTypeClicked(View itemView, int Postion);
    }

}
