package com.example.notezia;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class Fragment1 extends Fragment {
    RecyclerView recview;
    notesAdapter NotesAdapter;
    FirebaseAuth frag1firebaseauth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_1, container, false);
          recview = view.findViewById(R.id.recview);
          frag1firebaseauth = FirebaseAuth.getInstance();
        recview.setLayoutManager(new LinearLayoutManager(this.getContext()));

        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Notes").child("s1").child(frag1firebaseauth.getUid()), model.class)
                        .build();
        NotesAdapter = new notesAdapter(options);
        recview.setAdapter(NotesAdapter);

        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        NotesAdapter.startListening();
    }


    @Override
    public void onStop() {
        super.onStop();
        NotesAdapter.stopListening();
    }
}