package com.nexus.user.status.network;


import com.google.gson.JsonObject;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface UserRoleApi {

    @GET("roles")
    Single<JsonObject> getUserRole();
}
