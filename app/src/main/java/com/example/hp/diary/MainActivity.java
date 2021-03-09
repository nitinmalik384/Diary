package com.example.hp.diary;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton plus;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    Animation anim;
   /*private int[] tabIcons = {
            R.drawable.docfortab,
            R.drawable.painticonblue,
            //R.drawable.ic_tab_contacts
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        plus=(FloatingActionButton) findViewById(R.id.plus);


        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         anim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        plus.startAnimation(anim);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();


          tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
              @Override
              public void onTabSelected(TabLayout.Tab tab) {
                  if (tabLayout.getSelectedTabPosition()==0){
                  plus.setImageResource(R.drawable.pencil);
                    //  anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                      //plus.startAnimation(anim);
              }
              else {

                      plus.setImageResource(R.drawable.brush);

                      //anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                     // plus.startAnimation(anim);


                  }}

              @Override
              public void onTabUnselected(TabLayout.Tab tab) {
                //  plus.setImageResource(R.drawable.painticonfortab);

              }

              @Override
              public void onTabReselected(TabLayout.Tab tab) {
               //   plus.setImageResource(R.drawable.docfortab);

              }
          });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if (tabLayout.getTabAt(0).isSelected()){

                    Intent intent=new Intent(getApplicationContext(),WritingNotes.class);
                    intent.putExtra("create_update",0);//0 for create & 1 for update

                    startActivity(intent);
                }
                else if (tabLayout.getTabAt(1).isSelected()){
                    Intent intent=new Intent(getApplicationContext(),DrawingNotes.class);
                    startActivity(intent);
                }
            }
        });
    }
    int count=0;

    @Override
    public void onBackPressed() {
        ++count;
        if (count==1){
            Toast.makeText(getApplicationContext(),"Press Again",Toast.LENGTH_SHORT).show();


        }

        else if (count==2){
        count=0;
       finish();}
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0);
        tabLayout.getTabAt(1);
       // tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new NotesFragments(), "Notes");
        adapter.addFrag(new PaintFragment(), "Paints");
     //   adapter.addFrag(new ThreeFragment(), "THREE");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.settingmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==R.id.setting){


            Intent intent = new Intent(MainActivity.this, SettingActicity.class);
            startActivity(intent);


        }
        return true;
       }

    }
