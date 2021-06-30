package com.example.notezia;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.orhanobut.dialogplus.ViewHolder;

class notesAdapter extends FirebaseRecyclerAdapter<model,notesAdapter.myviewholder>  {
     FirebaseAuth noteadapterauth = FirebaseAuth.getInstance();
    public notesAdapter(@NonNull  FirebaseRecyclerOptions<model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull notesAdapter.myviewholder holder, int position, @NonNull  model model) {

        holder.singlentitle.setText(model.getNotetitle());
        holder.singlendes.setText(model.getNotedesc());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference()
                        .child("Notes")
                        .child("s1")
                        .child(noteadapterauth.getUid())
                        .child(getRef(position).getKey())
                        .removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogPlus dialog = DialogPlus.newDialog(v.getContext())
                        .setGravity(Gravity.CENTER)
                        .setMargin(50,0,50,0)
                        .setContentHolder(new ViewHolder(R.layout.updatenote))
                        .setExpanded(false)  // This will enable the expand feature, (similar to android L share dialog)
                        .create();
                View  holderView = dialog.getHolderView();
                EditText ntitle = holderView.findViewById(R.id.unotetitle);
                EditText ndesc = holderView.findViewById(R.id.unotedesc);


                ntitle.setText(model.getNotetitle());
                ndesc.setText(model.getNotedesc());


                Button update = holderView.findViewById(R.id.updatebutton);
                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String,Object> map = new HashMap<>();
                   //     map.put("notedesc",ntitle.getText().toString());
                        map.put("notetitle",ntitle.getText().toString());
                        map.put("notedesc",ndesc.getText().toString());

                        FirebaseDatabase.getInstance().getReference()
                                .child("Notes")
                                .child("s1")
                                .child(noteadapterauth.getUid())
                                .child(getRef(position).getKey())
                                .updateChildren(map)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        dialog.dismiss();
                                    }
                                });
                    }
                });
                dialog.show();
            }
        });
    }

    @NonNull

    @Override
    public myviewholder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.noteshowdesign,parent,false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView singlentitle,singlendes;
        ImageView delete;
        ImageView edit;
        public myviewholder(View itemView) {
            super(itemView);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
            cardView = itemView.findViewById(R.id.singlecardview);
            singlentitle = itemView.findViewById(R.id.singlentitle);
            singlendes = itemView.findViewById(R.id.singlendes);
        }
    }
}

