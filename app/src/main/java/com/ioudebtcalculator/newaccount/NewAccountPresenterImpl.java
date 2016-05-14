package com.ioudebtcalculator.newaccount;

import com.ioudebtcalculator.App;
import com.ioudebtcalculator.models.Account;
import com.ioudebtcalculator.repository.DataRepository;

import javax.inject.Inject;

public class NewAccountPresenterImpl implements NewAccountPresenter {

    @Inject
    DataRepository dataRepository;

    private NewAccountView view;

    public NewAccountPresenterImpl() {
        //TODO: Change all Presenters to use Constructor injection.
        App.getInstance().getAppComponent().inject(this);
    }

    @Override
    public void setView(NewAccountFragment view) {
        this.view = view;
    }

    @Override
    public void setContact() {

    }

    @Override
    public void validateInputAndSave() {
        boolean error = false;
        if (view.getBorrowOrLoanCheckedId() == -1) {
            view.setBorrowOrLoanError();
            error = true;
        }
        if (view.getNameEntered().equals("")) {
            view.setAccountNameError();
            error = true;
        }
        if (view.getAmountEntered().equals("")) {
            view.setAmountError();
            error = true;
        }
        if (!error) {
            Account account = new Account(
                    view.getAmountEntered(),
                    view.getCurrencyCode(),
                    view.getNameEntered(),
                    view.getImageUri(),
                    view.getDescriptionEntered(),
                    System.currentTimeMillis(),
                    view.getDueDate()
            );
            dataRepository.saveAccount(account);
            view.close();
        }
    }


}
