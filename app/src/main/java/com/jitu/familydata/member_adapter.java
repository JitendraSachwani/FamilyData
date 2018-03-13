package com.jitu.familydata;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import java.util.Collections;
import java.util.List;

/**
 * Created by JITU on 8/14/2017.
 */

public class member_adapter extends RecyclerView.Adapter<member_adapter.MyViewHolder> {

    private Context mContext;
    private List<Member> MemberList;
    private int family_no;
    private int setMembers;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final Context context;
        public TextView title, count;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
            context = itemView.getContext();

        }

        @Override
        public void onClick(View v) {
            //Toast.makeText(context,"The Item Clicked is: "+getPosition(),Toast.LENGTH_SHORT).show();

            if( getPosition() >= setMembers )
            {
                Intent i = new Intent(context, Main3Activity.class)
                        .putExtra("family_no",family_no);
                context.startActivity(i);
            }
        }
    }


    public member_adapter(Context mContext, List<Member> MemberList,int family_no,int setMembers) {
        this.mContext = mContext;
        this.MemberList = MemberList;
        this.family_no = family_no;
        this.setMembers = setMembers;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Member Member = MemberList.get(position);
        holder.title.setText(Member.getName());

        // loading Member cover using Glide library
        Glide.with(mContext).load(Member.getThumbnail()).into(holder.thumbnail);

    }

    @Override
    public int getItemCount() {
        return MemberList.size();
    }
}
