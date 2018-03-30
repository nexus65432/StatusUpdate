package com.nexus.user.status.ui;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.JsonObject;
import com.nexus.user.status.R;
import com.nexus.user.status.service.SocketInterface;
import com.nexus.user.status.ui.views.UserView;
import com.nexus.user.status.datamodel.StatusUpdate;
import com.nexus.user.status.datamodel.UserObj;
import com.nexus.user.status.network.ApiEndpoint;
import com.nexus.user.status.network.RetrofitService;
import com.nexus.user.status.network.StatusWebSocketListener;
import com.nexus.user.status.utils.JsonWrapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.observers.ResourceObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Request;
import okhttp3.WebSocket;

/**
 * This presenter class is used to process data and return it to UI
 */
public class UserPresenterImpl implements UserPresenter, SocketInterface {

    private static final String TAG = "UserPresenterImpl";

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private UserView mMainView;

    private ResourceObserver<List<UserObj>> mObservable;
    private ResourceObserver<UserObj> mUserObservable;

    private Map<String, Object> mUserRoles;
    private WebSocket mWebSocket;

    public UserPresenterImpl(UserView mMainView) {
        this.mMainView = mMainView;
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach ");
        mUserRoles = null;
        if (mObservable != null && !mObservable.isDisposed()) {
            mObservable.dispose();
        }
        if (mUserObservable != null && !mUserObservable.isDisposed()) {
            mUserObservable.dispose();
        }
    }

    @Override
    public void onPrepareToExit() {
        Log.d(TAG, "onPrepareToExit ");
        mWebSocket.close(StatusWebSocketListener.NORMAL_CLOSURE_STATUS, null);
        RetrofitService.getInstance().getOkHttpClient().dispatcher().executorService().shutdown();
    }

    @Override
    public void fetchUsers() {
        mMainView.loading(R.string.status_loading);
        mCompositeDisposable.add(RetrofitService.getInstance().getUsers()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(processUsersResponse()));
    }

    private DisposableSingleObserver<List<UserObj>> processUsersResponse() {
        return new DisposableSingleObserver<List<UserObj>>() {
            @Override
            public void onSuccess(List<UserObj> value) {
                Log.d(TAG, "processUsersResponse onSuccess ");
                mMainView.updateUserList(value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "processUsersResponse onError ");
                mMainView.loading(R.string.error_message);
            }
        };
    }

    @Override
    public void fetchUsersRole() {
        mCompositeDisposable.add(RetrofitService.getInstance().getUserRole()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(processUserRoleResponse()));
    }

    private DisposableSingleObserver<JsonObject> processUserRoleResponse() {
        return new DisposableSingleObserver<JsonObject>() {
            @Override
            public void onSuccess(JsonObject value) {
                Log.d(TAG, "processUserRoleResponse onSuccess ");
                // TODO: Use ObjectMapper (Having build issues)
                try {
                    mUserRoles = JsonWrapper.toMap(new JSONObject(value.toString()));
                    mMainView.updateUserRoles();
                } catch (JSONException e) {
                    Log.d(TAG, "processUserRoleResponse onError ");
                }
                mMainView.fetchUserRoles();
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "processUserRoleResponse onError ");
                mMainView.fetchUserRoles();
            }
        };
    }

    @Override
    public void updateUserRoles(final @NonNull List<UserObj> userItems) {
        mObservable = Observable.just(mUserRoles)
                .subscribeOn(Schedulers.computation())
                .map(new Function<Map<String, Object>, List<UserObj>>() {
                    @Override
                    public List<UserObj> apply(Map<String, Object> searchResults) {
                        Log.d(TAG, "updateUserRoles");
                        // TODO: Can use rxViews
                        for (UserObj userObj : userItems) {
                            String role = userObj.getUserRole();
                            if (mUserRoles.containsKey(role)) {
                                userObj.setUserRole(searchResults.get(role).toString());
                            }
                        }
                        return userItems;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceObserver<List<UserObj>>() {
                    @Override
                    public void onNext(List<UserObj> values) {
                        Log.d(TAG, "updateNewTweets onNext");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "updateNewTweets onError ");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "updateNewTweets onComplete ");
                    }
                });
    }

    @Override
    public void connectAndListenToStatusUpdates() {
        Log.d(TAG, "connectAndListenToStatusUpdates ");
        Request request = new Request.Builder().url(ApiEndpoint.WEB_SOCKET_PORT).build();
        mWebSocket = RetrofitService.getInstance().getOkHttpClient().newWebSocket(request,
                new StatusWebSocketListener());
    }

    @Override
    public void updateStatusChange(final @NonNull List<UserObj> userItems, final StatusUpdate statusObj) {
        mObservable = Observable.just(statusObj)
                .subscribeOn(Schedulers.computation())
                .map(new Function<StatusUpdate, List<UserObj>>() {
                    @Override
                    public List<UserObj> apply(StatusUpdate statusObj) {
                        Log.d(TAG, "updateStatusChange");
                        String userName = statusObj.getUser();
                        // TODO: Can use rxViews
                        for (UserObj userObj : userItems) {
                            if (TextUtils.equals(userName, userObj.getUserGithub())) {
                                userObj.setUserStatus(statusObj.getState());
                            }
                        }
                        return userItems;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceObserver<List<UserObj>>() {
                    @Override
                    public void onNext(List<UserObj> values) {
                        Log.d(TAG, "updateStatusChange onNext");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "updateStatusChange onError ");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "updateStatusChange onComplete ");
                    }
                });
    }

    @Override
    public void updateNewUser(@NonNull final UserObj newUserObj) {
        mUserObservable = Observable.just(newUserObj)
                .subscribeOn(Schedulers.computation())
                .map(new Function<UserObj, UserObj>() {
                    @Override
                    public UserObj apply(UserObj newUserObject) {
                        Log.d(TAG, "updateNewUser");
                        if (mUserRoles.containsKey(newUserObject.getUserRole())) {
                            newUserObj.setUserRole(mUserRoles.get(newUserObject.getUserRole()).toString());
                        }
                        return newUserObj;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceObserver<UserObj>() {
                    @Override
                    public void onNext(UserObj newUser) {
                        Log.d(TAG, "updateNewUser onNext");
                        if (newUser != null) {
                            mMainView.updateSingleUser(newUser);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "updateNewUser onError ");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "updateNewUser onComplete ");
                    }
                });
    }

}
