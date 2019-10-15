package com.example.acer.smartpecifier;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.FormationsViewHolder> {
    private static ClickListener clickListener;

    private Context mCtx;
    private List<Pecifier> pecifierList;

    public MyAdapter(Context mCtx, List<Pecifier> pecifierList) {
        this.mCtx = mCtx;
        this.pecifierList = pecifierList;
    }


    @NonNull
    @Override
    public FormationsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(mCtx);
        View view = layoutInflater.inflate(R.layout.pecifier_single_layout, viewGroup   ,false);
        FormationsViewHolder holder = new FormationsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FormationsViewHolder formationsViewHolder, int i) {
        Pecifier formationModel = pecifierList.get(i);

        formationsViewHolder.textViewId.setText(String.valueOf(formationModel.getID()));
        formationsViewHolder.textViewName.setText(formationModel.getBABY_NAME());


    }

    @Override
    public int getItemCount() {
        return pecifierList.size();
    }


    class FormationsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {


        TextView textViewId;
        TextView textViewName;


        public FormationsViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewId = itemView.findViewById(R.id.ID_single_layout);
            textViewName = itemView.findViewById(R.id.BabyName_single_layout);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);
        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onItemLongClick(getAdapterPosition(), view);
            return false;
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        MyAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View view);

        void onItemLongClick(int position, View view);
    }


}