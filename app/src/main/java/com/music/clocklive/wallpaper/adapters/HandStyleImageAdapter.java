package com.music.clocklive.wallpaper.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.music.clocklive.wallpaper.R;
import com.music.clocklive.wallpaper.modelclass.HandStyleImageData;

import java.util.ArrayList;

/**
 * Created by sellnews on 25/1/17.
 */

public abstract class HandStyleImageAdapter extends RecyclerView.Adapter<HandStyleImageAdapter.ViewHolder> {
    private ArrayList<HandStyleImageData> lstHandStyleImageList;
    private Context context;

    public HandStyleImageAdapter(ArrayList<HandStyleImageData> lstHandStyleImageList, Context context) {
        this.lstHandStyleImageList = lstHandStyleImageList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rawview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_showimage, parent, false);
        ViewHolder viewHolder = new ViewHolder(rawview);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        HandStyleImageData handStyleImageData=lstHandStyleImageList.get(position);
        holder.ivHandStyleImage.setImageResource(handStyleImageData.getIvHandStyleImage());
        holder.ivHandStyleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onImageClick(position,view);
            }
        });

    }

    @Override
    public int getItemCount() {
        return lstHandStyleImageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivHandStyleImage;
        public ViewHolder(View itemView) {
            super(itemView);
            ivHandStyleImage=(ImageView)itemView.findViewById(R.id.ivShowImage);
        }
    }
    public abstract void onImageClick(int position,View view);

}
