package com.ioudebtcalculator.newtransaction;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.ioudebtcalculator.App;
import com.ioudebtcalculator.R;
import com.ioudebtcalculator.models.Account;
import com.ioudebtcalculator.models.Transaction;
import com.ioudebtcalculator.repository.DataRepositoryListener;

import java.util.List;

import javax.inject.Inject;

public class NewTransactionFragment extends Fragment implements NewTransactionView {

    @Inject NewTransactionPresenter presenter;

    public static final String KEY_ACCOUNT_ID = "accountId";

    /**
     * Used to store the account id value passed into this fragment within the arguments bundle.
     * Can be null meaning that no account is currently associated with this fragment.
     */
    private Integer accountId;
    private Spinner accountNameSpinner;
    private Spinner currencySpinner;
    private RadioGroup borrowOrLoanRadioGroup;
    private RadioButton loanRadioButton;
    private RadioButton borrowRadioButton;
    private EditText transactionAmount;
    private CoordinatorLayout fabLayout;

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

    private View.OnClickListener saveTransactionListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            presenter.validateInputAndSave();
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
        App.getInstance().getAppComponent().inject(this);
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
        currencySpinner = (Spinner) view.findViewById(R.id.spnCurrency);
        borrowOrLoanRadioGroup = (RadioGroup) view.findViewById(R.id.rdgBorrowOrLoan);
        borrowRadioButton = (RadioButton) view.findViewById(R.id.rdbBorrow);
        loanRadioButton = (RadioButton) view.findViewById(R.id.rdbLoan);
        transactionAmount = (EditText) view.findViewById(R.id.edtAmount);
        fabLayout = (CoordinatorLayout) view.findViewById(R.id.fabLayout);
        FloatingActionButton floatingActionButton = (FloatingActionButton) view
                .findViewById(R.id.saveTransactionFab);
        floatingActionButton.setOnClickListener(saveTransactionListener);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.getAccounts(dataRepositoryListener);
        ArrayAdapter<String> currencyAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item,
                presenter.getAvailableCurrencies());
        currencySpinner.setAdapter(currencyAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.setView(this);
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        Snackbar.make(fabLayout, errorMessage, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void close() {
    }

    @Override
    public int getBorrowOrLoanCheckedId() {
        return borrowOrLoanRadioGroup.getCheckedRadioButtonId();
    }

    @Override
    public String getTransactionAmountEntered() {
        return transactionAmount.getText().toString();
    }

    @Override
    public void setBorrowOrLoanError(String error){
        loanRadioButton.setError(error);
        borrowRadioButton.setError(error);
    }

    @Override
    public void setTransactionAmountError(String error) {
        transactionAmount.setError(error);
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
