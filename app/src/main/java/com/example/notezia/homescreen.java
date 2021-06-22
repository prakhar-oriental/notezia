package com.example.notezia;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class homescreen extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    TabLayout tabLayout;
    TabItem tab1,tab2;
    String name;
    FirebaseAuth tfirebaseAuth = FirebaseAuth.getInstance();
    ViewPager viewPager;
    fragmentmanager fragmentmanager;
    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Toolbar toolbar=(Toolbar)findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);
        setTitle("Notezia");
        firebaseFirestore = FirebaseFirestore.getInstance();
        drawerLayout=(DrawerLayout)findViewById(R.id.mydrawer);
        navigationView=(NavigationView)findViewById(R.id.cnav);
        tabLayout=(TabLayout)findViewById(R.id.ctablayout);
        tab1=(TabItem)findViewById(R.id.ctab1);
        tab2=(TabItem)findViewById(R.id.ctab2);
        viewPager=(ViewPager)findViewById(R.id.pageholder);


        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        fragmentmanager=new fragmentmanager(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,tabLayout.getTabCount());
        viewPager.setAdapter(fragmentmanager);
        View navHeaderView= navigationView.getHeaderView(0);
        TextView navtext = navHeaderView.findViewById(R.id.navtext);
        ImageView navimage = navHeaderView.findViewById(R.id.nav_imgage);
        DocumentReference documentReference=firebaseFirestore.collection("Users").document("data");
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable  DocumentSnapshot value, @Nullable  FirebaseFirestoreException error) {
                 if(value!=null)
                 {
                     name = value.getString(tfirebaseAuth.getUid());
                     Log.d("op",tfirebaseAuth.getUid());
                     navtext.setText("Hello User");
                 }
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                if(item.getItemId()==R.id.home)
                    startActivity(new Intent(homescreen.this,homescreen.class));
                    //finish();

                if(item.getItemId()==R.id.AboutUs)
                    startActivity(new Intent(homescreen.this,Aboutus.class));
                  //finish();

                if(item.getItemId()==R.id.logout)
                    startActivity(new Intent(homescreen.this,loginuser.class));
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });


    }


}