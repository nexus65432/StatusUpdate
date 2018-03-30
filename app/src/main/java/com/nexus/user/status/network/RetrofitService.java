package com.nexus.user.status.network;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.nexus.user.status.datamodel.UserObj;

import java.util.List;

import io.reactivex.Single;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    private Retrofit mRetroFit;

    private UserApi mUserApi;

    private UserRoleApi mUserRoleApi;

    private OkHttpClient mOkHttpClient = new OkHttpClient();

    private static class SingletonRetroFitServiceHelper {
        private static final RetrofitService INSTANCE = new RetrofitService();
    }

    public static RetrofitService getInstance() {
        return SingletonRetroFitServiceHelper.INSTANCE;
    }

    private RetrofitService() {
        final Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        mRetroFit = new Retrofit.Builder().baseUrl(ApiEndpoint.END_POINT)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public Single<List<UserObj>> getUsers() {
        mUserApi = mRetroFit.create(UserApi.class);
        return mUserApi.getUserTeam();
    }

    public Single<JsonObject> getUserRole() {
        mUserRoleApi = mRetroFit.create(UserRoleApi.class);
        return mUserRoleApi.getUserRole();
    }

}
