package com.ioudebtcalculator.accountlist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ioudebtcalculator.R;
import com.ioudebtcalculator.models.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountListAdapter extends RecyclerView.Adapter {

    public static final int VIEW_TYPE_DEFAULT = 0;

    private List<Account> accountList = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            default:
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.default_account_view, parent, false);
                return new DefaultAccountViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_DEFAULT:
                DefaultAccountViewHolder defaultAccountViewHolder = (DefaultAccountViewHolder)
                        holder;
                if (defaultAccountViewHolder != null) {
                    defaultAccountViewHolder.bindAccount(accountList.get(position));
                }
        }
    }

    @Override
    public int getItemCount() {
        return accountList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_DEFAULT;
    }

    public void setAccounts(List<Account> accountList) {
        //TODO: Some logic to determine which positions changed and notify adapter correctly.
        this.accountList = accountList;
        notifyDataSetChanged();
    }

    public static class DefaultAccountViewHolder extends RecyclerView.ViewHolder {

        private TextView txtAccountName;

        public DefaultAccountViewHolder(View itemView) {
            super(itemView);
            txtAccountName = (TextView) itemView.findViewById(R.id.txtAccountName);
        }

        public void bindAccount(Account account) {
            txtAccountName.setText(account.getName());
        }

    }
}
