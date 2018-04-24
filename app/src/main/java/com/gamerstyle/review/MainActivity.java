package com.gamerstyle.review;

import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<ExampleItem> mExampleList;

    private RecyclerView mRecyclerView;
    private ExampleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    //notification
    public static final int ACTIVITY_CODE = 99;
    NotificationCompat.Builder mBuilder;
    NotificationManager mNotifyMgr;
    String CHANNEL_ID;
    private Button btnBNK;


    //ham_s
    private  String[] mDrawerTitle = {"BNK48"};
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ListView mListView;

    //alertDialog
    private View v;


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }


    //ham_e

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createExampleList();
        buildRecyclerView();

        btnBNK = findViewById(R.id.btnBNK);

            mDrawerLayout = findViewById(R.id.drawer_layout);
            actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this,
                    mDrawerLayout,
                    R.string.open_drawer,
                    R.string.close_drawer);
            mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            mListView = findViewById(R.id.drawer);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>( this,
                    android.R.layout.simple_list_item_1,mDrawerTitle );
            mListView.setAdapter(adapter);

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String itemValue = (String) mListView.getItemAtPosition(position);

                    mDrawerLayout.closeDrawers();
                    Toast.makeText(getApplicationContext(),
                            "Position :" + position + "  ListItem : " + itemValue, Toast.LENGTH_SHORT)
                            .show();
                }
            });

        btnBNK.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                CharSequence name = "my_channel";
                String Description = "This is my channel";
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
                mChannel.setDescription(Description);
                mChannel.enableLights(true);
                mChannel.setLightColor(Color.RED);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                mChannel.setShowBadge(false);
                mNotifyMgr.createNotificationChannel(mChannel);

                mNotifyMgr.notify(1, mBuilder.build());

                alertCustomDialog2(v);
            }
        });

        CHANNEL_ID = "my_channel_01";
        mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle("BNK48")
                .setContentText("Open BNK48 Page");

        Intent resultIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.bnk48.com/#/home"));

//  Define notification action
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,   // context
                        0,      // requestCode (defined PendingIntent id)
                        resultIntent,
                        // if a previous PendingIntent already exists,
                        // then the current one will update it with the latest intent
                        PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        alertCustomDialog1(v);
    }

    // Dialog
    public void alertCustomDialog1(View v) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(dialog.getWindow().FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.customdialog);
        dialog.setCancelable(true);

        Button button1 = (Button) dialog.findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext()
                        , "Close dialog", Toast.LENGTH_SHORT);
                dialog.cancel();
            }
        });

        TextView textView1 = (TextView) dialog.findViewById(R.id.textView1);
        textView1.setText("Welcom to BNK48");
        TextView textView2 = (TextView) dialog.findViewById(R.id.textView2);
        textView2.setText("Good Luck !!!");

        dialog.show();
    }

    public void alertCustomDialog2(View v) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(dialog.getWindow().FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.customdialog);
        dialog.setCancelable(true);

        Button button1 = (Button) dialog.findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext()
                        , "Close dialog", Toast.LENGTH_SHORT);
                dialog.cancel();
            }
        });

        TextView textView1 = (TextView) dialog.findViewById(R.id.textView1);
        textView1.setText("Go to Website BNK48");
        TextView textView2 = (TextView) dialog.findViewById(R.id.textView2);
        textView2.setText("<3");

        dialog.show();
    }

    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;

        switch (item.getItemId()) {
            case R.id.mnuNew:
                Toast.makeText(this, "New!", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.mnuHelp:
                Toast.makeText(this, "Help!", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //menu
    public void createExampleList() {
        mExampleList = new ArrayList<>();
        mExampleList.add(new ExampleItem(R.drawable.cher, "CHERPRANG", "Date of birth :2 May 1996\n" +
                "Height :160 cm\n" +
                "Province :Bangkok\n" +
                "Like :-\n" +
                "Blood Group :B\n" +
                "Hobby :กิน, นอน, เล่นเกม, ฟังเพลง, Cosplay"));
        mExampleList.add(new ExampleItem(R.drawable.pun, "PUN", "Date of birth :9 Nov 2000\n" +
                "Height :166 cm\n" +
                "Province :Bangkok\n" +
                "Like :Fashion\n" +
                "Blood Group :A\n" +
                "Hobby :ฟังเพลง, เล่นมือถือ"));
        mExampleList.add(new ExampleItem(R.drawable.orn, "ORN", "Date of birth :3 Feb 1997\n" +
                "Height :164 cm\n" +
                "Province :Bangkok\n" +
                "Like :แมว, แมวน้ำ, แฟชั่น, เครื่องสำอาง\n" +
                "Blood Group :O\n" +
                "Hobby :นอน, อ่านหนังสือ, เล่นกับแมว"));
        mExampleList.add(new ExampleItem(R.drawable.music, "MUSIC", "Date of birth :24 Feb 2001\n" +
                "Height :158 cm\n" +
                "Province :Bangkok\n" +
                "Like :อะไรก็ได้นิ่มๆ\n" +
                "Blood Group :B\n" +
                "Hobby :Cosplay, Game"));
        mExampleList.add(new ExampleItem(R.drawable.kai, "KAIMOOK", "Date of birth :27 Aug 1997\n" +
                "Height :153 cm\n" +
                "Province :Bangkok\n" +
                "Like :แมว\n" +
                "Blood Group :O\n" +
                "Hobby :เข้าครัว, เย็บผ้า"));
        mExampleList.add(new ExampleItem(R.drawable.pupe, "PUPE", "Date of birth :18 Jan 1998\n" +
                "Height :160 cm\n" +
                "Province :Chiang Rai\n" +
                "Like :ผ้มห่ม, สีชมพู, โดเรม่อน\n" +
                "Blood Group :B\n" +
                "Hobby :เล่นเกม, ดูอนิเมะ, ฟังเพลง. นอน"));
        mExampleList.add(new ExampleItem(R.drawable.kaew, "KAEW", "Date of birth :31 Mar 1994\n" +
                "Height :156 cm\n" +
                "Province :Chonburi\n" +
                "Like :เครื่องสำอาง, น้ำหอม\n" +
                "Blood Group :B\n" +
                "Hobby :เล่นเปียโน, นอน, เดินเล่น"));
        mExampleList.add(new ExampleItem(R.drawable.kate, "KATE", "Date of birth :9 Jun 2001\n" +
                "Height :162 cm\n" +
                "Province :Phayao\n" +
                "Like :gudetama, ตุ๊กตา\n" +
                "Blood Group :B\n" +
                "Hobby :ชิว"));
    }

    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ExampleAdapter(mExampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(MainActivity.this, Activity2.class);
                intent.putExtra("Example Item", mExampleList.get(position));

                startActivity(intent);
            }
        });
    }
}