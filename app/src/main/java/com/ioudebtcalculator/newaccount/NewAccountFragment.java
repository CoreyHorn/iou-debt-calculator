package com.ioudebtcalculator.newaccount;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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

import javax.inject.Inject;

public class NewAccountFragment extends Fragment implements NewAccountView {

    @Inject NewAccountPresenter presenter;

    private RadioGroup rdgBorrowOrLoan;
    private RadioButton rdbBorrow;
    private RadioButton rdbLoan;
    private EditText edtAccountName;
    //TODO: Implement Contact adding and attach to this button.
    private ImageButton btnAddContact;
    private EditText edtAmount;
    private Spinner spnCurrency;
    private EditText edtDescription;
    private FloatingActionButton btnSaveAccount;

    private View.OnClickListener saveAccountListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            presenter.validateInputAndSave();
        }
    };

    private View.OnClickListener clearRadioErrorListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            rdbBorrow.setError(null);
            rdbLoan.setError(null);
        }
    };

    public static NewAccountFragment newInstance() {
        return new NewAccountFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getActivity().getApplication()).getAppComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.newaccount, container, false);

        rdgBorrowOrLoan = (RadioGroup) view.findViewById(R.id.rdgBorrowOrLoan);
        rdbBorrow = (RadioButton) view.findViewById(R.id.rdbBorrow);
        rdbLoan = (RadioButton) view.findViewById(R.id.rdbLoan);
        edtAccountName = (EditText) view.findViewById(R.id.edtAccountName);
        btnAddContact = (ImageButton) view.findViewById(R.id.btnAddContact);
        edtAmount = (EditText) view.findViewById(R.id.edtAmount);
        spnCurrency = (Spinner) view.findViewById(R.id.spnCurrency);
        edtDescription = (EditText) view.findViewById(R.id.edtDescription);
        btnSaveAccount = (FloatingActionButton) view.findViewById(R.id.saveAccountFab);

        btnSaveAccount.setOnClickListener(saveAccountListener);
        rdbBorrow.setOnClickListener(clearRadioErrorListener);
        rdbLoan.setOnClickListener(clearRadioErrorListener);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spnCurrency.setAdapter(new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_item,
                presenter.getAvailableCurrencies()
                ));
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.setView(this);
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
    public String getNameEntered() {
        return edtAccountName.getText().toString();
    }

    @Override
    public String getCurrencyCode() {
        return spnCurrency.getSelectedItem().toString();
    }

    @Override
    public String getDescriptionEntered() {
        return edtDescription.getText().toString();
    }

    @Override
    public void setAmountError() {
        edtAmount.setError(getResources()
            .getString(R.string.error_must_enter_amount));
    }

    @Override
    public void setBorrowOrLoanError() {
        String error = getResources().getString(R.string.error_borrow_or_loan);
        rdbBorrow.setError(error);
        rdbLoan.setError(error);
    }

    @Override
    public void setAccountNameError() {
        edtAccountName.setError(getResources().getString(R.string.error_must_enter_account_name));
    }
}
