package com.glu.capitalone.interview.filter;

import com.glu.capitalone.interview.data.*;

import java.util.*;

public class IgnoreDonutsTransactionFilter implements TransactionFilter {
    private static final String DONUT_NAME_1 = "Krispy Kreme Donuts";
    private static final String DONUT_NAME_2 = "DUNKIN #336784";

    @Override
    public Set<Transaction> filter(Set<Transaction> set) {
        Set<Transaction> newSet = new HashSet<>();
        for (Transaction t : set) {
            if (!isDonutTrasnaction(t)) {
                newSet.add(t);
            }
        }
        return newSet;
    }

    private boolean isDonutTrasnaction(Transaction t) {
        return
            DONUT_NAME_1.equalsIgnoreCase(t.getMerchant()) ||
            DONUT_NAME_2.equalsIgnoreCase(t.getMerchant());
    }
}
