package com.nexus.user.status.network;


import com.nexus.user.status.datamodel.UserObj;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface UserApi {

    @GET("team")
    Single<List<UserObj>> getUserTeam();
}
