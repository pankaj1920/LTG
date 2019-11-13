package com.svgptechnologies.ltg.User.VechileCategory;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.svgptechnologies.ltg.Json.UserJson.VechileCategory.VichelCategoryData;
import com.svgptechnologies.ltg.R;

import java.util.List;

public class VechileCategoryAdapter extends RecyclerView.Adapter<VechileCategoryAdapter.vechileCategory_VH> {

    List<VichelCategoryData> vechileList;
    //  Boolean isSelected = false;
    int isSelected = -1;

    public VechileCategoryAdapter(List<VichelCategoryData> vechileList) {
        this.vechileList = vechileList;
    }

    @NonNull
    @Override
    public vechileCategory_VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vechile_category_recycle_model
                , parent, false);

        return new vechileCategory_VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final vechileCategory_VH holder, final int position) {

        VichelCategoryData data = vechileList.get(position);

        holder.vechileType.setText(data.getVehicle_type());

        Picasso.with(holder.vechileImage.getContext()).load(data.getVehicle_icon())
                .into(holder.vechileImage);


        holder.vechileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // taking the ostiton of clicked holder
                if (onVechileClickListner != null) {

                    int posit = position;

                    if (posit != RecyclerView.NO_POSITION) {

                        // passing hoder and position to onVechileClick method in Interface
                        onVechileClickListner.onVechileClick(holder, posit);
                    }
                }

                // storing current position in isSelectd
                isSelected = position;
                notifyDataSetChanged();
            }
        });

        // changing the background of selected adapter
        if (isSelected == position) {
            //  holder.vechileImage.setBackgroundColor(holder.vechileImage.getResources().getColor(R.color.blue));
            holder.selectVechileLayout.setBackground(holder.vechileType.getResources().getDrawable(R.drawable.select_vechile_bg_color));

            holder.vechileDot.setVisibility(View.VISIBLE);
        } else {
            //  make the default color of unSelected vechile category
            holder.selectVechileLayout.setBackground(holder.vechileType.getResources().getDrawable(R.drawable.select_vechile_default_color));

            holder.vechileDot.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return vechileList.size();
    }


    //Define listner member
    private onVechileClickListner onVechileClickListner;

    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnVechileClickListner(onVechileClickListner onVechileClickListner) {
        this.onVechileClickListner = onVechileClickListner;
    }

    class vechileCategory_VH extends RecyclerView.ViewHolder {

        TextView vechileTime, vechileType;
        ImageView vechileImage, vechileDot;
        ConstraintLayout selectVechileLayout, vichelCategoryLayout;

        public vechileCategory_VH(@NonNull final View itemView) {
            super(itemView);

            selectVechileLayout = (ConstraintLayout) itemView.findViewById(R.id.selectVechileLayout);

            vechileImage = (ImageView) itemView.findViewById(R.id.vechileImage);

            vechileDot = (ImageView) itemView.findViewById(R.id.vechileDot);

            vechileType = (TextView) itemView.findViewById(R.id.vechileType);

            selectVechileLayout = (ConstraintLayout) itemView.findViewById(R.id.selectVechileLayout);

            vichelCategoryLayout = (ConstraintLayout) itemView.findViewById(R.id.vichelCategoryLayout);

        }

    }

    //Creating the interface to pass data to UserHomepageActivity
    public interface onVechileClickListner {

        void onVechileClick(RecyclerView.ViewHolder holder, int postion);
    }
}

