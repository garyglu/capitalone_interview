package com.glu.capitalone.interview.output;

import com.glu.capitalone.interview.data.*;
import com.glu.capitalone.interview.interfaces.*;

import java.util.*;
import java.util.stream.*;

public class CCPaymentTransactionOutput implements OutputResult {

    @Override
    public String output(Set<Transaction> transactions, boolean crystalBall, boolean ignoreCCPayment) {
        Set<Transaction> ccPaymentTransactions = new TreeSet<>(
        new Comparator<Transaction>() {
            @Override
            public int compare(Transaction o1, Transaction o2) {
                return o1.getTransactionTime().compareTo(
                    o2.getTransactionTime());
            }
        });
        ccPaymentTransactions.addAll(
            transactions.stream().filter(Transaction::isCCPyament).collect(
                Collectors.toList()));


        StringBuilder sb = new StringBuilder("\n\n************** List Of The Credit Card Payments **************\n");
        for (Transaction t : ccPaymentTransactions) {
            sb.append(t.toString()).append("\n");
        }
        sb.append("************* End of List Of The Credit Card Payments *************\n\n");
        return  sb.toString();
    }
}
