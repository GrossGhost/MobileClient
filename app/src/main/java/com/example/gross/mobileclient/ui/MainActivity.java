package com.example.gross.mobileclient.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.gross.mobileclient.R;
import com.example.gross.mobileclient.adapter.GoodsAdapter;
import com.example.gross.mobileclient.controller.RestManager;
import com.example.gross.mobileclient.helper.Constants;
import com.example.gross.mobileclient.model.Goods;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements GoodsAdapter.GoodsClickListener{

    public static String token = null;
    private RecyclerView mRecyclerView;
    private RestManager mManeger;
    private GoodsAdapter mGoodsAdapter;


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
            intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);

        } else if (id == R.id.menu_logout){
            token = null;
            invalidateOptionsMenu();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configViews();

        mManeger = new RestManager();
        Call<List<Goods>> listCall = mManeger.getApiService().getGoodsJSON();
        listCall.enqueue(new Callback<List<Goods>>() {
            @Override
            public void onResponse(Call<List<Goods>> call, Response<List<Goods>> response) {

                if(response.isSuccessful()){
                    List<Goods> goodsList =response.body();

                    for(int i=0; i<goodsList.size(); i++){
                        Goods goods = goodsList.get(i);
                        mGoodsAdapter.addGoods(goods);
                    }
                }else{
                    int sc = response.code();
                    switch (sc){

                    }
                }
            }

            @Override
            public void onFailure(Call<List<Goods>> call, Throwable t) {

            }
        });
    }

    private void configViews(){
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        mGoodsAdapter = new GoodsAdapter(this);
        mRecyclerView.setAdapter(mGoodsAdapter);
    }

    @Override
    public void onClick(int position) {
        Goods selectedGoods = mGoodsAdapter.getSelectedGoods(position);
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(Constants.REFERENCE.GOODS, selectedGoods);
        startActivity(intent);
    }
}
