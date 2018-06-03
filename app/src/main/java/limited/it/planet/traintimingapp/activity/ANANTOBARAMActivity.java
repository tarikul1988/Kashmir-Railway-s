package limited.it.planet.traintimingapp.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import limited.it.planet.traintimingapp.R;
import limited.it.planet.traintimingapp.model.TrainSchedule;

public class ANANTOBARAMActivity extends AppCompatActivity implements View.OnClickListener{
    Toolbar toolbar;
    TableLayout tableLayout;
    static ArrayList<TrainSchedule> trainScheduleList ;
    DatabaseReference databaseReference;
    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anantobaram);
        toolbar = (Toolbar)findViewById(R.id.toolbar_anantobaram_activity);
        setSupportActionBar(toolbar);
        tableLayout = findViewById(R.id.tbl_layout_anantobad);
        addHeaders();
        trainScheduleList = new ArrayList<TrainSchedule>();

        databaseReference = FirebaseDatabase.getInstance().getReference("name_of_station");
        DatabaseReference dr = databaseReference.child("ANANTNAG TO BARAMULLA");

        dr.addValueEventListener(new ValueEventListener() {
            // ArrayList<TrainSchedule> trainScheduleList = new ArrayList<>();
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    TrainSchedule c = snapshot.getValue(TrainSchedule.class);
                    //Log.d("Categories: ", c.name + " " + c.food_items);
                    trainScheduleList.add(c);

                }
                orderDescending(trainScheduleList);
                addRows();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-2650328417911011/5304382557");
        // Request for Ads ca-app-pub-2650328417911011/5304382557
        AdRequest adRequest = new AdRequest.Builder().build();
        requestNewInterstitial();
        // Load ads into Interstitial Ads
        mInterstitialAd.loadAd(adRequest);

        // Prepare an Interstitial Ad Listener
        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                // Call displayInterstitial() function
                displayInterstitial();
            }


        });
    }

    public static void orderDescending(final ArrayList<TrainSchedule> list) {
        Collections.sort(list, new Comparator<TrainSchedule>() {
            public int compare(TrainSchedule s1, TrainSchedule s2) {
                Integer i1 = s1.getSerialNumber();
                Integer i2 = s2.getSerialNumber();
                // return i1.compareTo(i2);
                return i2.compareTo(i1);
            }
        });
    }
    private TextView getTextView(int id, String title, int color, int typeface, int bgColor) {
        TextView tv = new TextView(this);
        tv.setId(id);
        tv.setText(title.toUpperCase());
        tv.setTextColor(color);
        tv.setPadding(40, 40, 40, 40);
        tv.setTypeface(Typeface.DEFAULT, typeface);
        tv.setBackgroundColor(bgColor);
        tv.setLayoutParams(getLayoutParams());
        tv.setOnClickListener(this);
        return tv;
    }
    private TextView getRowsTextView(int id, String title, int color, int typeface, int bgColor) {
        TextView tv = new TextView(this);
        tv.setId(id);
        tv.setText(title);
        tv.setTextColor(color);
        tv.setPadding(40, 40, 40, 40);
        tv.setTypeface(Typeface.DEFAULT, typeface);
        tv.setBackgroundColor(bgColor);
        tv.setLayoutParams(getLayoutParams());
        tv.setOnClickListener(this);
        return tv;
    }

    @NonNull
    private TableRow.LayoutParams getLayoutParams() {
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        params.setMargins(2, 0, 0, 2);
        return params;
    }

    @NonNull
    private TableLayout.LayoutParams getTblLayoutParams() {
        return new TableLayout.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onClick(View view) {

    }

    /**
     * This function add the headers to the table
     **/
    public void addHeaders() {

        TableLayout tl = findViewById(R.id.tbl_layout_anantobad);
        TableRow tr = new TableRow(this);
        tr.setLayoutParams(getLayoutParams());

        //  tr.addView(getTextView(0, "Auditor id", Color.WHITE, Typeface.BOLD, R.color.colorAccent));
        tr.addView(getTextView(0, "TRAIN NUMBER", Color.WHITE, Typeface.BOLD, R.color.colorAccent));
        tr.addView(getTextView(0, "DAY", Color.WHITE, Typeface.BOLD, R.color.colorAccent));
        tr.addView(getTextView(0, "TIMING", Color.WHITE, Typeface.BOLD, R.color.colorAccent));

        tl.addView(tr, getTblLayoutParams());
    }
    public void addRows(){
        Collections.reverse(trainScheduleList);
        for (int i = 0; i < trainScheduleList.size(); i++) {
            TableRow tr = new TableRow(ANANTOBARAMActivity.this);
            tr.setLayoutParams(getLayoutParams());

            tr.addView(getRowsTextView(0, trainScheduleList.get(i).getTrainNumber(),Color.WHITE, Typeface.BOLD, ContextCompat.getColor(ANANTOBARAMActivity.this, R.color.cell_background_color)));
            tr.addView(getRowsTextView(0, trainScheduleList.get(i).getDay(), Color.WHITE, Typeface.BOLD, ContextCompat.getColor(ANANTOBARAMActivity.this, R.color.cell_background_color)));
            tr.addView(getRowsTextView(0, trainScheduleList.get(i).getTiming(), Color.WHITE, Typeface.BOLD, ContextCompat.getColor(ANANTOBARAMActivity.this, R.color.cell_background_color)));

            tableLayout.addView(tr, getTblLayoutParams());

        }

    }
    public void displayInterstitial() {
        // If Ads are loaded, show Interstitial else show nothing.
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
//                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//                .addTestDevice("YOUR_DEVICE_HASH")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

}
