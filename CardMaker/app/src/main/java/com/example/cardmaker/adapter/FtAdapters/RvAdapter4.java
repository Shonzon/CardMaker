package com.example.cardmaker.adapter.FtAdapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cardmaker.MyCardEdit.McEdit4;
import com.example.cardmaker.R;
import com.example.cardmaker.models.RvModel4;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;

public class RvAdapter4 extends RecyclerView.Adapter<RvAdapter4.MyHolder>{

    List<RvModel4> listdata;
    Context mContext;

    public RvAdapter4(Context context, List<RvModel4> listdata) {
        this.mContext = context;
        this.listdata = listdata;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.test_card4,viewGroup,false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        holder.pname.setText(listdata.get(position).getName());
        holder.designation.setText(listdata.get(position).getDesignation());
        holder.email.setText(listdata.get(position).getEmail());
        holder.pphone.setText(String.valueOf(listdata.get(position).getPhone()));
        holder.address.setText(listdata.get(position).getAddress());

    }

    @Override
    public int getItemCount() {
        //return listdata.size();
        int arr = 0;
        try{
            if(listdata.size()==0){
                arr = 0;
            }
            else{
                arr=listdata.size();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return arr;
    }

    class MyHolder extends RecyclerView.ViewHolder{
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        TextView pname,email,address,designation,pphone,optionsMenu;


        public MyHolder(final View itemView) {
            super(itemView);

            optionsMenu =itemView.findViewById(R.id.optionsMenu);
            pname = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
            pphone = itemView.findViewById(R.id.phone);
            designation = itemView.findViewById(R.id.designation);
            address = itemView.findViewById(R.id.address);
            //tempID = itemView.findViewById(R.id.tempID);


            /*optionsMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    //will show popup menu here
                    //creating a popup menu
                    PopupMenu popup = new PopupMenu(mContext,optionsMenu);
                    //inflating menu from xml resource
                    popup.inflate(R.menu.options_menu);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.add:
                                    Log.d("Click","Item 1");

                                    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
                                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                                    FirebaseUser me = mAuth.getCurrentUser();
                                    DatabaseReference myRef = mDatabase.getReference().child("usertemplate").child(mAuth.getCurrentUser().getUid());

                                    String userID = mAuth.getCurrentUser().getUid();
                                    String imageUri = "drawable://" + R.drawable.constant;


                                    RvModel4 data = new RvModel4(pname.getText().toString()
                                            ,designation.getText().toString()
                                            ,pphone.getText().toString(),email.getText().toString()
                                            ,address.getText().toString()
                                            );
                                    myRef.child("five").setValue(data);
                                    Toast.makeText(v.getContext(),"Added to mycard",Toast.LENGTH_SHORT).show();

                                    break;
                                case R.id.edit:
                                    Log.d("Click","Item 2");
                                    break;
                                case R.id.close:
                                    Log.d("Click","Item 3");
                                    break;
                            }
                            return false;
                        }
                    });
                    //displaying the popup
                    popup.show();
                }
            });*/

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent edit1 = new Intent(mContext, McEdit4.class);

                    FirebaseDatabase firebaseDatabase;
                    DatabaseReference myRef;
                    firebaseDatabase = FirebaseDatabase.getInstance();
                    myRef = firebaseDatabase.getReference("usertemplate").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("five");

                    HashMap<String, Object> result1 = new HashMap<>();
                    result1.put("name", "Sagar");
                    myRef.updateChildren(result1);

                    HashMap<String, Object> result2 = new HashMap<>();
                    result2.put("designation", "Designation");
                    myRef.updateChildren(result2);

                    HashMap<String, Object> result3 = new HashMap<>();
                    result3.put("email", "Email");
                    myRef.updateChildren(result3);

                    HashMap<String, Object> result4 = new HashMap<>();
                    result4.put("phone", "01832106030");
                    myRef.updateChildren(result4);

                    HashMap<String, Object> result5 = new HashMap<>();
                    result5.put("address", "Dhaka");
                    myRef.updateChildren(result5);


                    mContext.startActivity(edit1);
                }
            });
        }
    }

}
