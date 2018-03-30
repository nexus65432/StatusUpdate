package com.nexus.user.status.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nexus.user.status.BR;
import com.nexus.user.status.R;
import com.nexus.user.status.datamodel.UserObj;

import java.util.List;

/**
 * Binding adapter for user list RecyclerView
 */
public class UsersBindingAdapter extends RecyclerView.Adapter<UsersBindingAdapter.BindingHolder> {

    private Context mContext;
    private List<UserObj> mUserItems;

    public static class BindingHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;

        public BindingHolder(View v) {
            super(v);
            binding = DataBindingUtil.bind(v);
        }

        public ViewDataBinding getBinding() {
            return binding;
        }
    }

    public UsersBindingAdapter(@NonNull Context context, @NonNull List<UserObj> users) {
        mContext = context;
        mUserItems = users;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.user_list_item, parent, false);

        return new BindingHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {

        final UserObj userObj = mUserItems.get(position);
        holder.getBinding().setVariable(BR.user, userObj);
        holder.getBinding().executePendingBindings();
    }

    /**
     * Update the list with the users list
     * @param JsonObjects
     */
    public void updateList(List<UserObj> JsonObjects) {
        mUserItems.addAll(JsonObjects);
    }

    /**
     * Get the list of current users in the list
     */
    public List<UserObj> getUsers() {
        return mUserItems;
    }

    @Override
    public int getItemCount() {
        int retVal = 0;
        if (mUserItems != null) {
            retVal = mUserItems.size();
        }
        return retVal;
    }
}
