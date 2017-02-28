package com.example.gross.mobileclient.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gross.mobileclient.R;
import com.example.gross.mobileclient.model.Goods;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.example.gross.mobileclient.helper.Constants.HTTP.IMG_URL;

/**
 * Created by Gross on 14.01.2017.
 */

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.Holder> {

    private static final String TAG = GoodsAdapter.class.getSimpleName();
    private final GoodsClickListener mListener;
    private List<Goods> mGoods;

    public GoodsAdapter(GoodsClickListener listener){

        mGoods = new ArrayList<>();
        mListener = listener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item,parent,false);

        return new Holder(row);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        Goods currGoods = mGoods.get(position);

        holder.mTitle.setText(currGoods.getTitle());
        holder.mText.setText(currGoods.getText());
        Picasso.with(holder.itemView.getContext()).load(IMG_URL+currGoods.getImg()).into(holder.mPhoto);

    }

    @Override
    public int getItemCount() {
        return mGoods.size();
    }

    public void addGoods(Goods goods) {
        Log.d(TAG, goods.getImg());
        mGoods.add(goods);
        notifyDataSetChanged();

    }

    public Goods getSelectedGoods(int position) {

        return  mGoods.get(position);

    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView mPhoto;
        private TextView mTitle, mText;

        public Holder(View itemView){
            super(itemView);
            mPhoto = (ImageView) itemView.findViewById(R.id.goodsPhoto);
            mTitle = (TextView) itemView.findViewById(R.id.goodsTitle);
            mText = (TextView) itemView.findViewById(R.id.goodsText);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            mListener.onClick(getLayoutPosition());

        }
    }

    public interface GoodsClickListener{

        void onClick(int position);
    }
}
