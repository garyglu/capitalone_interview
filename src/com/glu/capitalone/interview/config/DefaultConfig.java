package com.glu.capitalone.interview.config;

import com.glu.capitalone.interview.*;
import com.glu.capitalone.interview.filter.*;
import com.glu.capitalone.interview.filter.TransactionFilter;
import com.glu.capitalone.interview.interfaces.*;
import com.glu.capitalone.interview.output.*;

import java.util.*;

public class DefaultConfig implements Config{
    private List<ApiDataFetcher> dataFetcherList;
    private List<TransactionFilter> filterList;
    private List<OutputResult> outputList;

    public DefaultConfig() {
        this.dataFetcherList = new ArrayList<>();
        this.filterList  = new ArrayList<>();
        this.outputList  = new ArrayList<>();
    }

    @Override
    public void config(boolean includingTestOnlyData, boolean ignoreDonuts,
            boolean crystalBall, boolean ignoreCCPayment) {

        //set default setting
        dataFetcherList.add(getDefaultAllTransactionParser());
        outputList.add(new TransactionOutput());

        if (!includingTestOnlyData) {
            filterList.add(new ExcludeTestOnlyTransactionFilter());
        }
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
    }

    @Override
    public List<ApiDataFetcher> getDataFetcherList() {
        return dataFetcherList;
    }

    @Override
    public List<TransactionFilter> getFilterList() {
        return filterList;
    }

    @Override
    public List<OutputResult> getOutputList() {
        return outputList;
    }

    protected ApiDataParser getDefaultAllTransactionParser() {
        return ApiServiceEnum.getAllTransactions.getParser();
    }

    protected ApiDataParser getDefaultProjectedTransactionParser() {
        return ApiServiceEnum.getProjectedTransactionsForMonth.getParser();
    }
}
