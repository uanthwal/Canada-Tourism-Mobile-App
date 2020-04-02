package com.developer.tourismapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class ResultPlaceListAdapter extends RecyclerView.Adapter<ResultPlaceListAdapter.MyViewHolder1> {
    private ArrayList<PlacesTO> mDataset;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder1 extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView placeName;
        public TextView placeDesc;
        public ImageView placeImage;
        public Button bookButton;

        public MyViewHolder1(View v) {
            super(v);
            placeName = v.findViewById(R.id.place_name);
            placeDesc = v.findViewById(R.id.place_desc);
            placeImage = v.findViewById(R.id.place_img);
            bookButton = v.findViewById(R.id.book_btn);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ResultPlaceListAdapter(Context mContext, ArrayList<PlacesTO> myDataset, OnItemClickListener onItemClickListener) {
        this.mContext = mContext;
        mDataset = myDataset;
        mOnItemClickListener = onItemClickListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        MyViewHolder1 vh = new MyViewHolder1(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder1 holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        if (null != mDataset && mDataset.size() > 0) {
            holder.placeName.setText(mDataset.get(position).placeName);
            holder.placeDesc.setText(mDataset.get(position).placeDesc);
            Picasso.get().load(mDataset.get(position).imgURL).into(holder.placeImage);
        }
        holder.bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick:  book ticket click action: " + position);
                Intent intent = new Intent(mContext, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                AppGlobalVars.SEARCH_PLACE_ID = mDataset.get(position).placeId;
                mContext.startActivity(intent);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (null != mDataset && mDataset.size() > 0)
            return mDataset.size();
        else
            return 0;
    }
}
