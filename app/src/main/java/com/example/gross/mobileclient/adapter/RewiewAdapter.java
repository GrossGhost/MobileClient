package com.example.gross.mobileclient.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gross.mobileclient.R;
import com.example.gross.mobileclient.model.Rewiew;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gross on 15.01.2017.
 */

public class RewiewAdapter extends RecyclerView.Adapter<RewiewAdapter.Holder> {

    private List<Rewiew> mRewiew;

    public RewiewAdapter(){
        mRewiew = new ArrayList<>();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_rewiew,parent,false);
        return new Holder(row);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        Rewiew currReview = mRewiew.get(position);

        holder.mReviewHead.setText(currReview.getCreatedBy().getUsername()+" at "+currReview.getCreatedAt());
        holder.mReviewRate.setText(""+currReview.getRate());
        holder.mReviewComment.setText(currReview.getText());

    }

    @Override
    public int getItemCount() {
           return mRewiew.size();
    }

    public void addRewiew(Rewiew rewiew) {

        mRewiew.add(rewiew);
        notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder{

        private TextView mReviewHead, mReviewRate, mReviewComment;

        public Holder(View itemView) {

            super(itemView);
            mReviewHead = (TextView) itemView.findViewById(R.id.textViewRateHead);
            mReviewRate = (TextView) itemView.findViewById(R.id.textViewRate);
            mReviewComment = (TextView) itemView.findViewById(R.id.textViewComment);
        }
    }
}
