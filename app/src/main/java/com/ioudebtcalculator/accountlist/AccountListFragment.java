package com.ioudebtcalculator.accountlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ioudebtcalculator.App;
import com.ioudebtcalculator.R;
import com.ioudebtcalculator.models.Account;

import java.util.List;

import javax.inject.Inject;

public class AccountListFragment extends Fragment implements AccountListView {

    public static final String KEY_TYPE = "key_type";
    public static final String TYPE_DEFAULT = "type_default";
    public static final String TYPE_LOANS = "type_loans";
    public static final String TYPE_DEBTS = "type_debts";

    @Inject AccountListPresenter presenter;

    private AccountListAdapter accountListAdapter;
    private String type;


    public static AccountListFragment newInstance(String type) {
        AccountListFragment accountListFragment = new AccountListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TYPE, type);
        accountListFragment.setArguments(bundle);
        return accountListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getInstance().getAppComponent().inject(this);
        accountListAdapter = new AccountListAdapter();
        presenter.setView(this);

        Bundle arguments = getArguments();
        if (arguments != null) {
            type = arguments.getString(KEY_TYPE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.accountlist, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rcyAccountList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(accountListAdapter);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.refreshAccounts(type);
    }

    @Override
    public void displayAccounts(List<Account> accountList) {
        accountListAdapter.setAccounts(accountList);
    }
}
