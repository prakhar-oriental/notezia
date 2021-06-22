package com.example.notezia;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class Fragment2 extends Fragment {
    EditText notetitle,notedesc;
    FirebaseAuth addnotefirebaseAuth;
    Button notesavebtn;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_2, container, false);

        notesavebtn = view.findViewById(R.id.notesavebtn);
        addnotefirebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Notes").child("s1").child(addnotefirebaseAuth.getUid());
        notetitle = view.findViewById(R.id.notestitle);
        notedesc = view.findViewById(R.id.notesdescription);


        notesavebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String snotetitle = notetitle.getText().toString().trim();
                String snotedesc = notedesc.getText().toString().trim();
                /*HashMap<String,String> note = new HashMap<>();

                note.put("notedesc",snotedesc);
                note.put("notetitle",snotetitle);*/
                model2 note = new model2(snotetitle,snotedesc);

                databaseReference.push().setValue(note).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete( Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(getContext(), "Note save successfully", Toast.LENGTH_SHORT).show();
                            notetitle.getText().clear();
                            notedesc.getText().clear();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure( Exception e) {
                        Toast.makeText(getContext(), "ERROR"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });



















        return view;
    }
}