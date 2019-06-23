package com.marfarijj.buildingmanagement.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.marfarijj.buildingmanagement.Models.Notice;
import com.marfarijj.buildingmanagement.R;

import java.util.List;

public class NoticeListAdapter extends RecyclerView.Adapter<NoticeListAdapter.MyViewHolder> {

    List<Notice> noticeList;
    Context context;

    public NoticeListAdapter(List<Notice> noticeList, Context context) {
        this.noticeList = noticeList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_notice,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

        Notice notice = noticeList.get(i);
        holder.tv_NoticeDesc.setText(notice.getNoticeDescription());
        holder.tvNoticeSub.setText(notice.getNoticeSubject());
        holder.tvNoticeFrom.setText(notice.getNoticeFrom());

        System.out.println("Notice:"+"\n"+notice.getNoticeDescription()+"\n"+notice.getNoticeFrom()+"\n"+notice.getNoticeSubject());
    }

    @Override
    public int getItemCount() {
        return noticeList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvNoticeSub, tv_NoticeDesc, tvNoticeFrom;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_NoticeDesc = itemView.findViewById(R.id.tv_notice_desc);
            tvNoticeSub = itemView.findViewById(R.id.tv_notice_sub);
            tvNoticeFrom = itemView.findViewById(R.id.tv_notice_from);
        }
    }
}
