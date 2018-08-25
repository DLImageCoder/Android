package com.example.dlimagecoder.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dlimagecoder.R;
import com.example.dlimagecoder.lisenter.UserInfoLisenter;
import com.example.dlimagecoder.netmodel.NetResult;
import com.example.dlimagecoder.netmodel.Tiezi;
import com.example.dlimagecoder.netmodel.UserInfo;
import com.example.dlimagecoder.util.NetUtil;
import com.example.dlimagecoder.util.ToastUtil;

import java.util.List;

import rx.functions.Action1;


/**
 * Created by lenovo on 2017/7/17.
 */

public class TieziAdapter extends RecyclerView.Adapter<TieziAdapter.MyViewHolder> {

    private List<Tiezi> list;
    private Context context;
    private OnItenClickListener listener;
    private Handler handler;

    public interface OnItenClickListener {
        public void onItemClick(View view, int position);

        public void onItemLongClick(View view, int position);

    }

    public void setLisenter(OnItenClickListener lisenter) {
        this.listener = lisenter;
    }

    public TieziAdapter(Context context, List<Tiezi> list, Handler handler) {
        this.list = list;
        this.context = context;
        this.handler = handler;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_rv, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Tiezi item = list.get(position);
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

        holder.tvContent.setText(item.getText());
        holder.tvTime.setText(item.getTime());
        //图片
        holder.ivLarge.setVisibility(View.GONE);
        holder.llImageSmall.setVisibility(View.GONE);
        holder.llImageMsdium.setVisibility(View.GONE);
        List<String> pics = item.getPics();
        switch (pics.size()) {
            case 0:
                break;
            case 1:
                holder.ivLarge.setVisibility(View.VISIBLE);
                Glide.with(context).load(pics.get(0)).into(holder.ivLarge);
                break;
            case 2:
                holder.llImageMsdium.setVisibility(View.VISIBLE);
                Glide.with(context).load(pics.get(0)).into(holder.imageView4);
                Glide.with(context).load(pics.get(1)).into(holder.imageView5);
                break;
            case 3:
                holder.llImageSmall.setVisibility(View.VISIBLE);
                Glide.with(context).load(pics.get(0)).into(holder.imageView1);
                Glide.with(context).load(pics.get(1)).into(holder.imageView2);
                Glide.with(context).load(pics.get(2)).into(holder.imageView3);
                break;
            default:
                break;
        }
        //点赞和评论
        holder.tvApproval.setText(""+item.getApproNum());
        holder.tvComment.setText(""+item.getCommentNum());
        if (item.isApproval()) {
            holder.ivApproval.setBackgroundResource(R.drawable.zan_press);
        } else {
            holder.ivApproval.setBackgroundResource(R.drawable.zan);
        }
        final int type = item.isApproval()? 1:0;
        holder.ivApproval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        NetUtil.getAppUrl().approval(item.getMomentId(), Integer.parseInt(NetUtil.id), type)
                                .subscribe(new Action1<NetResult>() {
                                    @Override
                                    public void call(final NetResult imgProcessResult) {
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (imgProcessResult.isSuccessful()) {
                                                    if (!item.isApproval()){
                                                        item.getApproUsers().add(NetUtil.id);
                                                        holder.ivApproval.setBackgroundResource(R.drawable.zan_press);
                                                        holder.tvApproval.setText(""+(item.getApproNum()));
                                                    } else {
                                                        item.getApproUsers().remove(NetUtil.id);
                                                        holder.ivApproval.setBackgroundResource(R.drawable.zan);
                                                        holder.tvApproval.setText(""+(item.getApproNum()));
                                                    }
                                                } else {
                                                    ToastUtil.showToast("点赞失败");
                                                }
                                            }
                                        });
                                    }
                                });
                    }
                }).start();
            }
        });
        NetUtil.getUserInfo(String.valueOf(item.getUserId()), new UserInfoLisenter() {
            @Override
            public void process(final UserInfo info) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (!TextUtils.isEmpty(info.getHead()))
                            Glide.with(context).load(info.getHead()).into(holder.ivHead);
                        holder.tvName.setText(info.getName());
                    }
                });
            }

            @Override
            public void fail() {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvContent, tvApproval, tvTime, tvComment,tvName;
        ImageView ivHead, ivApproval, imageView1, imageView2, imageView3, imageView4, imageView5, ivLarge;
        LinearLayout llImageSmall,llImageMsdium;


        public MyViewHolder(View itemView) {
            super(itemView);
            tvApproval = (TextView) itemView.findViewById(R.id.tvApproval);
            tvContent = (TextView) itemView.findViewById(R.id.tvContent);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
            tvComment = itemView.findViewById(R.id.tvComment);
            tvName = itemView.findViewById(R.id.tvName);
            ivHead = itemView.findViewById(R.id.ivHead);
            ivApproval = itemView.findViewById(R.id.ivApproval);
            imageView1 = itemView.findViewById(R.id.imageView1);
            imageView2 = itemView.findViewById(R.id.imageView2);
            imageView3 = itemView.findViewById(R.id.imageView3);
            imageView4 = itemView.findViewById(R.id.imageView4);
            imageView5 = itemView.findViewById(R.id.imageView5);
            ivLarge = itemView.findViewById(R.id.ivLarge);

            llImageSmall = itemView.findViewById(R.id.llImageSmall);
            llImageMsdium = itemView.findViewById(R.id.llImageMedium);
        }
    }


}
