package com.example.cardmaker.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.cardmaker.R;
import com.example.cardmaker.models.SendModel;

import java.util.ArrayList;
import java.util.List;

public class ShowUserAdapter extends RecyclerView.Adapter<ShowUserAdapter.ShowNameHoder>{

    private ArrayList<SendModel> sendlist;
    private OnShowAdapterItemClickListener itemClickListener;

    public interface OnShowAdapterItemClickListener{
        void OnItemClick(View v, int position);
    }

    public OnShowAdapterItemClickListener getMlistener() {
        return itemClickListener;
    }

    public void setMlistener(OnShowAdapterItemClickListener mlistener) {
        this.itemClickListener = mlistener;
    }
    public ArrayList<SendModel> getSendlist() {
        return sendlist;
    }

    public void setSendlist(ArrayList<SendModel> sendlist) {
        this.sendlist = sendlist;
    }

    @NonNull
    @Override
    public ShowUserAdapter.ShowNameHoder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_list_items,viewGroup,false);
        ShowUserAdapter.ShowNameHoder vh=new ShowUserAdapter.ShowNameHoder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ShowUserAdapter.ShowNameHoder showNameHoder, int i) {
        showNameHoder.searchName.setText(sendlist.get(i).getEmail()+"("+sendlist.get(i).getName()+")");
    }

    @Override
    public int getItemCount() {
        return sendlist.size();
    }

    public class ShowNameHoder extends RecyclerView.ViewHolder{

        TextView searchName;

        public ShowNameHoder(@NonNull View itemView) {
            super(itemView);
            searchName=(TextView)itemView.findViewById(R.id.searchedText);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   getMlistener().OnItemClick(view,getAdapterPosition());
                }
            });
        }


    }
    public void resetList(List<SendModel> update){
        sendlist=new ArrayList<>();
        sendlist.addAll(update);
        this.notifyDataSetChanged();
    }
    public void updateList(List<SendModel> update){
        sendlist=new ArrayList<>();
        sendlist.addAll(update);
        this.notifyDataSetChanged();
    }

}
