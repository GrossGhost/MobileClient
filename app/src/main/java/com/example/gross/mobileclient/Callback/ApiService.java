package com.example.gross.mobileclient.Callback;

import com.example.gross.mobileclient.model.Goods;

import com.example.gross.mobileclient.model.RegistrationResponse;
import com.example.gross.mobileclient.model.Rewiew;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Gross on 14.01.2017.
 */

public interface ApiService {

    @GET("/api/products/")
    Call<List<Goods>> getGoodsJSON();

    @GET("/api/reviews/{goods_id}")
    Call<List<Rewiew>> getRewiewJSON(@Path("goods_id") String goodsId);

    @FormUrlEncoded
    @POST("/api/register/")
    Call<RegistrationResponse> registerUser(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST("/api/login/")
    Call<RegistrationResponse> loginUser(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST("/api/reviews/{goods_id}")
    Call<Integer> postRate(@Header("Authorization") String token,
                           @Path("goods_id") String goodsId,
                           @Field("rate") int rate,
                           @Field("text") String text);
}
