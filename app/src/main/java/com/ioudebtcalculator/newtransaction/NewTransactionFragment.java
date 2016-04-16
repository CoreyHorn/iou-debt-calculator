package com.ioudebtcalculator.newtransaction;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.ioudebtcalculator.R;
import com.ioudebtcalculator.models.Account;
import com.ioudebtcalculator.models.Transaction;
import com.ioudebtcalculator.repository.DataRepositoryListener;

import java.util.List;

public class NewTransactionFragment extends Fragment implements NewTransactionView {

    public static final String KEY_ACCOUNT_ID = "accountId";

    /**
     * Used to store the account id value passed into this fragment within the arguments bundle.
     * Can be null meaning that no account is currently associated with this fragment.
     */
    private Integer accountId;
    private Spinner accountNameSpinner;

    private DataRepositoryListener dataRepositoryListener = new DataRepositoryListener() {
        @Override
        public void onAccountListAvailable(List<Account> accounts) {
            if (accountNameSpinner != null) {
                AccountNameSpinnerAdapter adapter = new AccountNameSpinnerAdapter(accounts,
                        getContext());
                accountNameSpinner.setAdapter(adapter);
                setSpinnerToDefaultAccount();
            }
        }

        @Override
        public void onTransactionListAvailable(List<Transaction> transactions) {

        }
    };

    /**
     * Creates and returns a new instance of NewTransactionFragment.
     * @param accountId account id associated with this new transaction. Can be null
     * @return instance of NewTransactionFragment
     */
    public static NewTransactionFragment newInstance(Integer accountId) {
        NewTransactionFragment newTransactionFragment = new NewTransactionFragment();
        if (accountId != null) {
            Bundle bundle = new Bundle();
            bundle.putInt(KEY_ACCOUNT_ID, accountId);
            newTransactionFragment.setArguments(bundle);
        }
        return newTransactionFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            accountId = arguments.getInt(KEY_ACCOUNT_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.newtransaction, container, false);
        accountNameSpinner = (Spinner) view.findViewById(R.id.spnAccountName);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void showErrorMessage(String errorMessage) {

    }

    @Override
    public void close() {
    }

    private void setSpinnerToDefaultAccount() {
        if (accountId != null) {
            for (int position = 0;
                 position < accountNameSpinner.getAdapter().getCount();
                 position++) {
                if (accountNameSpinner.getAdapter().getItemId(position) == accountId) {
                    accountNameSpinner.setSelection(position);
                    return;
                }
            }
        }
    }
}
