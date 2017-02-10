package com.glu.capitalone.interview.filter;

import com.glu.capitalone.interview.data.*;

import java.util.*;

public class ExcludeTestOnlyTransactionFilter implements TransactionFilter {

    @Override
    public Set<Transaction> filter(Set<Transaction> set) {
        Set<Transaction> newSet = new HashSet<>();
        for (Transaction t : set) {
            if (!t.getIsTestOnly()) {
                newSet.add(t);
            }
        }
        return newSet;
    }
}
