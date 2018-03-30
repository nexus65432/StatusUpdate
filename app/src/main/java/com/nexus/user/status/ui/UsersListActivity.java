package com.nexus.user.status.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.nexus.user.status.R;
import com.nexus.user.status.ui.views.UserView;
import com.nexus.user.status.events.NewUserEvent;
import com.nexus.user.status.events.SocketEvents;
import com.nexus.user.status.adapter.UsersBindingAdapter;
import com.nexus.user.status.datamodel.UserObj;
import com.nexus.user.status.events.UserStatusChangeEvent;
import com.nexus.user.status.service.ProcessSocketData;
import com.nexus.user.status.utils.NetworkUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class UsersListActivity extends BaseActivity implements UserView {

    private static final String TAG = "UsersListActivity";

    private UserPresenterImpl mMainPresenterImpl;

    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private TextView mDefaultMessage;
    private RecyclerView mTweetsList;
    private UsersBindingAdapter mUsersAdapter;
    private List<UserObj> mUserItems = new ArrayList<>();

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, UsersListActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainPresenterImpl = new UserPresenterImpl(this);
        setupToolbar();
        mDefaultMessage = findViewById(R.id.status_loading);
        setUpRecyclerView();
    }

    private void setupToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbarTitle = findViewById(R.id.toolbar_title);
        mToolbarTitle.setText(getString(R.string.actionbar_title));
    }

    /**
     * Setup recyclerView
     */
    private void setUpRecyclerView() {
        mUsersAdapter = new UsersBindingAdapter(this, mUserItems);
        mTweetsList = findViewById(R.id.listView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mTweetsList.setItemAnimator(new DefaultItemAnimator());
        mTweetsList.setLayoutManager(layoutManager);
        mTweetsList.setAdapter(mUsersAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        if (NetworkUtils.getInstance().isNetworkAvailable(this)) {
            mMainPresenterImpl.fetchUsers();
        } else {
            onError(R.string.error_network_unavailable);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMainPresenterImpl.onPrepareToExit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainPresenterImpl.onDetach();
    }

    /**
     * Listen to messages from socket
     * @param event Packet
     */
    @Subscribe
    public void onMessageEvent(SocketEvents event) {
        Log.d(TAG, "onMessageEvent for  SocketEvents");
        if (event != null && event.getMessage() != null) {
            Intent intent = new Intent(UsersListActivity.this, ProcessSocketData.class);
            intent.putExtra(ProcessSocketData.NEW_MESSAGE, event.getMessage());
            startService(intent);
        }
    }

    /**
     * Listen to user status change events
     * @param event
     */
    @Subscribe
    public void onMessageEvent(UserStatusChangeEvent event) {
        Log.d(TAG, "onMessageEvent for  UserStatusChangeEvent");
        if (event != null && event.getStatusUpdate() != null) {
            mMainPresenterImpl.updateStatusChange(mUsersAdapter.getUsers(), event.getStatusUpdate());
        }
    }

    /**
     * Listen to new user events
     * @param event
     */
    @Subscribe
    public void onMessageEvent(NewUserEvent event) {
        Log.d(TAG, "onMessageEvent for  NewUserEvent");
        if (event != null && event.getNewUserObj() != null) {
            mMainPresenterImpl.updateNewUser(event.getNewUserObj().getUser());
        }
    }

    private void updateMessage(boolean show) {
        mDefaultMessage.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void updateUserList(@NonNull List<UserObj> results) {
        Log.d(TAG, "updateUserList ");
        if (results != null && results.size() > 0) {
            updateMessage(false);
            mUsersAdapter.updateList(results);
            mUsersAdapter.notifyDataSetChanged();
        }
        mMainPresenterImpl.fetchUsersRole();
    }

    @Override
    public void fetchUserRoles() {
        Log.d(TAG, "fetchUserRoles ");
        if (NetworkUtils.getInstance().isNetworkAvailable(this)) {
            mMainPresenterImpl.connectAndListenToStatusUpdates();
        }
    }

    @Override
    public void updateUserRoles() {
        Log.d(TAG, "updateUserRoles ");
        if (NetworkUtils.getInstance().isNetworkAvailable(this)) {
            mMainPresenterImpl.updateUserRoles(mUsersAdapter.getUsers());
        }
    }

    @Override
    public void updateSingleUser(@NonNull UserObj newUser) {
        Log.d(TAG, "updateSingleUser ");
        // Add new user to list
        mUserItems.add(newUser);
        // Don't refresh entire list, only notify of new item inserted
        mUsersAdapter.notifyItemInserted(mUserItems.size() - 1);
    }

    @Override
    public void loading(@StringRes int resource) {
        Log.d(TAG, "loading ");
        mDefaultMessage.setText(getString(resource));
    }

    @Override
    public void onError(@StringRes int resource) {
        Log.d(TAG, "onError ");
        mDefaultMessage.setText(getString(resource));
    }

}
