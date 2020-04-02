package com.developer.tourismapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.TextViewHolder> {

    ArrayList<myModel> list = new ArrayList<myModel>();
    Context context;
    String source, destination, t_date;

    public RecyclerAdapter(ArrayList<myModel> list, Context context, String source, String destination, String t_date) {
        this.list = list;
        this.context = context;
        this.source = source;
        this.destination = destination;
        this.t_date = t_date;
    }

    @NonNull
    @Override
    public TextViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item1, viewGroup, false);
        TextViewHolder textViewHolder = new TextViewHolder(view);
        return textViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TextViewHolder viewHolder, final int i) {
        viewHolder.travel_name.setText(list.get(i).travel_name);
        viewHolder.travel_mode.setText(list.get(i).travel_mode);
        viewHolder.mode_number.setText(list.get(i).mode_number);
        viewHolder.price_travel.setText("$" + list.get(i).price_travel);
        viewHolder.btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String travel_name = list.get(i).travel_name;
                String travel_mode = list.get(i).travel_mode;
                String mode_number = list.get(i).mode_number;
                String price_travel = list.get(i).price_travel;
                String mode_company = list.get(i).mode_company;
                String mode_id = list.get(i).mode_id;

                Intent i = new Intent(context, CardDetails.class);
                i.putExtra("TRAVEL_NAME", travel_name);
                i.putExtra("TRAVEL_MODE", travel_mode);
                i.putExtra("MODE_NUMBER", mode_number);
                i.putExtra("PRICE_TRAVEL", price_travel);
                i.putExtra("MODE_COMPANY", mode_company);
                i.putExtra("MODE_ID", mode_id);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class TextViewHolder extends RecyclerView.ViewHolder {
        TextView travel_name;
        TextView travel_mode;
        TextView mode_number;
        TextView price_travel;
        Button btnBook;

        public TextViewHolder(@NonNull View itemView) {
            super(itemView);
            travel_name = (TextView) itemView.findViewById(R.id.travelName);
            travel_mode = (TextView) itemView.findViewById(R.id.mode);
            mode_number = (TextView) itemView.findViewById(R.id.modeNumber);
            price_travel = (TextView) itemView.findViewById(R.id.price);
            btnBook = (Button) itemView.findViewById(R.id.bookNow);
        }
    }

}
