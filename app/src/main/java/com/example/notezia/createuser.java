package com.example.notezia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class createuser extends AppCompatActivity {
    TextView clickhere;
    ProgressBar createprogressbar;
    private static int PICK_IMAGE=123;
      EditText createusername,createemail,createpassword;
      FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    ImageView createimageView;
    Button createaccountbutton;
    Uri imagepath;
    Uri imagepath2;
    DocumentReference documentReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createuser);
         clickhere = findViewById(R.id.clickhere);
        createprogressbar = findViewById(R.id.createprogressBar);
        createimageView = findViewById(R.id.userimg);
        createusername = findViewById(R.id.createname);
        createemail = findViewById(R.id.createemail);
        createpassword = findViewById(R.id.createpassword);
        createaccountbutton = findViewById(R.id.loginbutton);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference  = firebaseStorage.getReference();
        createprogressbar.setVisibility(View.INVISIBLE);
        clickhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(createuser.this,loginuser.class));
                finish();
            }
        });
        createimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveuserimg();
            }
        });
        createaccountbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createprogressbar.setVisibility(View.VISIBLE);

                userdata();

            }
        });

    }

    private void userdata() {
        String fname = createusername.getText().toString().trim();

        DocumentReference documentReference=firebaseFirestore.collection("Users").document("data");
        Map<String , Object> userdata=new HashMap<>();
        userdata.put("name",fname);



        documentReference.set(userdata).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
               // Toast.makeText(getApplicationContext(),"Data on Cloud Firestore send success", Toast.LENGTH_SHORT).show();

            }
        });
        FirebaseAutentication();
    }

    private void saveuserimg() {

                 Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                 startActivityForResult(intent,PICK_IMAGE);


    }

    private void FirebaseAutentication() {

        String username = createusername.getText().toString().trim();
        String useremail = createemail.getText().toString().trim();
        String userpassword = createpassword.getText().toString().trim();
        if(username.isEmpty())
        {
            Toast.makeText(this, "username is empty", Toast.LENGTH_SHORT).show();
        }else if(useremail.isEmpty())
        {
            Toast.makeText(this, "email is empty", Toast.LENGTH_SHORT).show();

        }else if(userpassword.isEmpty())
        {
            Toast.makeText(this, "password is empty", Toast.LENGTH_SHORT).show();

        }else
        {
            firebaseAuth.createUserWithEmailAndPassword(useremail, userpassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                StorageReference imageref=storageReference.child("Images").child(firebaseAuth.getUid());

                                UploadTask uploadTask = imageref.putFile(imagepath);
                                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        imageref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                imagepath2 = uri;
                                            }
                                        });
                                    }
                                });
                                // Sign in success, update UI with the signed-in user's information
                                createprogressbar.setVisibility(View.INVISIBLE);
                                Toast.makeText(createuser.this, "Account create successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(createuser.this,loginuser.class));
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                              //  Toast.makeText(createuser.this, "Account not create", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure( Exception e) {
                    Toast.makeText(createuser.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode==PICK_IMAGE && resultCode==RESULT_OK)
        {
            imagepath=data.getData();
            createimageView.setImageURI(imagepath);
        }




        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        if(firebaseAuth.getCurrentUser()!=null)
        {
            startActivity(new Intent(createuser.this,homescreen.class));
            finish();
        }
        super.onStart();
    }
}