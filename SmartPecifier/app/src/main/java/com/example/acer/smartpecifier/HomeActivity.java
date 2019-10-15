package com.example.acer.smartpecifier;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.macroyau.thingspeakandroid.ThingSpeakChannel;
import com.macroyau.thingspeakandroid.model.ChannelFeed;

import java.io.IOException;
import java.util.List;


import at.markushi.ui.CircleButton;

public class HomeActivity extends AppCompatActivity {
    private EditText idtext,keytext,babyname;
    private Dialog MyDialog;
    private MaterialButton AjouterBtn,SuperViserBtn,params;
    private String ChannelName="";
    private ProgressDialog mRegProgress;
    private boolean cond1=false;

    String ID,READ_KEY,Name;

    SharedPrefrence sharedPrefrence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sharedPrefrence =new SharedPrefrence();

        MyDialog = new Dialog(this);

        params=findViewById(R.id.params);
        params.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, ParamsActivity.class);
                startActivity(i);
            }
        });


        SuperViserBtn=findViewById(R.id.superviser);
        SuperViserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this,SuperviserActivity.class);
                startActivity(i);
            }
        });

        AjouterBtn=findViewById(R.id.showadd);
        AjouterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPopup(v);
            }
        });


    }


    public void ShowPopup(View v){
        MyDialog.setContentView(R.layout.dialog_account_layout);
        mRegProgress= new ProgressDialog(this);

        CircleButton cr= (CircleButton) MyDialog.findViewById(R.id.close_dialog_btn);
        cr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDialog.dismiss();
            }
        });
        final MaterialButton ChercherBtn = MyDialog.findViewById(R.id.chercher);

        idtext = MyDialog.findViewById(R.id.idtext);
        keytext = MyDialog.findViewById(R.id.keytext);
        babyname = MyDialog.findViewById(R.id.babyname);


        ChercherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRegProgress.setTitle("Verification des données");
                mRegProgress.setMessage("Veuillez patienter pendant que nous verifiant votre compte !");


                 ID =idtext.getText().toString();
                 READ_KEY =keytext.getText().toString();


                if(isInternetAvailable()){
                    mRegProgress.show();
                        if(ID.isEmpty()|| ID.length()!=6 ){
                            idtext.setError("L'identifiant doit contenir 6 charactères");
                            mRegProgress.dismiss();
                        }

                        else if(READ_KEY.isEmpty() ||  READ_KEY.length()!=16){
                            keytext.setError("La clé doit contenir 16 charactères");
                            mRegProgress.dismiss();
                        }

                        else{
                            ThingSpeakChannel tsChannel = new ThingSpeakChannel(Long.parseLong(ID),READ_KEY);
                            tsChannel.setChannelFeedUpdateListener(new ThingSpeakChannel.ChannelFeedUpdateListener() {
                            @Override
                             public void onChannelFeedUpdated(long channelId, String channelName, ChannelFeed channelFeed) {
                            ChannelName = channelName;
                             }
                            });
                            tsChannel.loadChannelFeed();

                             if(ChannelName.equals("Smart Pacifier")){
                                mRegProgress.dismiss();
                                Toast.makeText(HomeActivity.this, "Succées...", Toast.LENGTH_SHORT).show();


                                 idtext.setEnabled(false);
                                 idtext.setInputType(InputType.TYPE_NULL);


                                 keytext.setEnabled(false);
                                 keytext.setInputType(InputType.TYPE_NULL);

                                 ChercherBtn.setVisibility(View.GONE);

                                 babyname.setVisibility(View.VISIBLE);
                                 cond1=true;


                             }else{
                               Toast.makeText(HomeActivity.this, "Mauvaise combinaison !", Toast.LENGTH_SHORT).show();
                               mRegProgress.dismiss();
                             }
                            }


                }
                    else{
                    mRegProgress.dismiss();
                    Snackbar mySnackbar = Snackbar.make(findViewById(android.R.id.content), "Pas de connexion internet", Snackbar.LENGTH_LONG);
                    mySnackbar.show();
                }


            }
        });

        CircleButton done= (CircleButton) MyDialog.findViewById(R.id.yesbtn);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(cond1){
                    Name =babyname.getText().toString();
                   if(!Name.isEmpty()){
                       Toast.makeText(HomeActivity.this, "Ready", Toast.LENGTH_SHORT).show();
                        sharedPrefrence =new SharedPrefrence();

                       Pecifier p=new Pecifier();

                       p.setID(Integer.parseInt(ID));
                       p.setBABY_NAME(Name);
                       p.setREAD_KEY(READ_KEY);

                       if(!checkFavoriteItem(p,getApplicationContext())){
                           sharedPrefrence.addFavorite(getApplicationContext(),p);
                           Intent i = new Intent(HomeActivity.this,SuperviserActivity.class);
                           startActivity(i);

                       }else{
                           Toast.makeText(HomeActivity.this, "Sucette existe déja", Toast.LENGTH_SHORT).show();
                       }



                   }else{
                       babyname.setError("Le nom ne peut pas être vide");
                   }

               }else{
                   Toast.makeText(HomeActivity.this, "Veuillez remplir les champs", Toast.LENGTH_SHORT).show();
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


    public boolean checkFavoriteItem(Pecifier checkProduct, Context ctx) {
        boolean check = false;
        List<Pecifier> favorites = sharedPrefrence.getFavorites(ctx);
        if (favorites != null) {
            for (Pecifier product : favorites) {
                if (product.equals(checkProduct)) {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }
}
