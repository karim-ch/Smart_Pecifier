package com.example.acer.smartpecifier;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Handler;
import android.support.design.button.MaterialButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.lzyzsd.circleprogress.ArcProgress;
import com.macroyau.thingspeakandroid.ThingSpeakChannel;
import com.macroyau.thingspeakandroid.ThingSpeakLineChart;
import com.macroyau.thingspeakandroid.model.ChannelFeed;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import at.markushi.ui.CircleButton;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class OpenPecifier extends AppCompatActivity {

    private ThingSpeakChannel tsChannel;
    private ThingSpeakLineChart tsChart;
    private LineChartView chartView;
    private Toolbar mToolbar;
    private ArcProgress arcTemp;
    private TextView BabyName;

    private RadioGroup toggle_motor;
    private RadioButton onBtn;
    private RadioButton offBtn;
    private boolean ifWorking=false;
    private Dialog MyDialog;

    private Handler handler;

    private EditText WRITE_KEY_EDIT;

    String ID,BABY_NAME,READ_KEY,Position,WRITE_KEY, seuil="38";
    Calendar calendar;

    private ProgressDialog mRegProgress;

    SharedPrefrence sharedPrefrence;

    MaterialButton ChangeSeuil;
    EditText SeuilEdit;

    boolean seuiIsInit=false;

    private TextView danger;
    private MaterialButton voir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_pecifier);

        //GET INTENT
        Intent intent = getIntent();
        ID = intent.getStringExtra("ID");
        BABY_NAME = intent.getStringExtra("Name");
        READ_KEY = intent.getStringExtra("READ_KEY");
        Position = intent.getStringExtra("Position");

        MyDialog = new Dialog(this);

        sharedPrefrence=new SharedPrefrence();
        //sharedPrefrence.getFavorites(this).get(Integer.parseInt(Position)).getWRITE_KEY();


        mRegProgress= new ProgressDialog(this);
        mRegProgress.setTitle("Chargement de données");
        mRegProgress.setMessage("Veuillez patienter pendant que nous chargeons les données !");
        mRegProgress.show();

        handler = new Handler();



        mToolbar =(Toolbar)findViewById(R.id.toolbar_open);
        mToolbar.setNavigationIcon(R.mipmap.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OpenPecifier.this, SuperviserActivity.class);
                startActivity(i);
            }
        });



        danger =findViewById(R.id.danger);
        voir=findViewById(R.id.voir);


            voir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = "https://www.med.tn/medecin/pediatre/tunis/";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            });




        toggle_motor=(RadioGroup)findViewById(R.id.toggle_motor);
        onBtn=(RadioButton)findViewById(R.id.motor_on);
        offBtn=(RadioButton)findViewById(R.id.motor_off);


        arcTemp=(ArcProgress)findViewById(R.id.arc_prog_temp);
        BabyName=findViewById(R.id.open_bbname);
        BabyName.setText(BABY_NAME);

        // Create a Calendar object dated 5 minutes ago
         calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -5);

        // Configure LineChartView
        chartView = findViewById(R.id.chart);
        chartView.setZoomEnabled(false);
        chartView.setValueSelectionEnabled(true);

        // Create a line chart from Field1 of ThinkSpeak Channel 9
        tsChart = new ThingSpeakLineChart(Long.parseLong(ID), 1, READ_KEY);
        // Get 200 entries at maximum
        tsChart.setNumberOfEntries(10);
        // Set value axis labels on 10-unit interval
        tsChart.setValueAxisLabelInterval(10);
        // Set date axis labels on 5-minute interval
        tsChart.setDateAxisLabelInterval(5);
        // Show the line as a cubic spline
        tsChart.useSpline(true);
        // Set the line color
         tsChart.setLineColor(Color.parseColor("#d3d3d3"));
        // Set the axis color
        tsChart.setAxisColor(Color.parseColor("#455a64"));
        // Set the starting date (5 minutes ago) for the default viewport of the chart
        tsChart.setChartStartDate(calendar.getTime());
        // Set listener for chart data update







        offBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                WRITE_KEY=sharedPrefrence.getFavorites(OpenPecifier.this).get(Integer.parseInt(Position)).getWRITE_KEY();
                if(WRITE_KEY==null){
                    ShowPopup(view);
                }else{
                RequestQueue queue = Volley.newRequestQueue(OpenPecifier.this);
                String url ="https://api.thingspeak.com/update?api_key="+WRITE_KEY+"&field2=0";

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.substring(0,response.length()).equals("0")){
                                    Toast.makeText(OpenPecifier.this, "Failed to turn off", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(OpenPecifier.this, "turned off", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(OpenPecifier.this, "Failed completly", Toast.LENGTH_SHORT).show();
                    }
                });
                queue.add(stringRequest);
            }

            }
        });



        onBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                WRITE_KEY=sharedPrefrence.getFavorites(OpenPecifier.this).get(Integer.parseInt(Position)).getWRITE_KEY();
                if(WRITE_KEY==null){
                    ShowPopup(view);
                }else{
                    RequestQueue queue = Volley.newRequestQueue(OpenPecifier.this);
                    String url ="https://api.thingspeak.com/update?api_key="+WRITE_KEY+"&field2=1";

                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if(response.substring(0,response.length()).equals("0")){
                                        Toast.makeText(OpenPecifier.this, "Failed to turn on", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(OpenPecifier.this, "turned on", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(OpenPecifier.this, "Failed completly", Toast.LENGTH_SHORT).show();
                        }
                    });
                    queue.add(stringRequest);
                }



            }
        });

        SeuilEdit = findViewById(R.id.seuilEdit);
        ChangeSeuil = findViewById(R.id.changeSeuil);


        ChangeSeuil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WRITE_KEY=sharedPrefrence.getFavorites(OpenPecifier.this).get(Integer.parseInt(Position)).getWRITE_KEY();
                if(WRITE_KEY==null){
                    ShowPopup(v);
                }else{

                        String testSeuil= SeuilEdit.getText().toString();

                        if(testSeuil.isEmpty() || testSeuil.length()!=2 ){
                            WRITE_KEY_EDIT.setError("Le seuil doit contenir au moin 2 charactères");
                            mRegProgress.dismiss();
                        }else{

                            seuil =testSeuil;
                         RequestQueue queue = Volley.newRequestQueue(OpenPecifier.this);
                        String url ="https://api.thingspeak.com/update?api_key="+WRITE_KEY+"&field3="+seuil;

                        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if(response.substring(0,response.length()).equals("0")){
                                            Toast.makeText(OpenPecifier.this, "Failed to change seuil", Toast.LENGTH_SHORT).show();
                                            SeuilEdit.setError("Valeur n'est pas retenue!");
                                        }else{
                                            Toast.makeText(OpenPecifier.this, "Seuil changé", Toast.LENGTH_SHORT).show();
                                            SeuilEdit.setText(String.valueOf(seuil));
                                            SeuilEdit.setSelected(false);


                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(OpenPecifier.this, "Failed completly", Toast.LENGTH_SHORT).show();
                            }
                        });
                        queue.add(stringRequest);


                    }

                }


            }
        });





        doTheAutoRefresh();
    }


    private void doTheAutoRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                tsChart.setListener(new ThingSpeakLineChart.ChartDataUpdateListener() {
                    @Override
                    public void onChartDataUpdated(long channelId, int fieldId, String title, LineChartData lineChartData, Viewport maxViewport, Viewport initialViewport) {
                        // Set chart data to the LineChartView
                        chartView.setLineChartData(lineChartData);
                        // Set scrolling bounds of the chart
                        chartView.setMaximumViewport(maxViewport);
                        // Set the initial chart bounds
                        chartView.setCurrentViewport(initialViewport);
                    }
                });
                // Load chart data asynchronously
                tsChart.loadChartData();



                ThingSpeakChannel tsChannel = new ThingSpeakChannel(Long.parseLong(ID),READ_KEY);
                tsChannel.setChannelFeedUpdateListener(new ThingSpeakChannel.ChannelFeedUpdateListener() {
                    @Override
                    public void onChannelFeedUpdated(long channelId, String channelName, ChannelFeed channelFeed) {


                        int lastitemfield1index=0;
                        int lastitemfield2index=0;
                        int lastitemfield3index=0;


                        int totalsize = channelFeed.getFeeds().size();
                        int lastitem = totalsize-1;


                        //get last item of field 1----------------------------
                        do{
                            if(channelFeed.getFeeds().get(lastitem).getField1()==null){
                                lastitem--;
                            }else{
                                lastitemfield1index=lastitem;
                                break;
                            }
                        }while(lastitem>0);
                        //------------------------------------


                        //get last item of field 2------------------
                        lastitem = totalsize-1;
                        do{
                            if(channelFeed.getFeeds().get(lastitem).getField2()==null){
                                lastitem--;
                            }else{
                                lastitemfield2index=lastitem;
                                break;
                            }
                        }while(lastitem>0);
                        //--------------------------------------------------

                        //get last item of field 3------------------
                        lastitem = totalsize-1;
                        do{
                            if(channelFeed.getFeeds().get(lastitem).getField3()==null){
                                lastitem--;
                            }else{
                                lastitemfield3index=lastitem;
                                break;
                            }
                        }while(lastitem>0);
                        //--------------------------------------------------


                        //GET BOTH LAST VALUES--------------------------
                        int lastItemValueField1 = Integer.parseInt(channelFeed.getFeeds().get(lastitemfield1index).getField1());
                        int lastItemValueField2 = Integer.parseInt(channelFeed.getFeeds().get(lastitemfield2index).getField2());
                        int lastItemValueField3 = Integer.parseInt(channelFeed.getFeeds().get(lastitemfield3index).getField3());
                        //-------------------------------------------------------


                        //init seuil----------------------------------
                        if(!seuiIsInit){ //voir si le seuil a été initisialisé
                            SeuilEdit.setText(String.valueOf(lastItemValueField3));
                            SeuilEdit.setSelected(false);
                            seuiIsInit=true;
                        }




                        //CHANGE THE TOGGLE
                        if(lastItemValueField2==0){
                            toggle_motor.check(R.id.motor_off);
                           // Toast.makeText(OpenPecifier.this," State was turned 0 from the Database",Toast.LENGTH_LONG).show();
                            arcTemp.setTextColor(Color.parseColor("#d3d3d3"));
                            ifWorking=false;
                        }else if (lastItemValueField2==1){
                            toggle_motor.check(R.id.motor_on);
                         //   Toast.makeText(OpenPecifier.this," State was turned 1 from the Database",Toast.LENGTH_LONG).show();
                            arcTemp.setTextColor(Color.parseColor("#66bb6a"));
                            ifWorking=true;
                        }


                        if(ifWorking) {
                            arcTemp.setProgress(lastItemValueField1);

                            //SET TEMP ON ARC AND CHANGE COLORS
                            if (lastItemValueField1 >= lastItemValueField3) {
                                tsChart.setLineColor(Color.parseColor("#dc4656"));
                                arcTemp.setFinishedStrokeColor(Color.parseColor("#dc4656"));
                                arcTemp.setTextColor(Color.parseColor("#dc4656"));
                                arcTemp.setBottomText("Attention !");

                                voir.setVisibility(View.VISIBLE);
                                danger.setVisibility(View.VISIBLE);




                            } else {
                                tsChart.setLineColor(Color.parseColor("#66bb6a"));
                                arcTemp.setFinishedStrokeColor(Color.parseColor("#66bb6a"));
                                arcTemp.setTextColor(Color.parseColor("#66bb6a"));
                                arcTemp.setBottomText("État normal");

                                voir.setVisibility(View.GONE);
                                danger.setVisibility(View.GONE);

                            }
                            mRegProgress.dismiss();
                        }
                        //----------------------------------------------------------





                    }
                });
                tsChannel.loadChannelFeed();





                //Toast.makeText(OpenPecifier.this, "refrech", Toast.LENGTH_SHORT).show();
                doTheAutoRefresh();
            }
        }, 10000);
    }











    public void ShowPopup(View v){
        MyDialog.setContentView(R.layout.dialog_open_layout);
        mRegProgress= new ProgressDialog(this);

        CircleButton cr= (CircleButton) MyDialog.findViewById(R.id.close_dialog_btn_open);
        cr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDialog.dismiss();
            }
        });

        WRITE_KEY_EDIT = MyDialog.findViewById(R.id.writetext);



        CircleButton done= (CircleButton) MyDialog.findViewById(R.id.yes_dialog_btn_open);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mRegProgress.setTitle("Verification des données");
                mRegProgress.setMessage("Veuillez patienter pendant que nous verifiant votre compte !");


                WRITE_KEY =WRITE_KEY_EDIT.getText().toString();


                if(isInternetAvailable()){
                    mRegProgress.show();
                    if(WRITE_KEY.isEmpty() || WRITE_KEY.length()!=16 ){
                        WRITE_KEY_EDIT.setError("La clé doit contenir 16 charactères");
                        mRegProgress.dismiss();
                    }

                    else{
                        final ArrayList<Pecifier> pecifiers =sharedPrefrence.getFavorites(OpenPecifier.this);
                        sharedPrefrence.removeFavorite(OpenPecifier.this,
                                pecifiers.get(Integer.parseInt(Position)));

                        Pecifier p=new Pecifier();

                        p.setID(Integer.parseInt(ID));
                        p.setBABY_NAME(BABY_NAME);
                        p.setREAD_KEY(READ_KEY);
                        p.setWRITE_KEY(WRITE_KEY);

                        sharedPrefrence.addFavorite(getApplicationContext(),p);
                        mRegProgress.dismiss();
                        Intent i = new Intent(OpenPecifier.this,SuperviserActivity.class);
                        startActivity(i);
                        finish();


                        MyDialog.dismiss();
                        Toast.makeText(OpenPecifier.this, "WriteKey edited Succefully !", Toast.LENGTH_SHORT).show();
                    }


                }
                else{
                    mRegProgress.dismiss();
                    Snackbar mySnackbar = Snackbar.make(findViewById(android.R.id.content), "Pas de connexion internet", Snackbar.LENGTH_LONG);
                    mySnackbar.show();
                }




            }
        });

        MyDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        MyDialog.show();
    }


    public static boolean isInternetAvailable() {

        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();

            return (exitValue == 0);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }


}
