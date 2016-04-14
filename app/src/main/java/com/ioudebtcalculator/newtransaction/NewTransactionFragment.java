package com.ioudebtcalculator.newtransaction;

import android.accounts.AccountManager;
import android.app.Fragment;

import com.ioudebtcalculator.models.AccountMetadata;

import java.util.List;

public class NewTransactionFragment extends Fragment implements NewTransactionView {

    private List<AccountMetadata> accountMetadataList;

    /**
     * Pass a bundle into the Fragment possibly containing an Account name and id.
     * If it contains the appropriate values, use those to fill in the appropriate fields
     * with the default information.
     * Otherwise, reach out to the presenter to grab a list of accounts to display.
     *
     * The issue with this approach is that we only need Account names and ids in this class.
     * Possible solutions are:
     * -Grab the account from the presenter when the fragment is started with default values.
     * --Shitty because of unnecessary database access.
     *
     * -Create a custom object only containing the account name and Id and store a list of those.
     * --Shitty because of unnecessary object creation. (No actual extra objects in memory)
     *^^^ Attempting this for now ^^^
     *
     * -Store a map of account Id to name.
     * --Shitty because of processing data models into maps within the presenter.
     * -Store parallel lists of account names and ids.
     * --Shitty because of needing two methods asking the presenter for both names and ids.
     * ---Wouldn't need to wait on the ids to be returned until they finalize creating the transaction.
     */

}
