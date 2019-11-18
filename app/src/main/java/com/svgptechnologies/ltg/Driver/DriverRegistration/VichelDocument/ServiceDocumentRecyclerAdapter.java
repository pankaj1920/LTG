package com.svgptechnologies.ltg.Driver.DriverRegistration.VichelDocument;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.svgptechnologies.ltg.Json.DriverJson.VichelDocument.DocumentListData;
import com.svgptechnologies.ltg.R;

import java.util.List;

public class ServiceDocumentRecyclerAdapter extends RecyclerView.Adapter<ServiceDocumentRecyclerAdapter.vechiledoc_VH> {

    List<DocumentListData> documentListData;
    ServiceDocumentsActivity serviceDocumentsActivity;

    public ServiceDocumentRecyclerAdapter ( ServiceDocumentsActivity serviceDocumentsActivity, List<DocumentListData> documentListData ) {
        this.documentListData = documentListData;
        this.serviceDocumentsActivity = serviceDocumentsActivity;
    }

    @NonNull
    @Override
    public vechiledoc_VH onCreateViewHolder ( @NonNull ViewGroup parent, int viewType ) {

        View view = LayoutInflater.from ( parent.getContext ( ) ).inflate ( R.layout.service_documents_list, parent, false );
        return new vechiledoc_VH ( view );
    }

    @Override
    public void onBindViewHolder ( @NonNull vechiledoc_VH holder, int position ) {

        DocumentListData documentList = documentListData.get ( position );
        holder.documentName.setText ( documentList.getDocument_type ( ) );
    }

    @Override
    public int getItemCount ( ) {
        return documentListData.size ( );
    }


    private OnItemClickListner onItemClickListner;

    public void setOnItemClickListner ( OnItemClickListner onItemClickListner ) {

        this.onItemClickListner = onItemClickListner;
    }

    class vechiledoc_VH extends RecyclerView.ViewHolder {

        ImageView DocumentImage, PickDocument;
        TextView documentName;

        public vechiledoc_VH ( @NonNull final View itemView ) {
            super ( itemView );

            DocumentImage = itemView.findViewById ( R.id.DocumentImage );
            PickDocument = itemView.findViewById ( R.id.PickDocument );
            documentName = itemView.findViewById ( R.id.documentName );

            PickDocument.setOnClickListener ( new View.OnClickListener ( ) {
                @Override
                public void onClick ( View v ) {

                    if ( onItemClickListner != null ) {

                        int position = getAdapterPosition ( );
                        if ( position != RecyclerView.NO_POSITION ) {

                            onItemClickListner.onPickDocumentClickListner ( itemView, position );
                        }
                    }
                }
            } );
        }
    }

    public interface OnItemClickListner {

        void onPickDocumentClickListner ( View view, int position );
    }

}
