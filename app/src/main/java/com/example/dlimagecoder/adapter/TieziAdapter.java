package com.example.dlimagecoder.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dlimagecoder.R;
import com.example.dlimagecoder.netmodel.Tiezi;

import java.util.List;


/**
 * Created by lenovo on 2017/7/17.
 */

public class TieziAdapter extends RecyclerView.Adapter<TieziAdapter.MyViewHolder> {

    private List<Tiezi> list;
    private Context context;
    private OnItenClickListener listener;

    public interface OnItenClickListener {
        public void onItemClick(View view, int position);

        public void onItemLongClick(View view, int position);

    }

    public void setLisenter(OnItenClickListener lisenter) {
        this.listener = lisenter;
    }

    public TieziAdapter(Context context, List<Tiezi> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_rv, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Tiezi item = list.get(position);
        if (listener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(view, position);
                }

            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.onItemLongClick(view, position);
                    return false;
                }
            });
        }

        List<String> pics = item.getPics();
        switch (pics.size()) {
            case 0:
                break;
            case 1:
                Glide.with(context).load(pics.get(0)).into(holder.ivLarge);
                break;
            case 2:
                Glide.with(context).load(pics.get(0)).into(holder.imageView4);
                Glide.with(context).load(pics.get(1)).into(holder.imageView5);
                break;
            case 3:
                Glide.with(context).load(pics.get(0)).into(holder.imageView1);
                Glide.with(context).load(pics.get(0)).into(holder.imageView2);
                Glide.with(context).load(pics.get(0)).into(holder.imageView3);
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvContent, tvApproval, tvTime;
        ImageView ivHead, ivComment, imageView1, imageView2, imageView3, imageView4, imageView5, ivLarge;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvApproval = (TextView) itemView.findViewById(R.id.tvApproval);
            tvContent = (TextView) itemView.findViewById(R.id.tvContent);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
            ivHead = itemView.findViewById(R.id.ivHead);
            ivComment = itemView.findViewById(R.id.ivComment);
            imageView1 = itemView.findViewById(R.id.imageView1);
            imageView2 = itemView.findViewById(R.id.imageView2);
            imageView3 = itemView.findViewById(R.id.imageView3);
            imageView4 = itemView.findViewById(R.id.imageView4);
            imageView5 = itemView.findViewById(R.id.imageView5);
            ivLarge = itemView.findViewById(R.id.ivLarge);
        }
    }


}
