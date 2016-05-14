package com.ioudebtcalculator.newtransaction;

import dagger.Subcomponent;

@NewTransactionScope
@Subcomponent(modules = { NewTransactionModule.class })
public interface NewTransactionComponent {

    void inject(NewTransactionFragment target);
}
