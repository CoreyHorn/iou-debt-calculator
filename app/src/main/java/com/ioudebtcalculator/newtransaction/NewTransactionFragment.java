package com.ioudebtcalculator.newtransaction;

import android.app.Fragment;
import android.os.Bundle;

public class NewTransactionFragment extends Fragment implements NewTransactionView {

    public static final String KEY_ACCOUNT_ID = "accountId";

    /**
     * Used to store the accountId value passed into this fragment within the arguments bundle.
     * Can be null meaning that no account is currently associated with this fragment.
     */
    private Integer accountId;

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
    public void close() {
    }
}
