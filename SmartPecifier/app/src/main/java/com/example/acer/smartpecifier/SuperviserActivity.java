package com.example.acer.smartpecifier;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class SuperviserActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MyAdapter adapter;
    List<Pecifier> pecifierList;
    SharedPrefrence sharedPrefrence;

    boolean noitem=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_superviser);

        sharedPrefrence=new SharedPrefrence();
        final ArrayList<Pecifier> pecifiers =sharedPrefrence.getFavorites(this);

            if(pecifiers==null||pecifiers.size()==0){
                setContentView(R.layout.activity_superviser_noitems);
                noitem=true;
            }


        else {
            pecifierList = new ArrayList<>();
            recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

            for (int i = 0; i < pecifiers.size(); i++) {
                pecifierList.add(
                        new Pecifier(
                                pecifiers.get(i).getID(),
                                pecifiers.get(i).getREAD_KEY(),
                                "",
                                pecifiers.get(i).getBABY_NAME()

                        ));
            }

            adapter = new MyAdapter(this, pecifierList);
            recyclerView.setAdapter(adapter);


            //------------------------MAKE SPACE BETWEEN ITEMS-----------------------
            SpacesItemDecoration spacesItemDecoration = new SpacesItemDecoration(20);
            recyclerView.addItemDecoration(spacesItemDecoration);
            //----------------------------------------------------------------------

            //-----------------------LISTNER FOR ITEMS CLICKS--------------------------------------------------
            adapter.setOnItemClickListener(new MyAdapter.ClickListener() {
                @Override
                public void onItemClick(final int position, View v) {

                    CharSequence options[] = new CharSequence[]{"Ouvrir", "Supprimer"};
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SuperviserActivity.this );
                    alertDialogBuilder.setTitle(pecifiers.get(position).getBABY_NAME());

                    alertDialogBuilder.setItems(options, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            if (i == 0){
                              Intent profileIntent = new Intent(SuperviserActivity.this, OpenPecifier.class);
                                profileIntent.putExtra("ID", String.valueOf(pecifiers.get(position).getID()));
                                profileIntent.putExtra("READ_KEY", pecifiers.get(position).getREAD_KEY());
                                profileIntent.putExtra("Name", pecifiers.get(position).getBABY_NAME());
                                profileIntent.putExtra("Position", String.valueOf(position));
                                startActivity(profileIntent);

                                Toast.makeText(SuperviserActivity.this, pecifiers.get(position).toString(), Toast.LENGTH_SHORT).show();
                            }


                            if (i == 1){
                                sharedPrefrence.removeFavorite(SuperviserActivity.this,pecifiers.get(position));


                                Toast.makeText(getApplicationContext(),"Sucette supprimée!",Toast.LENGTH_SHORT).show();
                                Intent refresh = new Intent(SuperviserActivity.this, SuperviserActivity.class);
                                startActivity(refresh);
                                finish();


                            }

                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();





                }

                @Override
                public void onItemLongClick(int position, View v) {
                    //    Toast.makeText(getContext(), "onItemLongClick pos: " + position, Toast.LENGTH_SHORT).show();

                }
            });
            //------------------------------------------------------------------------------------------------

        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(noitem){
            Intent i=new Intent(SuperviserActivity.this,HomeActivity.class);
            startActivity(i);
        }

    }
}