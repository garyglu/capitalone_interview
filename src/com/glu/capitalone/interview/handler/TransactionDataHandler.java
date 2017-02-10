package com.glu.capitalone.interview.handler;

import com.glu.capitalone.interview.*;
import com.glu.capitalone.interview.data.*;
import com.glu.capitalone.interview.filter.*;
import com.glu.capitalone.interview.interfaces.*;
import com.glu.capitalone.interview.utils.*;
import output.*;

import java.util.*;

public class TransactionDataHandler {
    private List<ApiDataFetcher> dataFetcherList = new ArrayList<>();
    private List<TransactionFilter> filterList = new ArrayList<>();
    private List<OutputResult> outputList = new ArrayList<>();
    private final boolean testMode;

    public TransactionDataHandler() {
        this(false);
    }

    public TransactionDataHandler(boolean test) {
        testMode = test;
        dataFetcherList.add(ApiServiceEnum.getAllTransactions.getParser());
        outputList.add(new TransactionOutput());
    }


    public boolean handle() {
        return handle(true, false, false, false);
    }

    public boolean handle(boolean includingTestOnlyData, boolean ignoreDonuts, boolean crystalBall, boolean ignoreCCPayment) {
        if (ignoreDonuts) {
            filterList.add(new IgnoreDonutsTransactionFilter());
        }
        if (crystalBall) {
            dataFetcherList.add(ApiServiceEnum.getProjectedTransactionsForMonth.getParser());
        }
        if (ignoreCCPayment) {
            filterList.add(new IgnoreCCTransactionTransactionFilter());
            outputList.add(new CCPaymentTransactionOutput());
        }

        Set<Transaction> allTransactions = new HashSet<>();
        for (ApiDataFetcher f : dataFetcherList) {
            allTransactions.addAll(f.getTransactionData(includingTestOnlyData));
        }

        if (testMode && allTransactions.isEmpty()) {
            return false;

        }

        for (TransactionFilter f : filterList) {
            allTransactions = new HashSet<>(f.filter(allTransactions));
        }


        //output result
        for (OutputResult o : outputList) {
            OutputUtils.println(o.output(allTransactions, crystalBall, ignoreCCPayment));
        }
        return true;
    }

}
