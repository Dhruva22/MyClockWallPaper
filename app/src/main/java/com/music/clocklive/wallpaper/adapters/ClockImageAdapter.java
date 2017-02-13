package com.music.clocklive.wallpaper.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.music.clocklive.wallpaper.R;
import com.music.clocklive.wallpaper.modelclass.ImageData;

import java.util.ArrayList;

/**
 * Created by sellnews on 23/1/17.
 */

public abstract class ClockImageAdapter extends RecyclerView.Adapter<ClockImageAdapter.MyViewHolder> {
    private ArrayList<ImageData> lstClockImageList;
    private Context context;

    public ClockImageAdapter(ArrayList<ImageData> lstClockImageList, Context context) {
        this.lstClockImageList = lstClockImageList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rawview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_showimage, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(rawview);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        ImageData imageData=lstClockImageList.get(position);
        holder.ivShowImage.setImageResource(imageData.getIvImage());
        holder.ivShowImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onImageClick(position,view);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstClockImageList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivShowImage;
        public MyViewHolder(View itemView) {
            super(itemView);
            ivShowImage=(ImageView)itemView.findViewById(R.id.ivShowImage);
        }
    }
    public abstract void onImageClick(int position,View view);
}
