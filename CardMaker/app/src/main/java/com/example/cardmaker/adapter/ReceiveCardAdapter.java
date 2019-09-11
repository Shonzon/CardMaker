package com.example.cardmaker.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cardmaker.R;
import com.example.cardmaker.models.SendModel;
import com.example.cardmaker.models.UploadModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ReceiveCardAdapter extends RecyclerView.Adapter<ReceiveCardAdapter.ReceiveViewHolder> {

    private Context context;
    private List<UploadModel> uploadModelList;

    public ReceiveCardAdapter(Context context,List<UploadModel> uploadModels){
        this.context=context;
        this.uploadModelList=uploadModels;
    }

    @NonNull
    @Override
    public ReceiveViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(context).inflate(R.layout.receive_card_adapter,viewGroup,false);
        return new ReceiveViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceiveViewHolder receiveViewHolder, int i) {
        UploadModel uploadModel=uploadModelList.get(i);
        receiveViewHolder.receiveEmail.setText(uploadModel.getUserEmail()+"("+uploadModel.getUserName()+")");
        Glide.with(context).load(uploadModel.getImageUri())
                .into(receiveViewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return uploadModelList.size();
    }

    public class ReceiveViewHolder extends RecyclerView.ViewHolder{
        public TextView receiveEmail;
        public ImageView imageView;

        public  ReceiveViewHolder(View itemView){
            super(itemView);

            receiveEmail=(TextView) itemView.findViewById(R.id.receiveEmail);
            imageView=(ImageView) itemView.findViewById(R.id.receiveImage);
        }
    }
    public void resetList(List<UploadModel> update){
        uploadModelList=new ArrayList<>();
        uploadModelList.addAll(update);
        this.notifyDataSetChanged();
    }
    public void updateList(List<UploadModel> update){
        uploadModelList=new ArrayList<>();
        uploadModelList.addAll(update);
        this.notifyDataSetChanged();
    }
}
