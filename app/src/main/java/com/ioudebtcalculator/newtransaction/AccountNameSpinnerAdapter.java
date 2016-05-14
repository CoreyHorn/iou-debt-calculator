package com.ioudebtcalculator.newtransaction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ioudebtcalculator.R;
import com.ioudebtcalculator.models.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountNameSpinnerAdapter extends BaseAdapter {

    private List<Account> accounts = new ArrayList<>();
    private Context context;

    public AccountNameSpinnerAdapter(List<Account> accounts, Context context) {
        this.accounts = accounts;
        this.context = context;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return accounts.size();
    }

    @Override
    public Object getItem(int position) {
        return accounts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return accounts.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.spinner_row_account_name, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(R.id.txtAccountName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.textView.setText(accounts.get(position).getName());
        return convertView;
    }

    static class ViewHolder {
        private TextView textView;
    }
}
