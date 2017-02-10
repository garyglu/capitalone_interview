package com.glu.capitalone.interview.filter;

import com.glu.capitalone.interview.data.*;

import java.util.*;

public class IgnoreCCTransactionTransactionFilter implements TransactionFilter {

    public static final String CC_PAYMENT_CATEGORY = "Credit Card Payment";
    public static final String CC_PAYMENT_MERCHANT = "CC Payment";
    public static final String CC_PAYMENT_RAW_MERCHANT = "CREDIT CARD PAYMENT";


    @Override
    public Set<Transaction> filter(Set<Transaction> set) {
        for (Transaction t : set) {
            if (CC_PAYMENT_CATEGORY.equalsIgnoreCase(t.getCategorization()) ||
                CC_PAYMENT_MERCHANT.equalsIgnoreCase(t.getMerchant()) ||
                CC_PAYMENT_RAW_MERCHANT.equalsIgnoreCase(t.getRawMerchant())){
                t.setCCPyament(true);
            }
        }
        return set;
    }
}
