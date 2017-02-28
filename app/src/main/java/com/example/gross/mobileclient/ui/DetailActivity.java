package com.example.gross.mobileclient.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gross.mobileclient.R;
import com.example.gross.mobileclient.adapter.RewiewAdapter;
import com.example.gross.mobileclient.controller.RestManager;
import com.example.gross.mobileclient.helper.Constants;
import com.example.gross.mobileclient.model.Goods;
import com.example.gross.mobileclient.model.Rewiew;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.gross.mobileclient.helper.Constants.HTTP.IMG_URL;
import static com.example.gross.mobileclient.ui.MainActivity.token;


/**
 * Created by Gross on 14.01.2017.
 */

public class DetailActivity extends AppCompatActivity {

    private TextView mTitle, mText;
    private EditText mReviewText;
    private RatingBar mRatingBar;
    private ImageView mPhoto;
    private RecyclerView mRecyclerView;
    private RestManager mManager;
    private Integer goodsId;
    private RewiewAdapter mRewiewAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuLogin = menu.findItem(R.id.menu_login);
        MenuItem menuLogout = menu.findItem(R.id.menu_logout);

        if (token == null){
            menuLogin.setVisible(true);
            menuLogout.setVisible(false);
        } else {
            menuLogin.setVisible(false);
            menuLogout.setVisible(true);
        }
        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_login){
            Intent intent;
            intent = new Intent(DetailActivity.this, LoginActivity.class);
            startActivity(intent);
        }else if (id == R.id.menu_logout){
            invalidateOptionsMenu();
            token = null;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mReviewText = (EditText) findViewById(R.id.editTextPostReview);
        mRatingBar = (RatingBar) findViewById(R.id.ratingBar);

        Intent intent = getIntent();

        Goods goods = (Goods) intent.getSerializableExtra(Constants.REFERENCE.GOODS);

        configViews();

        mTitle.setText(goods.getTitle());
        mText.setText(goods.getText());
        Picasso.with(getApplicationContext()).load(IMG_URL+goods.getImg()).into(mPhoto);

        goodsId=goods.getId();

        mManager = new RestManager();
        Call<List<Rewiew>> listCall = mManager.getApiService().getRewiewJSON(goodsId.toString());
        listCall.enqueue(new Callback<List<Rewiew>>() {
            @Override
            public void onResponse(Call<List<Rewiew>> call, Response<List<Rewiew>> response) {
                if(response.isSuccessful()){
                    List<Rewiew> rewiewList = response.body();

                    for (int i=0; i<rewiewList.size(); i++){

                        Rewiew rewiew = rewiewList.get(i);
                        mRewiewAdapter.addRewiew(rewiew);
                    }
                }else{
                    int sc = response.code();
                    switch (sc){

                    }
                }
            }

            @Override
            public void onFailure(Call<List<Rewiew>> call, Throwable t) {

            }
        });
    }

    private void configViews() {
        mPhoto = (ImageView) findViewById(R.id.goodsPhoto);
        mTitle = (TextView) findViewById(R.id.goodsTitle);
        mText = (TextView) findViewById(R.id.goodsText);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewRewiew);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        mRewiewAdapter = new RewiewAdapter();
        mRecyclerView.setAdapter(mRewiewAdapter);
    }

    public void onPostReviewClick(View view) {

        if (token != null && mRatingBar.getRating()>0) {

            mManager = new RestManager();

            Call<Integer> rewiew_id = mManager.getApiService().postRate("Token " + token, goodsId.toString(), (int)mRatingBar.getRating(), mReviewText.getText().toString());

            rewiew_id.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {

                }
                @Override
                public void onFailure(Call<Integer> call, Throwable t) {

                }
            });

            Toast toast = Toast.makeText(getApplicationContext(), "Rewiew Add!", Toast.LENGTH_LONG);
            toast.show();

        } else if (token == null){
            Toast toast = Toast.makeText(getApplicationContext(), "Login please", Toast.LENGTH_LONG);
            toast.show();
        } else if (mRatingBar.getRating()<1){
            Toast toast = Toast.makeText(getApplicationContext(), "Need 1 star minimum", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
