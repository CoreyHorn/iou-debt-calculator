package com.ioudebtcalculator.newtransaction;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
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
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.ioudebtcalculator.App;
import com.ioudebtcalculator.R;
import com.ioudebtcalculator.models.Account;
import com.ioudebtcalculator.models.Transaction;
import com.ioudebtcalculator.newaccount.NewAccountActivity;
import com.ioudebtcalculator.repository.DataRepositoryListener;

import java.util.List;

import javax.inject.Inject;

public class NewTransactionFragment extends Fragment implements NewTransactionView {

    @Inject NewTransactionPresenter presenter;

    public static final String KEY_ACCOUNT_ID = "accountId";
    public static final int NEW_ACCOUNT_REQUEST = 1;

    /**
     * Used to store the account id value passed into this fragment within the arguments bundle.
     * Can be null meaning that no account is currently associated with this fragment.
     */
    private Integer accountId;
    private Spinner spnAccountName;
    private Spinner spnCurrency;
    private RadioGroup rdgBorrowOrLoan;
    private RadioButton rdbLoan;
    private RadioButton rdbBorrow;
    private EditText edtAmount;
    private CoordinatorLayout fabLayout;
    private ImageButton imbNewAccount;

    private boolean receivedAccountResult = false;
    private List<Account> accounts;

    private DataRepositoryListener dataRepositoryListener = new DataRepositoryListener() {
        @Override
        public void onAccountListAvailable(List<Account> accounts) {
            if (spnAccountName != null) {
                setAccounts(accounts);
                AccountNameSpinnerAdapter adapter = new AccountNameSpinnerAdapter(accounts,
                        getContext());
                spnAccountName.setAdapter(adapter);
                if (receivedAccountResult) {
                    spnAccountName.setSelection(adapter.getCount());
                } else {
                    setSpinnerToDefaultAccount();
                }
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

    private View.OnClickListener clearRadioErrorListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            rdbLoan.setError(null);
            rdbBorrow.setError(null);
        }
    };

    private View.OnClickListener newAccountListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), NewAccountActivity.class);
            startActivityForResult(intent, NEW_ACCOUNT_REQUEST);
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
        ((App) getActivity().getApplication()).getAppComponent().inject(this);
        Bundle arguments = getArguments();
        if (arguments != null) {
            accountId = arguments.getInt(KEY_ACCOUNT_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_new_transaction, container, false);
        spnAccountName = (Spinner) view.findViewById(R.id.spnAccountName);
        spnCurrency = (Spinner) view.findViewById(R.id.spnCurrency);
        rdgBorrowOrLoan = (RadioGroup) view.findViewById(R.id.rdgBorrowOrLoan);
        rdbBorrow = (RadioButton) view.findViewById(R.id.rdbBorrow);
        rdbLoan = (RadioButton) view.findViewById(R.id.rdbLoan);
        edtAmount = (EditText) view.findViewById(R.id.edtAmount);
        imbNewAccount = (ImageButton) view.findViewById(R.id.btnNewAccount);
        fabLayout = (CoordinatorLayout) view.findViewById(R.id.fabLayout);
        FloatingActionButton floatingActionButton = (FloatingActionButton) view
                .findViewById(R.id.saveTransactionFab);
        floatingActionButton.setOnClickListener(saveTransactionListener);
        rdbBorrow.setOnClickListener(clearRadioErrorListener);
        rdbLoan.setOnClickListener(clearRadioErrorListener);
        imbNewAccount.setOnClickListener(newAccountListener);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayAdapter<String> currencyAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item,
                presenter.getAvailableCurrencies());
        spnCurrency.setAdapter(currencyAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.getAccounts(dataRepositoryListener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == NEW_ACCOUNT_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                receivedAccountResult = true;
            }
        }
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        Snackbar.make(fabLayout, errorMessage, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void close() {
        getActivity().finish();
    }

    @Override
    public int getBorrowOrLoanCheckedId() {
        return rdgBorrowOrLoan.getCheckedRadioButtonId();
    }

    @Override
    public String getAmountEntered() {
        return edtAmount.getText().toString();
    }

    @Override
    public int getSelectedAccountId() {
        int selectedPosition = spnAccountName.getSelectedItemPosition();
        if (accounts != null && selectedPosition != -1) {
            return accounts.get(selectedPosition).getId();
        }
        return -1;
    }

    @Override
    public void setBorrowOrLoanError(){
        Resources resources = getResources();
        rdbLoan.setError(resources
                .getString(R.string.error_borrow_or_loan));
        rdbBorrow.setError(resources
                .getString(R.string.error_borrow_or_loan));
    }

    @Override
    public void setTransactionAmountError() {
        edtAmount.setError(getResources()
            .getString(R.string.error_must_enter_amount));
    }

    private void setSpinnerToDefaultAccount() {
        if (accountId != null) {
            for (int position = 0;
                 position < spnAccountName.getAdapter().getCount();
                 position++) {
                if (spnAccountName.getAdapter().getItemId(position) == accountId) {
                    spnAccountName.setSelection(position);
                    return;
                }
            }
        }
    }

    private void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}