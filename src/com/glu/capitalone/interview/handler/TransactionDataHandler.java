package com.glu.capitalone.interview.handler;

import com.glu.capitalone.interview.*;
import com.glu.capitalone.interview.data.*;
import com.glu.capitalone.interview.filter.*;
import com.glu.capitalone.interview.interfaces.*;
import com.glu.capitalone.interview.output.*;

import java.util.*;

public class TransactionDataHandler {

    public String handle(boolean includingTestOnlyData, boolean ignoreDonuts,
        boolean crystalBall, boolean ignoreCCPayment) throws Exception{
        List<ApiDataFetcher> dataFetcherList = new ArrayList<>();
        List<TransactionFilter> filterList = new ArrayList<>();
        List<OutputResult> outputList = new ArrayList<>();

        //set default setting
        dataFetcherList.add(getDefaultAllTransactionParser());
        outputList.add(new TransactionOutput());

        if (ignoreDonuts) {
            filterList.add(new IgnoreDonutsTransactionFilter());
        }
        if (crystalBall) {
            dataFetcherList.add(getDefaultProjectedTransactionParser());
        }
        if (ignoreCCPayment) {
            filterList.add(new IgnoreCCTransactionTransactionFilter());
            outputList.add(new CCPaymentTransactionOutput());
        }

        Set<Transaction> allTransactions = new HashSet<>();
        for (ApiDataFetcher f : dataFetcherList) {
            allTransactions.addAll(f.getTransactionData(includingTestOnlyData));
        }

        for (TransactionFilter f : filterList) {
            allTransactions = new HashSet<>(f.filter(allTransactions));
        }

        StringBuilder sb = new StringBuilder();
        for (OutputResult o : outputList) {
            sb.append(o.output(allTransactions, crystalBall, ignoreCCPayment));
        }

        return sb.toString();
    }

    protected ApiDataParser getDefaultAllTransactionParser() {
        return ApiServiceEnum.getAllTransactions.getParser();
    }

    protected ApiDataParser getDefaultProjectedTransactionParser() {
        return ApiServiceEnum.getProjectedTransactionsForMonth.getParser();
    }
}
