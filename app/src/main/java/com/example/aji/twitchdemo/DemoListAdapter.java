package com.example.aji.twitchdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aji on 16/3/2017.
 *
 */

class DemoListAdapter extends RecyclerView.Adapter<DemoListAdapter.ViewHolder> {

    private Context mContext;
    private static OnItemClickListener sListener;
    private List<DemoEntity> mList = new ArrayList<>();

    DemoListAdapter(Context context, List<DemoEntity> demoEntityList) {
        mContext = context;
        setEntityList(demoEntityList);
    }

    void setEntityList(List<DemoEntity> demoEntityList) {
        mList = demoEntityList;
        notifyDataSetChanged();
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        sListener = listener;
    }

    DemoEntity getObject(int position) {
        return mList.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        mContext = parent.getContext();
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View view = mInflater.inflate(R.layout.row_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DemoEntity entity = getObject(position);
        holder.ivTwitch.setImageResource(entity.imageRes);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ivTwitch;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);

            ivTwitch = (ImageView) itemView.findViewById(R.id.iv_twitch);
        }

        @Override
        public void onClick(View v) {
            sListener.onItemClick(v, getAdapterPosition());
        }
    }

    interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

}
